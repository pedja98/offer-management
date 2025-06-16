package com.etf.om.repositories;

import com.etf.om.dtos.OfferDto;
import com.etf.om.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    @Query("""
            SELECT new com.etf.om.dtos.OfferDto(o.id, o.name, o.status, o.approvalDescription, o.approvalLevel, o.approvalStatus, o.createdByUsername, o.modifiedByUsername, o.dateCreated, o.dateModified)
            FROM Offer o
            WHERE o.id = :id""")
    Optional<OfferDto> findOfferDtoById(UUID id);

    @Query("""
            SELECT new com.etf.om.dtos.OfferDto(o.id, o.name, o.status, o.approvalDescription, o.approvalLevel, o.approvalStatus, o.createdByUsername, o.modifiedByUsername, o.dateCreated, o.dateModified)
            FROM Offer o""")
    List<OfferDto> findAllOfferDto();
}
