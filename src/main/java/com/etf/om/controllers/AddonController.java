package com.etf.om.controllers;

import com.etf.om.dtos.AddonDto;
import com.etf.om.dtos.CreateAddonDto;
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
    public ResponseEntity<MessageResponse> createAddon(@RequestBody CreateAddonDto body) {
        return ResponseEntity.ok(new MessageResponse(addonService.createAddon(body)));
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<MessageResponse> deleteAddons(@RequestBody List<UUID>  addonIds) {
        return ResponseEntity.ok(new MessageResponse(this.addonService.deleteAddonBulk(addonIds)));
    }

    @GetMapping
    public List<AddonDto> getAddonsByOfferIdAndTariffPlanIdentifier(@RequestParam UUID offerId,
                                                          @RequestParam String tariffPlanIdentifier) {
        return addonService.getAddonsByOfferIdAndTariffPlanIdentifier(offerId, tariffPlanIdentifier);
    }
}
