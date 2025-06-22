package com.etf.om.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TariffPlanDto {
    private UUID id;
    private Map<String, String> plannedTpName;
    private String plannedTpIdentifier;
    private BigDecimal plannedTpPrice;
    private Map<String, String> actualTpName;
    private String actualTpIdentifier;
    private BigDecimal actualTpPrice;
}
