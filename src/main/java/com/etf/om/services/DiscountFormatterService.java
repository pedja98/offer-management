package com.etf.om.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DiscountFormatterService {
    private final WebClient webClient;

    public DiscountFormatterService(@Value("${discount.service.url}") String discountServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(discountServiceUrl)
                .build();
    }

    public WebClient.RequestHeadersUriSpec<?> get() {
        return webClient.get();
    }
}