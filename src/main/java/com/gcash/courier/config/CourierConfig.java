package com.gcash.courier.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.gcash.courier")
public class CourierConfig {
    private String voucherHost;
    private double largePrice;
    private double mediumPrice;
    private double smallPrice;
    private double heavyPrice;

}
