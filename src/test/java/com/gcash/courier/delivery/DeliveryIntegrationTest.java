package com.gcash.courier.delivery;

import com.gcash.courier.TestcontainersConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
class DeliveryIntegrationTest {
    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }


    @Test
    void testDelivery() {
        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0f, 1.0f, 1.0f, 1.0f, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    void testRejectDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(51.0f, 1.0f, 1.0f, 1.0f, "test"))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void testHeavyDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(12.0f, 1.0f, 1.0f, 1.0f, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(240).compareTo(deliveryCost.cost())));
    }

    @Test
    void testSmallDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0f, 10.0f, 10.0f, 10.0f, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(30).compareTo(deliveryCost.cost())));
    }

    @Test
    void testMediumDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0f, 20.0f, 10.0f, 10.0f, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(80).compareTo(deliveryCost.cost())));
    }

    @Test
    void testLargeDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0f, 30.0f, 10.0f, 10.0f, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(150).compareTo(deliveryCost.cost())));
    }


}
