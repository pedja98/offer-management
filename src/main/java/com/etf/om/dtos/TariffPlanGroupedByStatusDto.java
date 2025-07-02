package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TariffPlanGroupedByStatusDto {
    private List<PrintTariffPlanDto> activeTariffPlans;
    private List<PrintTariffPlanDto> deactivatedTariffPlans;
}
