package com.etf.om.services;

import com.etf.om.dtos.TariffPlanDiscountDto;
import com.etf.om.entities.TariffPlanDiscount;
import com.etf.om.repositories.TariffPlanDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.etf.om.common.OmConstants.ErrorCodes.DISCOUNT_NOT_FOUND;
import static com.etf.om.common.OmConstants.ErrorCodes.DISCOUNT_MAX;
import static com.etf.om.common.OmConstants.SuccessCodes.DISCOUNT_UPDATED;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TariffPlanDiscountService {
    @Autowired
    private TariffPlanDiscountRepository tariffPlanDiscountsRepository;

    public TariffPlanDiscountDto getTariffPlansFromOffer(UUID offerId, String tpIdentifier) {
        return this.tariffPlanDiscountsRepository.findTariffPlanDiscountByOfferIdAndTariffPlanIdentifier(offerId, tpIdentifier);
    }

    public String updateAdditionalDiscount(UUID id, BigDecimal additionalDiscount) {
        TariffPlanDiscount tpDiscount = this.tariffPlanDiscountsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(DISCOUNT_NOT_FOUND));
        if (tpDiscount.getInitialDiscount().add(additionalDiscount).compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new RuntimeException(DISCOUNT_MAX);
        }
        tpDiscount.setAdditionalDiscount(additionalDiscount);
        this.tariffPlanDiscountsRepository.save(tpDiscount);
        return DISCOUNT_UPDATED;
    }
}
