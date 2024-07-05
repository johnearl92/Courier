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

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testRejectDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(51.0, 1.0, 1.0, 1.0, "test"))
                .exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    public void testHeavyDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(12.0, 1.0, 1.0, 1.0, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(240).compareTo(deliveryCost.cost())));
    }

    @Test
    public void testSmallDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0, 10.0, 10.0, 10.0, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(30).compareTo(deliveryCost.cost())));
    }

    @Test
    public void testMediumDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0, 20.0, 10.0, 10.0, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(80).compareTo(deliveryCost.cost())));
    }

    @Test
    public void testLargeDeliveryCost() {

        webTestClient
                .post().uri("/api/v1/deliveries/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-api-key", UUID.randomUUID().toString())
                .bodyValue(new Parcel(1.0, 30.0, 10.0, 10.0, "test"))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(DeliveryCost.class)
                .value(deliveryCost ->
                        assertEquals(0, BigDecimal.valueOf(150).compareTo(deliveryCost.cost())));
    }


}
