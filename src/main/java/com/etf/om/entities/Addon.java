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
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "addons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Addon {

    @Id
    @GeneratedValue
    private UUID id;

    @Convert(converter = JsonToNameMapConvertor.class)
    @Column(columnDefinition = "VARCHAR", length = 255)
    private Map<String, String> name;

    @Column(nullable = false, length = 30)
    private String identifier;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, name = "tariff_Plan_Identifier")
    private String tariffPlanIdentifier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private Offer offer;

    @Column(nullable = false, length = 20, name = "created_by_user")
    private String createdByUser;

    @CreationTimestamp
    @Column(name = "date_created")
    private Instant dateCreated;
}