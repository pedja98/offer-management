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

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.etf.om.common.OmConstants.ErrorCodes.OFFER_NOT_FOUND;
import static com.etf.om.common.OmConstants.SuccessCodes.OFFER_UPDATED;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    @Transactional
    public CreateOfferResponseDto createOffer(CreateOfferDto dto) {
        System.out.println(dto.getName());
        Offer offer = Offer.builder()
                .name(dto.getName())
                .mmc(0)
                .contractObligation(0)
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

    @Transactional
    public String patchOffer(UUID id, Map<String, Object> updates) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(OFFER_NOT_FOUND));

        Set<String> allowedFields = Arrays.stream(Offer.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toSet());

        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException("Field '" + key + "' is not allowed to be updated");
            }

            try {
                Field field = Offer.class.getDeclaredField(key);
                field.setAccessible(true);

                Class<?> fieldType = field.getType();
                Object convertedValue;

                if (fieldType.equals(String.class)) {
                    convertedValue = value.toString();
                } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    convertedValue = Integer.parseInt(value.toString());
                } else if (fieldType.isEnum()) {
                    convertedValue = Enum.valueOf((Class<Enum>) fieldType, value.toString());
                } else {
                    throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
                }

                field.set(offer, convertedValue);

            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("Field '" + key + "' does not exist on Offer");
            } catch (IllegalAccessException | IllegalArgumentException e) {
                throw new RuntimeException("Failed to set field '" + key + "'", e);
            }
        }

        offerRepository.save(offer);
        return OFFER_UPDATED;
    }

}
