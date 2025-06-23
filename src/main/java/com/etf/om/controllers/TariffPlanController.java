package com.etf.om.controllers;

import com.etf.om.dtos.CreateTariffPlanDto;
import com.etf.om.dtos.CreateTariffPlansBulkResponseDto;
import com.etf.om.dtos.TariffPlanDto;
import com.etf.om.dtos.UpdateTariffPlansDto;
import com.etf.om.services.TariffPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tariff-plans")
@RequiredArgsConstructor
public class TariffPlanController {

    @Autowired
    private final TariffPlanService tariffPlanService;

    @PostMapping("bulk")
    public ResponseEntity<CreateTariffPlansBulkResponseDto> createOffer(@RequestBody CreateTariffPlanDto body) {
        return ResponseEntity.ok(this.tariffPlanService.createBulkTariffPlans(body));
    }

    @GetMapping("/offer/{offerId}")
    public ResponseEntity<List<TariffPlanDto>> getOfferById(@PathVariable UUID offerId) {
        return ResponseEntity.ok(this.tariffPlanService.getTariffPlansFromOffer(offerId));
    }

    @PutMapping("bulk")
    public ResponseEntity<String> updateBulkTariffPlans(@RequestBody UpdateTariffPlansDto body) {
        return ResponseEntity.ok(this.tariffPlanService.updateBulkTariffPlans(body));
    }

    @DeleteMapping("bulk")
    public ResponseEntity<String> deleteBulkTariffPlans(@RequestBody List<UUID> tpIds) {
        return ResponseEntity.ok(this.tariffPlanService.deleteBulkTariffPlans(tpIds));
    }
}
