package com.aselsis.iot.gateway.deviceApiKeyTest;

import com.aselsis.iot.gateway.deviceapikey.*;
import com.aselsis.iot.gateway.deviceapikey.enums.ProtocolType;
import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.security.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")

public class PostDAKTest {

    @Autowired
    private DeviceApiKeyRepository deviceApiKeyRepository;

    @Autowired
    private DeviceApiKeyService deviceApiKeyService;
    @Autowired
    private TokenManager tokenManager;
    String pathDeviceApiKey = "/device-api-keys";
     LocalDate gecerlilikTarihi1aylik = LocalDate.now().plusMonths(1);

    @Test
    @Order(1)
    public void postDAK201Test() {
        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .protocol(ProtocolType.Tcp)
                .validUntil(gecerlilikTarihi1aylik)
                .build();
        String token = tokenManager.generateToken("ADMIN");

        // Set the default response parser to JSON
        RestAssured.defaultParser = Parser.JSON;

        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON).contentType(ContentType.JSON).body(req)
                .post(pathDeviceApiKey).then();

        then.statusCode(201);
        DeviceApiKeyDTO response = then.extract().body().as(DeviceApiKeyDTO.class);
        assertThat(response.getProtocol(), equalTo(ProtocolType.Tcp));
        assertThat(response.getValidUntil(), equalTo(gecerlilikTarihi1aylik));
        assertThat(response.getToken(), hasLength(32));
    }

    @Test
    @Order(2)
    public void postDAK401Test(){

        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .validUntil(gecerlilikTarihi1aylik)
                .protocol(ProtocolType.Tcp)
                 .build();
        RestAssured.defaultParser = Parser.JSON;
        deviceApiKeyRepository.save(DeviceApiKey.create(req));
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080").accept(ContentType.JSON)
                .contentType(ContentType.JSON).body(req).post(pathDeviceApiKey).then();
        then.statusCode(401);

    }

    @Test
    @Order(3)
    public void postDAK403Test(){

        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .validUntil(gecerlilikTarihi1aylik)
                .protocol(ProtocolType.Tcp)
                .build();

        RestAssured.defaultParser = Parser.JSON;

        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given()
                .baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(req).post(pathDeviceApiKey).then()
                .statusCode(403);


    }
}
