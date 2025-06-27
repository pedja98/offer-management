package com.etf.om.controllers;

import com.etf.om.dtos.MessageResponse;
import com.etf.om.dtos.TariffPlanDiscountDto;
import com.etf.om.services.TariffPlanDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/tariff-plan-discounts")
@RequiredArgsConstructor
public class TariffPlanDiscountController {
    @Autowired
    private TariffPlanDiscountService tariffPlanDiscountService;

    @GetMapping("/{offerId}/{tpIdentifier}")
    public ResponseEntity<TariffPlanDiscountDto> getTariffPlanDiscount(@PathVariable UUID offerId,
                                                                       @PathVariable String tpIdentifier) {
        return ResponseEntity.ok(tariffPlanDiscountService.getTariffPlansFromOffer(offerId, tpIdentifier));
    }

    @PutMapping("/{id}/additional-discount")
    public ResponseEntity<MessageResponse> updateAdditionalDiscount(@PathVariable UUID id,
                                                                    @RequestParam BigDecimal additionalDiscount) {
        return ResponseEntity.ok(new MessageResponse(tariffPlanDiscountService.updateAdditionalDiscount(id, additionalDiscount)));
    }
}
