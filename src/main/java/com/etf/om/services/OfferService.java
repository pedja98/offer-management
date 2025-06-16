package com.etf.om.services;

import com.etf.om.dtos.CreateOfferDto;
import com.etf.om.dtos.CreateOfferResponseDto;
import com.etf.om.dtos.OfferDto;
import com.etf.om.entities.Offer;
import com.etf.om.enums.OfferApprovalStatus;
import com.etf.om.enums.OfferStatus;
import com.etf.om.exceptions.ItemNotFoundException;
import com.etf.om.filters.SetCurrentUserFilter;
import com.etf.om.repositories.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.etf.om.common.OmConstants.ErrorCodes.OFFER_NOT_FOUND;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Transactional
    public CreateOfferResponseDto createOffer(CreateOfferDto dto) {
        Offer offer = Offer.builder()
                .name(dto.getName())
                .approvalStatus(OfferApprovalStatus.NONE)
                .status(OfferStatus.DRAFT)
                .createdByUsername(SetCurrentUserFilter.getCurrentUsername())
                .build();

        return (new CreateOfferResponseDto(offerRepository.save(offer).getId()));
    }

    public OfferDto getOfferById(UUID id) {
        return this.offerRepository.findOfferDtoById(id)
                .orElseThrow(() -> new ItemNotFoundException(OFFER_NOT_FOUND));
    }

    public List<OfferDto> getAllOffers() {
        return this.offerRepository.findAllOfferDto();
    }
}
