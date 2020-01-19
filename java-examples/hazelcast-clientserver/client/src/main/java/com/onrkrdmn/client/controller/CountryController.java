package com.onrkrdmn.client.controller;

import com.onrkrdmn.client.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/country")
public class CountryController {

    private static Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    @RequestMapping
    public String getCountry() {
        String logFormat = "%s call took %d millis with result: %s";
        long start1 = System.nanoTime();
        String country = countryService.getCountry();
        long end1 = System.nanoTime();
        LOGGER.info(String.format(logFormat, "Rest", TimeUnit.NANOSECONDS.toMillis(end1 - start1), country));
        return country;
    }

    @RequestMapping("/{country}")
    public String setCity(@PathVariable String country) {
        return countryService.setCountry(country);
    }
}
