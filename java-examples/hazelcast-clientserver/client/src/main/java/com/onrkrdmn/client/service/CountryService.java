package com.onrkrdmn.client.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private String country = "Germany";

    @Cacheable("country")
    public String getCountry() {
        return country;
    }

    @CachePut(value = "country", key = "#country + 1")
    public String setCountry(String country) {
        this.country = country;
        return country;
    }
}
