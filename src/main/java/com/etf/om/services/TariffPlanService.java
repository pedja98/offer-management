package com.etf.om.services;

import com.etf.om.dtos.*;
import com.etf.om.entities.Offer;
import com.etf.om.entities.TariffPlan;
import com.etf.om.entities.TariffPlanDiscount;
import com.etf.om.exceptions.ItemNotFoundException;
import com.etf.om.filters.SetCurrentUserFilter;
import com.etf.om.repositories.AddonRepository;
import com.etf.om.repositories.OfferRepository;
import com.etf.om.repositories.TariffPlanDiscountRepository;
import com.etf.om.repositories.TariffPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.etf.om.common.OmConstants.ErrorCodes.TARIFF_PLAN_NOT_FOUND;
import static com.etf.om.common.OmConstants.ErrorCodes.OFFER_NOT_FOUND;
import static com.etf.om.common.OmConstants.SuccessCodes.*;

@Service
public class TariffPlanService {

    private final DiscountFormatterService webClient;

    @Autowired
    private TariffPlanRepository tariffPlanRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private AddonRepository addonRepository;

    @Autowired
    private TariffPlanDiscountRepository tariffPlanDiscountsRepository;

    public TariffPlanService(DiscountFormatterService webClient) {
        this.webClient = webClient;
    }

    @Transactional
    public String createBulkTariffPlans(CreateTariffPlanDto body) {
        Offer offer = offerRepository.findById(body.getOmOfferId())
                .orElseThrow(() -> new RuntimeException(OFFER_NOT_FOUND));

        String username = SetCurrentUserFilter.getCurrentUsername();

        for (int i = 0; i < body.getNumberOfItems(); i++) {
            TariffPlan tariffPlan = TariffPlan.builder()
                    .actualTpIdentifier(body.getTariffPlan().getIdentifier())
                    .actualTpName(body.getTariffPlan().getName())
                    .actualTpPrice(body.getTariffPlan().getPrice())
                    .offer(offer)
                    .deactivate(false)
                    .createdByUser(username)
                    .build();
            UUID tpId = this.tariffPlanRepository.save(tariffPlan).getId();
        }
        this.formatDiscountConnectedToTariffPlan(body.getOmOfferId());
        return ADD_TARIFF_PLANS;
    }

    public List<TariffPlanDto> getTariffPlansFromOffer(UUID offerId) {
        return this.tariffPlanRepository.findAllTariffPlanDtosByOfferId(offerId);
    }

    @Transactional
    public String updateBulkTariffPlans(UpdateTariffPlansDto body, UUID offerId) {
        List<String> tpIdentifiersBeforeChange = this.tariffPlanRepository.findAllDistinctPreferredIdentifiers(offerId);
        for (UUID uuid : body.uuids) {
            TariffPlan tariffPlan = tariffPlanRepository.findById(uuid)
                    .orElseThrow(() -> new RuntimeException(TARIFF_PLAN_NOT_FOUND));
            tariffPlan.setActualTpIdentifier(body.getNewTpIdentifier());
            tariffPlan.setActualTpPrice(body.getNewTpPrice());
            tariffPlan.setActualTpName(body.getNewTpName());

            this.tariffPlanRepository.save(tariffPlan);
        }
        this.removeConnectedEntities(offerId, tpIdentifiersBeforeChange);
        this.formatDiscountConnectedToTariffPlan(offerId);
        return TARIFF_PLANS_UPDATED;
    }

