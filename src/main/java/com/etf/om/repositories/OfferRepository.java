package com.etf.om.repositories;

import com.etf.om.dtos.OfferDto;
import com.etf.om.entities.Offer;
import com.etf.om.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    Offer findOfferByCrmOfferId(Long crmOfferId);

    @Query("""
            SELECT new com.etf.om.dtos.OfferDto(o.id, o.name, o.opportunityName, o.opportunityType,o.contractObligation, o.status, o.approvalDescription,
                         o.approvalLevel, o.companyId, o.opportunityId, o.crmOfferId ,o.createdByUsername, o.modifiedByUsername, o.dateCreated, o.dateModified)
            FROM Offer o
            WHERE o.id = :id""")
    Optional<OfferDto> findOfferDtoById(UUID id);

    @Query("""
            SELECT new com.etf.om.dtos.OfferDto(o.id, o.name, o.opportunityName, o.opportunityType, o.contractObligation, o.status, o.approvalDescription,
                         o.approvalLevel, o.companyId, o.opportunityId, o.crmOfferId ,o.createdByUsername, o.modifiedByUsername, o.dateCreated, o.dateModified)
            FROM Offer o
            WHERE o.crmOfferId = :crmOfferId""")
    Optional<OfferDto> findOfferDtoByCrmOfferId(Long crmOfferId);

    @Query("""
            SELECT new com.etf.om.dtos.OfferDto(o.id, o.name, o.opportunityName, o.opportunityType, o.contractObligation, o.status, o.approvalDescription,
                         o.approvalLevel, o.companyId, o.opportunityId, o.crmOfferId ,o.createdByUsername, o.modifiedByUsername, o.dateCreated, o.dateModified)
            FROM Offer o""")
    List<OfferDto> findAllOfferDto();

    @Query("""
                SELECT COUNT(o) > 0
                FROM Offer o
                WHERE o.opportunityId = :opportunityId
                  AND o.id <> :offerId
                  AND o.status IN (
                    com.etf.om.enums.OfferStatus.L1_PENDING,
                    com.etf.om.enums.OfferStatus.L2_PENDING,
                    com.etf.om.enums.OfferStatus.CUSTOMER_ACCEPTED,
                    com.etf.om.enums.OfferStatus.OFFER_APPROVED,
                    com.etf.om.enums.OfferStatus.CONCLUDED
                  )
            """)
    boolean existsActiveOfferByOpportunityId(@Param("opportunityId") Long opportunityId, @Param("offerId") UUID offerId);

    @Modifying
    @Query("""
                UPDATE Offer o
                SET o.status = :offerStatus,
                    o.modifiedByUsername = :modifiedByUsername,
                    o.dateModified = CURRENT_TIMESTAMP
                WHERE o.opportunityId = :opportunityId
            """)
    void updateOfferStatusAndModifiedByOpportunityId(@Param("opportunityId") Long opportunityId,
                                                    @Param("offerStatus") OfferStatus offerStatus,
                                                    @Param("modifiedByUsername") String modifiedByUsername);


}
