package com.etf.om.controllers;

import com.etf.om.dtos.*;
import com.etf.om.services.TariffPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tariff-plans")
@RequiredArgsConstructor
public class TariffPlanController {

    @Autowired
    private final TariffPlanService tariffPlanService;

    @PostMapping("bulk")
    public ResponseEntity<MessageResponse> createOffer(@RequestBody CreateTariffPlanDto body) {
        return ResponseEntity.ok(new MessageResponse(this.tariffPlanService.createBulkTariffPlans(body)));
    }

    @GetMapping("/offer/{offerId}")
    public ResponseEntity<List<TariffPlanDto>> getOfferById(@PathVariable UUID offerId) {
        return ResponseEntity.ok(this.tariffPlanService.getTariffPlansFromOffer(offerId));
    }

    @PutMapping("offer/{offerId}/bulk")
    public ResponseEntity<MessageResponse> updateBulkTariffPlans(@RequestBody UpdateTariffPlansDto body, @PathVariable UUID offerId) {
        return ResponseEntity.ok(new MessageResponse(this.tariffPlanService.updateBulkTariffPlans(body, offerId)));
    }

    @DeleteMapping("offer/{offerId}/bulk")
    public ResponseEntity<MessageResponse> deleteBulkTariffPlans(@RequestBody List<UUID> tpIds, @PathVariable UUID offerId) {
        return ResponseEntity.ok(new MessageResponse(this.tariffPlanService.deleteBulkTariffPlans(tpIds, offerId)));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<MessageResponse> deactivateOfferTariffPlan(
            @PathVariable UUID id,
            @RequestBody DeactivateTariffPlanDto body
    ) {
        return ResponseEntity.ok(new MessageResponse(this.tariffPlanService.deactivateOfferTariffPlan(id, body)));
    }

    @GetMapping("offer/{offerId}/identifier-counts")
    public ResponseEntity<Map<String, Map<String, Object>>> getTariffPlanCountsWithNames(@PathVariable UUID offerId) {
        return ResponseEntity.ok(this.tariffPlanService.getTariffPlanCountsWithNames(offerId));
    }

}
