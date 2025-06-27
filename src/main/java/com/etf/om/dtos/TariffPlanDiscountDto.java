package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlanDiscountDto {
    private UUID id;
    private BigDecimal initialDiscount;
    private BigDecimal additionalDiscount;
    private String tariffPlanIdentifier;
    private UUID offerId;
}
