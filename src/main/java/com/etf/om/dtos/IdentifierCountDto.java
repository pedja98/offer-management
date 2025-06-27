package com.etf.om.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdentifierCountDto {
    private String identifier;
    private Long count;
}
