package com.gcash.courier;

import org.springframework.boot.SpringApplication;

public class TestCourierApplication {

    public static void main(String[] args) {
        SpringApplication.from(CourierApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
