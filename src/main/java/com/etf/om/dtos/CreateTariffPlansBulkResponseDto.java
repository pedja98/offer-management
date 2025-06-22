package com.etf.om.dtos;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateTariffPlansBulkResponseDto {
    private String message;
    private List<TariffPlanDto> tariffPlans;
}
