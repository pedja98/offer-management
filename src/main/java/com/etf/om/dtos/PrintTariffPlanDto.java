package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class PrintTariffPlanDto {
    private Map<String, String> name;
    private String identifier;
    private BigDecimal price;
    private Long count;
}
