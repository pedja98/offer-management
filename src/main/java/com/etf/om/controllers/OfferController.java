package com.etf.om.controllers;

import com.etf.om.dtos.*;
import com.etf.om.enums.OfferStatus;
import com.etf.om.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/crm/{crmOfferId}")
    public ResponseEntity<OfferDto> getOfferByCrmOfferId(@PathVariable Long crmOfferId) {
        return ResponseEntity.ok(offerService.getOfferByCrmOfferId(crmOfferId));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createOffer(@RequestBody CreateOfferDto body) {
        return ResponseEntity.ok(new MessageResponse(this.offerService.createOffer(body)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable UUID id) {
        return ResponseEntity.ok(offerService.getOfferById(id));
    }

    @GetMapping
    public ResponseEntity<List<OfferDto>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> patchOffer(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> body
    ) {
        return ResponseEntity.ok(new MessageResponse(offerService.patchOffer(id, body)));
    }

    @PatchMapping("/{crmOfferId}/status")
    public ResponseEntity<MessageResponse> changeOfferStatus(
            @PathVariable Long crmOfferId,
            @RequestBody ChangeOfferStatusDto body
    ) {
        return ResponseEntity.ok(new MessageResponse(offerService.changeOfferStatus(crmOfferId, body)));
    }
}
