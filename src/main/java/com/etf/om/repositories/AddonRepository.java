package com.etf.om.repositories;

import com.etf.om.dtos.AddonDto;
import com.etf.om.entities.Addon;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AddonRepository extends JpaRepository<Addon, UUID> {
    @Query("""
    SELECT new com.etf.om.dtos.AddonDto(
        a.id,
        a.name,
        a.identifier,
        a.price,
        a.tariffPlanIdentifier,
        a.offer.id
    )
    FROM Addon a
    WHERE a.offer.id = :offerId AND a.tariffPlanIdentifier = :tariffPlanIdentifier
""")
    List<AddonDto> findAllAddonDtosByOfferIdAndTariffPlanIdentifier(UUID offerId, String tariffPlanIdentifier);

    @Modifying
    @Transactional
    @Query("DELETE FROM Addon a WHERE a.tariffPlanIdentifier IN :identifiers")
    void deleteAllByTariffPlanIdentifierIn(@org.springframework.data.repository.query.Param("identifiers") List<String> identifiers);

    @Query("SELECT COUNT(a) > 0 FROM Addon a WHERE a.offer.id = :offerId AND a.identifier = :addonIdentifier AND a.tariffPlanIdentifier = :tpIdentifier")
    boolean existsByOfferIdAddonIdentifierAndTariffPlanIdentifier(@Param("offerId") UUID offerId, @Param("addonIdentifier") String addonIdentifier, @Param("tpIdentifier") String tpIdentifier);

}
