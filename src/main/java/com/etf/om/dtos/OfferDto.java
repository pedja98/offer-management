package com.etf.om.dtos;

import com.etf.om.enums.OfferApprovalLevels;
import com.etf.om.enums.OfferApprovalStatus;
import com.etf.om.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private UUID id;
    private String name;
    private Integer mmc;
    private Integer contractPeriod;
    private OfferStatus status;
    private String approvalDescription;
    private OfferApprovalLevels approvalLevel;
    private OfferApprovalStatus approvalStatus;
    private String createdByUsername;
    private String modifiedByUsername;
    private Instant dateCreated;
    private Instant dateModified;
}
