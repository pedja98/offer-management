package com.etf.om.dtos;

import com.etf.om.enums.OfferApprovalLevels;
import com.etf.om.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private UUID id;
    private String name;
    private Integer contractObligation;
    private OfferStatus status;
    private String approvalDescription;
    private OfferApprovalLevels approvalLevel;
    private Long companyId;
    private Long opportunityId;
    private Long crmOfferId;
    private String createdByUsername;
    private String modifiedByUsername;
    private Instant dateCreated;
    private Instant dateModified;
}
