package com.etf.om.dtos;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeactivateTariffPlanDto {
    private Boolean deactivate;
    private UUID omOfferId;
}
