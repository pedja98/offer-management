package com.etf.om.controllers;

import com.etf.om.dtos.AddonDto;
import com.etf.om.dtos.CreateAddonDto;
import com.etf.om.dtos.CreateAddonResponseDto;
import com.etf.om.dtos.MessageResponse;
import com.etf.om.services.AddonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addons")
@RequiredArgsConstructor
public class AddonController {

    @Autowired
    private AddonService addonService;

    @PostMapping
    public ResponseEntity<CreateAddonResponseDto> createAddon(@RequestBody CreateAddonDto body) {
        return ResponseEntity.ok(addonService.createAddon(body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAddon(@PathVariable UUID id) {
        return ResponseEntity.ok(new MessageResponse(this.addonService.deleteAddon(id)));
    }

    @GetMapping
    public List<AddonDto> getAddonsByOfferIdAndTariffPlanIdentifier(@RequestParam UUID offerId,
                                                          @RequestParam String tariffPlanIdentifier) {
        return addonService.getAddonsByOfferIdAndTariffPlanIdentifier(offerId, tariffPlanIdentifier);
    }
}
