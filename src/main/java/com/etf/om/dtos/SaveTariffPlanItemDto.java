package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveTariffPlanItemDto {
    private String identifier;
    private Map<String, String> name;
    private BigDecimal price;
}
