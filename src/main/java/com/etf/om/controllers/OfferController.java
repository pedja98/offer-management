package com.etf.om.controllers;

import com.etf.om.dtos.CreateOfferDto;
import com.etf.om.dtos.CreateOfferResponseDto;
import com.etf.om.services.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
