package com.etf.om.dtos;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IdentifierCountNameDto {
    private String identifier;
    private Map<String, String> name;
    private Long count;
}
