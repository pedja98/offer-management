package com.etf.om.repositories;

import com.etf.om.dtos.IdentifierCountNameDto;
import com.etf.om.dtos.TariffPlanDto;
import com.etf.om.entities.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TariffPlanRepository extends JpaRepository<TariffPlan, UUID> {
    @Query("""
            SELECT new com.etf.om.dtos.TariffPlanDto(tp.id, tp.plannedTpName, tp.plannedTpIdentifier, tp.plannedTpPrice,
                                                     tp.actualTpName, tp.actualTpIdentifier, tp.actualTpPrice, tp.deactivate)
            FROM TariffPlan tp
            WHERE tp.offer.id = :offerId
            Order by tp.dateCreated, tp.dateModified desc
            """)
    List<TariffPlanDto> findAllTariffPlanDtosByOfferId(@Param("offerId") UUID offerId);

    @Query("""
            SELECT DISTINCT\s
                CASE\s
                    WHEN tp.actualTpIdentifier IS NOT NULL THEN tp.actualTpIdentifier\s
                    ELSE tp.plannedTpIdentifier\s
                END
            FROM TariffPlan tp
            WHERE tp.offer.id = :offerId and tp.deactivate = false
            """)
    List<String> findAllDistinctPreferredIdentifiers(@Param("offerId") UUID offerId);

    @Query("""
            SELECT new com.etf.om.dtos.IdentifierCountNameDto(
                COALESCE(tp.actualTpIdentifier, tp.plannedTpIdentifier),
                COALESCE(tp.actualTpName, tp.plannedTpName),
                COUNT(tp)
            )
            FROM TariffPlan tp
            WHERE tp.offer.id = :offerId AND tp.deactivate = false
            GROUP BY COALESCE(tp.actualTpIdentifier, tp.plannedTpIdentifier), COALESCE(tp.actualTpName, tp.plannedTpName)
            """)
    List<IdentifierCountNameDto> countTariffPlansByIdentifierWithName(@Param("offerId") UUID offerId);
}
