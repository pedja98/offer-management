package com.etf.om.repositories;

import com.etf.om.dtos.TariffPlanDiscountDto;
import com.etf.om.entities.TariffPlanDiscount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TariffPlanDiscountRepository extends JpaRepository<TariffPlanDiscount, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TariffPlanDiscount tpd WHERE tpd.tariffPlanIdentifier IN :identifiers")
    void deleteAllByTariffPlanIdentifierIn(@org.springframework.data.repository.query.Param("identifiers") List<String> identifiers);

    @Modifying
    @Transactional
    @Query("DELETE FROM TariffPlanDiscount tpd WHERE tpd.tariffPlanIdentifier = :identifier and tpd.offer.id = :offerId")
    void deleteByTariffPlanIdentifierAndOfferId(@org.springframework.data.repository.query.Param("identifier") String identifier, @Param("offerId") UUID offerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM TariffPlanDiscount tpd WHERE tpd.offer.id = :offerId")
    void deleteByOfferId(@Param("offerId") UUID offerId);

    TariffPlanDiscount findByTariffPlanIdentifierAndOfferId(String tariffPlanIdentifier, UUID offer_id);

    @Query("""
                SELECT new com.etf.om.dtos.TariffPlanDiscountDto(tp.id, tp.initialDiscount, tp.additionalDiscount, tp.tariffPlanIdentifier, tp.offer.id)
                FROM TariffPlanDiscount tp
                WHERE tp.offer.id = :offerId and tp.tariffPlanIdentifier = :tariffPlanIdentifier
            """)
    TariffPlanDiscountDto findTariffPlanDiscountByOfferIdAndTariffPlanIdentifier(@Param("offerId") UUID offerId, @Param("tariffPlanIdentifier") String tariffPlanIdentifier);
}
