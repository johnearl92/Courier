package com.gcash.courier.delivery;

import com.gcash.courier.TestcontainersConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.gcash.courier.delivery.model.DeliveryCost;
import com.gcash.courier.delivery.model.Parcel;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class DeliveryIntegrationTest {
    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testDelivery() {
        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0, 1.0, 1.0, 1.0, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(Assertions::assertNotNull);
    }
}
