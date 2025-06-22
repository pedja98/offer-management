package com.etf.om.services;

import com.etf.om.dtos.CreateTariffPlanDto;
import com.etf.om.dtos.CreateTariffPlansBulkResponseDto;
import com.etf.om.dtos.TariffPlanDto;
import com.etf.om.entities.Offer;
import com.etf.om.entities.TariffPlan;
import com.etf.om.filters.SetCurrentUserFilter;
import com.etf.om.repositories.OfferRepository;
import com.etf.om.repositories.TariffPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.etf.om.common.OmConstants.SuccessCodes.ADD_TARIFF_PLANS;
import static com.etf.om.common.OmConstants.ErrorCodes.OFFER_NOT_FOUND;

@Service
public class TariffPlanService {

    @Autowired
    private TariffPlanRepository tariffPlanRepository;

    @Autowired
    private OfferRepository offerPlanRepository;

    @Transactional
    public CreateTariffPlansBulkResponseDto createBulkTariffPlans(CreateTariffPlanDto body) {
        Offer offer = offerPlanRepository.findById(body.getOmOfferId())
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
        return new CreateTariffPlansBulkResponseDto(ADD_TARIFF_PLANS,  tariffPlans);
    }

    public List<TariffPlanDto> getTariffPlansFromOffer(UUID offerId) {
        return this.tariffPlanRepository.findAllTariffPlanDtosByOfferId(offerId);
    }
}
