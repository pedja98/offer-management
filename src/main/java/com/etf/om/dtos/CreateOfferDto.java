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
public class CreateOfferDto {
    private String name;
    private Long crmOfferId;
    private Long companyId;
    private Long opportunityId;
}
