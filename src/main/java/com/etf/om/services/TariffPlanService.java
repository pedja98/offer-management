package com.etf.om.services;

import com.etf.om.dtos.CreateTariffPlanDto;
import com.etf.om.dtos.CreateTariffPlansBulkResponseDto;
import com.etf.om.dtos.TariffPlanDto;
import com.etf.om.dtos.UpdateTariffPlansDto;
import com.etf.om.entities.Offer;
import com.etf.om.entities.TariffPlan;
import com.etf.om.filters.SetCurrentUserFilter;
import com.etf.om.repositories.AddonRepository;
import com.etf.om.repositories.OfferRepository;
import com.etf.om.repositories.TariffPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.etf.om.common.OmConstants.ErrorCodes.TARIFF_PLAN_NOT_FOUND;
import static com.etf.om.common.OmConstants.ErrorCodes.OFFER_NOT_FOUND;
import static com.etf.om.common.OmConstants.SuccessCodes.*;

@Service
public class TariffPlanService {

    @Autowired
    private TariffPlanRepository tariffPlanRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private AddonRepository addonRepository;

    @Transactional
    public CreateTariffPlansBulkResponseDto createBulkTariffPlans(CreateTariffPlanDto body) {
        Offer offer = offerRepository.findById(body.getOmOfferId())
                .orElseThrow(() -> new RuntimeException(OFFER_NOT_FOUND));

        String username = SetCurrentUserFilter.getCurrentUsername();
        List<TariffPlanDto> tariffPlans = new ArrayList<>();

        for (int i = 0; i < body.getNumberOfItems(); i++) {
            TariffPlan tariffPlan = TariffPlan.builder()
                    .actualTpIdentifier(body.getTariffPlan().getIdentifier())
                    .actualTpName(body.getTariffPlan().getName())
                    .actualTpPrice(body.getTariffPlan().getPrice())
                    .offer(offer)
                    .createdByUser(username)
                    .build();
            UUID tpId = this.tariffPlanRepository.save(tariffPlan).getId();
            tariffPlans.add(new TariffPlanDto(tpId,
                    tariffPlan.getPlannedTpName(), tariffPlan.getPlannedTpIdentifier(), tariffPlan.getPlannedTpPrice(),
                    tariffPlan.getActualTpName(), tariffPlan.getActualTpIdentifier(), tariffPlan.getActualTpPrice()
            ));
        }
        return new CreateTariffPlansBulkResponseDto(ADD_TARIFF_PLANS, tariffPlans);
    }

    public List<TariffPlanDto> getTariffPlansFromOffer(UUID offerId) {
        return this.tariffPlanRepository.findAllTariffPlanDtosByOfferId(offerId);
    }

    @Transactional
    public String updateBulkTariffPlans(UpdateTariffPlansDto body) {
        for (UUID uuid : body.uuids) {
            TariffPlan tariffPlan = tariffPlanRepository.findById(uuid)
                    .orElseThrow(() -> new RuntimeException(TARIFF_PLAN_NOT_FOUND));
            tariffPlan.setActualTpIdentifier(body.getNewTpIdentifier());
            tariffPlan.setActualTpPrice(body.getNewTpPrice());
            tariffPlan.setActualTpName(body.getNewTpName());

            this.tariffPlanRepository.save(tariffPlan);
        }
        this.removeConnectedEntities();
        return TARIFF_PLANS_UPDATED;
    }

    @Transactional
    public String deleteBulkTariffPlans(List<UUID> tpIds) {
        for (UUID uuid : tpIds) {
            TariffPlan tariffPlan = tariffPlanRepository.findById(uuid)
                    .orElseThrow(() -> new RuntimeException(TARIFF_PLAN_NOT_FOUND));
            this.tariffPlanRepository.delete(tariffPlan);
        }
        this.removeConnectedEntities();
        return TARIFF_PLANS_DELETED;
    }

    protected void removeConnectedEntities() {
        List<String> tpIdentifiers = this.tariffPlanRepository.findAllDistinctPreferredIdentifiers();
        List<String> addonTariffPlanIdentifiers = this.addonRepository.findDistinctTariffPlanIdentifiers();

        Set<String> tpIdentifierSet = new HashSet<>(tpIdentifiers);

        List<String> missingIdentifiers = addonTariffPlanIdentifiers.stream()
                .filter(identifier -> !tpIdentifierSet.contains(identifier))
                .toList();

        this.addonRepository.deleteAllByTariffPlanIdentifierIn(missingIdentifiers);
    }
}
