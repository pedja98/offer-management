package com.etf.om.entities;

import com.etf.om.enums.OfferApprovalLevels;
import com.etf.om.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "offers")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OfferStatus status;

    @Column(nullable = false, name = "approval_description", columnDefinition = "TEXT")
    private String approvalDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "approval_level")
    private OfferApprovalLevels approvalLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "approval_status")
    private OfferApprovalLevels approvalStatus;

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

    @Column(name = "deleted")
    private Boolean deleted = false;
}
