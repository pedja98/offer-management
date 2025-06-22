package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddonDto {
    private UUID id;
    private Map<String, String> name;
    private String identifier;
    private BigDecimal price;
    private String tariffPlanIdentifier;
    private UUID offerId;
}
