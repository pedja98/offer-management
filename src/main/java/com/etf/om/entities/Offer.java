package com.etf.om.entities;

import com.etf.om.enums.OfferApprovalLevels;
import com.etf.om.enums.OfferStatus;
import com.etf.om.enums.OpportunityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Table(name = "offers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OfferStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15,  name = "opportunity_type")
    private OpportunityType opportunityType;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TariffPlan> tariffPlans;

    @Column(nullable = false, name = "contract_obligation")
    private Integer contractObligation = 0;

    @Column(name = "approval_description", columnDefinition = "TEXT")
    private String approvalDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_level")
    private OfferApprovalLevels approvalLevel;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "opportunity_id", nullable = false)
    private Long opportunityId;

    @Column(name = "crm_offer_id", nullable = false)
    private Long crmOfferId;

    @Column(name = "created_by_username", nullable = false, updatable = false)
    private String createdByUsername;

    @Column(name = "modified_by_username")
    private String modifiedByUsername;

    @CreationTimestamp
    @Column(name = "date_created")
    private Instant dateCreated;

    @UpdateTimestamp
    @Column(name = "date_modified")
    private Instant dateModified;
}
