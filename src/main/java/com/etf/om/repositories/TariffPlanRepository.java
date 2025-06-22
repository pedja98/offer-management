package com.etf.om.repositories;

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
                                             tp.actualTpName, tp.actualTpIdentifier, tp.actualTpPrice)
    FROM TariffPlan tp
    WHERE tp.offer.id = :offerId
""")
    List<TariffPlanDto> findAllTariffPlanDtosByOfferId(@Param("offerId") UUID offerId);

}
