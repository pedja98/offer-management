package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTariffPlanDto {
    private Integer numberOfItems;
    private UUID omOfferId;
    private SaveTariffPlanItemDto tariffPlan;
}
