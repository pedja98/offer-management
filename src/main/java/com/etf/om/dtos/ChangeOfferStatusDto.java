package com.etf.om.dtos;

import com.etf.om.enums.OfferStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeOfferStatusDto {
    private OfferStatus status;
}
