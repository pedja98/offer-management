package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTariffPlansDto {
    public String newTpIdentifier;
    private Map<String, String> newTpName;
    private BigDecimal newTpPrice;
    public List<UUID> uuids;
}
