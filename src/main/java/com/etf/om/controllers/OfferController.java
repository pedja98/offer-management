package com.etf.om.controllers;

import com.etf.om.dtos.*;
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

    @PostMapping
    public ResponseEntity<CreateOfferResponseDto> createOffer(@RequestBody CreateOfferDto dto) {
        return ResponseEntity.ok(this.offerService.createOffer(dto));
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
}
