package com.etf.om.entities;

import com.etf.om.converters.JsonToNameMapConvertor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "tariff_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TariffPlan {

    @Id
    @GeneratedValue
    private UUID id;

    @Convert(converter = JsonToNameMapConvertor.class)
    @Column(columnDefinition = "VARCHAR", length = 255, name = "planned_tp_name")
    private Map<String, String> plannedTpName;

    @Column(length = 30, name = "planned_tp_identifier")
    private String plannedTpIdentifier;

    @Column(name = "planned_tp_price")
    private BigDecimal plannedTpPrice;

    @Convert(converter = JsonToNameMapConvertor.class)
    @Column(columnDefinition = "VARCHAR", length = 255, name = "actual_tp_name")
    private Map<String, String> actualTpName;

    @Column(length = 30, name = "actual_tp_identifier")
    private String actualTpIdentifier;

    @Column(name = "actual_tp_price")
    private BigDecimal actualTpPrice;

    @Column()
    private Boolean deactivate = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

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
