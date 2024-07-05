package com.gcash.courier.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.gcash.courier")
public class CourierConfig {
    private String voucherHost;
    private BigDecimal largePrice;
    private BigDecimal mediumPrice;
    private BigDecimal smallPrice;
    private BigDecimal heavyPrice;

}
