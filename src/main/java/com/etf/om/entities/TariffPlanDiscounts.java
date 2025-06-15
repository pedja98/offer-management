package com.etf.om.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tariff_plan_discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffPlanDiscounts {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, name="initial_discount")
    private BigDecimal initialDiscount;

    @Column(nullable = false, name="additional_discount")
    private BigDecimal additionalDiscount =  BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "tariff_plan_id", updatable = false)
    private TariffPlan tariffPlan;

    @Column(nullable = false, length = 20, name = "created_by_user")
    private String createdByUser;

    @Column(length = 20, name = "modified_by_user")
    private String modifiedByUser;

    @CreationTimestamp
    @Column(name = "date_created")
    private Instant dateCreated;

    @UpdateTimestamp
    @Column(name = "date_modified")
    private Instant dateModified;
}
