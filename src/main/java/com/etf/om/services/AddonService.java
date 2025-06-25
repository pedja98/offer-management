package com.etf.om.services;

import com.etf.om.dtos.AddonDto;
import com.etf.om.dtos.CreateAddonDto;
import com.etf.om.entities.Addon;
import com.etf.om.entities.Offer;
import com.etf.om.exceptions.ItemNotFoundException;
import com.etf.om.filters.SetCurrentUserFilter;
import com.etf.om.repositories.AddonRepository;
import com.etf.om.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.etf.om.common.OmConstants.ErrorCodes.*;
import static com.etf.om.common.OmConstants.SuccessCodes.ADDON_CREATED;
import static com.etf.om.common.OmConstants.SuccessCodes.ADDONS_DELETED;

@Service
public class AddonService {

    @Autowired
    private AddonRepository addonRepository;

    @Autowired
    private OfferRepository offerRepository;

    public String createAddon(CreateAddonDto body) {
        Offer offer = offerRepository.findById(body.getOmOfferId())
                .orElseThrow(() -> new ItemNotFoundException(OFFER_NOT_FOUND));

        Addon addon = Addon.builder()
                .name(body.getName())
                .identifier(body.getIdentifier())
                .price(body.getPrice())
                .tariffPlanIdentifier(body.getTariffPlanIdentifier())
                .offer(offer)
                .createdByUser(SetCurrentUserFilter.getCurrentUsername())
                .build();

        UUID addonId = addonRepository.save(addon).getId();

        return ADDON_CREATED;
    }

    public String deleteAddonBulk(List<UUID> addonIds) {
        for (UUID uuid : addonIds) {
            Addon addon = this.addonRepository.findById(uuid)
                    .orElseThrow(() -> new RuntimeException(TARIFF_PLAN_NOT_FOUND));
            this.addonRepository.delete(addon);
        }

        return ADDONS_DELETED;
    }

    public List<AddonDto> getAddonsByOfferIdAndTariffPlanIdentifier(UUID offerId, String tariffPlanIdentifier) {
        return this.addonRepository.findAllAddonDtosByOfferIdAndTariffPlanIdentifier(offerId, tariffPlanIdentifier);
    }
}
