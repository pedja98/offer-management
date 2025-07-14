package com.etf.om.dtos;

import com.etf.om.enums.OpportunityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOfferDto {
    private String name;
    private Long crmOfferId;
    private Long companyId;
    private Long opportunityId;
    private OpportunityType opportunityType;
    private String opportunityName;

}
