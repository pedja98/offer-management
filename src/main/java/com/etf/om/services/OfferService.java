package com.etf.om.services;

import com.etf.om.dtos.CreateOfferDto;
import com.etf.om.dtos.CreateOfferResponseDto;
import com.etf.om.entities.Offer;
import com.etf.om.enums.OfferApprovalStatus;
import com.etf.om.enums.OfferStatus;
import com.etf.om.filters.SetCurrentUserFilter;
import com.etf.om.repositories.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