    @Transactional
    public String deleteBulkTariffPlans(List<UUID> tpIds, UUID offerId) {
        List<String> tpIdentifiersBeforeChange = this.tariffPlanRepository.findAllDistinctPreferredIdentifiers(offerId);
        for (UUID uuid : tpIds) {
            TariffPlan tariffPlan = tariffPlanRepository.findById(uuid)
                    .orElseThrow(() -> new RuntimeException(TARIFF_PLAN_NOT_FOUND));
            this.tariffPlanRepository.delete(tariffPlan);
        }
        this.removeConnectedEntities(offerId, tpIdentifiersBeforeChange);
        this.formatDiscountConnectedToTariffPlan(offerId);
        if (this.tariffPlanRepository.countActivatedTariffPlans() == 0) {
            Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new RuntimeException(OFFER_NOT_FOUND));
            offer.setApprovalLevel(null);
            this.offerRepository.save(offer);
        }
        return TARIFF_PLANS_DELETED;
    }

    @Transactional
    public String deactivateOfferTariffPlan(UUID id, DeactivateTariffPlanDto body) {
        List<String> tpIdentifiersBeforeChange = this.tariffPlanRepository.findAllDistinctPreferredIdentifiers(body.getOmOfferId());
        TariffPlan tariffPlan = this.tariffPlanRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(TARIFF_PLAN_NOT_FOUND));
        tariffPlan.setDeactivate(body.getDeactivate());
        this.tariffPlanRepository.save(tariffPlan);
        this.removeConnectedEntities(tariffPlan.getOffer().getId(), tpIdentifiersBeforeChange);
        return TARIFF_PLAN_UPDATED;
    }

    public Map<String, Map<String, Object>> getTariffPlanCountsWithNames(UUID offerId) {
        List<IdentifierCountNameDto> data = this.tariffPlanRepository.countTariffPlansByIdentifierWithName(offerId);

        return data.stream().collect(Collectors.toMap(
                IdentifierCountNameDto::getIdentifier,
                dto -> Map.of(
                        "name", dto.getName(),
                        "count", dto.getCount()
                )
        ));
    }

    private void removeConnectedEntities(UUID offerId, List<String> tpIdentifiersBeforeChange) {
        List<String> tpIdentifiers = this.tariffPlanRepository.findAllDistinctPreferredIdentifiers(offerId);

        Set<String> tpIdentifierSet = new HashSet<>(tpIdentifiers);

        List<String> missingIdentifiers = tpIdentifiersBeforeChange.stream()
                .filter(identifier -> !tpIdentifierSet.contains(identifier))
                .toList();
        this.addonRepository.deleteAllByTariffPlanIdentifierIn(missingIdentifiers);
        this.tariffPlanDiscountsRepository.deleteAllByTariffPlanIdentifierIn(tpIdentifiers);
    }

    private void formatDiscountConnectedToTariffPlan(UUID omOfferId) {
        List<IdentifierCountDto> counts = this.tariffPlanRepository.countTariffPlansGroupedByPreferredIdentifier();

        for (IdentifierCountDto count : counts) {
            String identifier = count.getIdentifier();
            int amount = count.getCount().intValue();

            try {
                PcTariffPlanDiscountDto discountDto = fetchDiscount(identifier, amount);

                if (discountDto != null) {
                    TariffPlanDiscount existingDiscount = this.tariffPlanDiscountsRepository
                            .findByTariffPlanIdentifierAndOfferId(identifier, omOfferId);

                    BigDecimal newInitial = discountDto.getDiscount();
                    String currentUser = SetCurrentUserFilter.getCurrentUsername();

                    if (existingDiscount != null) {
                        BigDecimal existingAdditional = existingDiscount.getAdditionalDiscount();
                        BigDecimal sum = newInitial.add(existingAdditional);

                        if (sum.compareTo(BigDecimal.valueOf(100)) > 0) {
                            existingDiscount.setAdditionalDiscount(BigDecimal.valueOf(100).subtract(newInitial));
                        }

                        existingDiscount.setInitialDiscount(newInitial);
                        existingDiscount.setModifiedByUser(currentUser);

                        this.tariffPlanDiscountsRepository.save(existingDiscount);
                    } else {
                        Offer offer = offerRepository.findById(omOfferId)
                                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

                        TariffPlanDiscount newDiscount = TariffPlanDiscount.builder()
                                .tariffPlanIdentifier(identifier)
                                .offer(offer)
                                .initialDiscount(newInitial)
                                .additionalDiscount(BigDecimal.ZERO)
                                .createdByUser(currentUser)
                                .build();

                        this.tariffPlanDiscountsRepository.save(newDiscount);
                    }
                } else {
                    this.tariffPlanDiscountsRepository.deleteByTariffPlanIdentifier(identifier);
                }
            } catch (Exception e) {
                System.err.println("Failed to process discount for identifier: " + identifier + " - " + e.getMessage());
            }
        }
    }

    private PcTariffPlanDiscountDto fetchDiscount(String identifier, int amount) {
        String username = SetCurrentUserFilter.getCurrentUsername();
        String userType = SetCurrentUserFilter.getCurrentUserType();

        return webClient
                .get()
                .uri("/{identifier}/first-fit-discount?amount={amount}", identifier, amount)
                .header("X-Username", username)
                .header("X-User-Type", userType)
                .retrieve()
                .bodyToMono(PcTariffPlanDiscountDto.class)
                .block();
    }
}