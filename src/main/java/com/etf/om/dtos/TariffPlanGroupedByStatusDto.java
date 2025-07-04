package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class TariffPlanGroupedByStatusDto {
    private List<PrintTariffPlanDto> activeTariffPlans;
    private List<PrintTariffPlanDto> deactivatedTariffPlans;
    private Map<String, List<AddonDto>> activeTariffPlansAddons;
    Map<String, BigDecimal> activeDiscounts;
}
