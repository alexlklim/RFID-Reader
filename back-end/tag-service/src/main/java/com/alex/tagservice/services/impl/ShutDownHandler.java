package com.alex.tagservice.services.impl;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ShutDownHandler {
    private final RestTemplate restTemplate;
    @Value("${my.url.clear-items-from-product-service}")
    private String URL_clearItemsFromProductService;

    public ShutDownHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PreDestroy
    public void sendShutdownRequest() {
        String url = URL_clearItemsFromProductService;
        restTemplate.getForObject(url, String.class);
    }
}