package com.aselsis.iot.gateway.deviceApiKeyTest;

import com.aselsis.iot.gateway.deviceapikey.*;
import com.aselsis.iot.gateway.deviceapikey.enums.ProtocolType;
import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.deviceapikey.request.PutDeviceApiKeyRequest;
import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasLength;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class PutDAKTest {
    @Autowired
    private DeviceApiKeyRepository repository;
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    private LocalDate gecerlilikTarihi1aylik = LocalDate.now().plusMonths(1);
    DeviceApiKey dak;
    String pathDeviceApiKey = "/device-api-keys";

    @PostConstruct
    public  void init(){
        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .protocol(ProtocolType.Tcp)
                .validUntil(gecerlilikTarihi1aylik)
                .build();
         dak = repository.save(DeviceApiKey.create(req));
    }

    @Test
    public void putDAK200Test() {
        PutDeviceApiKeyRequest putDAKRequest = PutDeviceApiKeyRequest.builder()
                .validUntil(dak.getValidUntil())
                .build();
        String token = tokenManager.generateToken("ADMIN");

        ValidatableResponse then = given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(putDAKRequest)
                .put(pathDeviceApiKey+"/"+dak.getId()+"")
                .then();

        RestAssured.defaultParser = Parser.JSON;
        then.statusCode(200);
        DeviceApiKeyDTO response = then.extract().as(DeviceApiKeyDTO.class);

        assertThat(response.getValidUntil(), equalTo(gecerlilikTarihi1aylik));
        assertThat(response.getToken(), hasLength(32));

    }


    @Test
    public void putDAK401Test(){

        PutDeviceApiKeyRequest req = PutDeviceApiKeyRequest.builder()
                .validUntil(gecerlilikTarihi1aylik)
                .build();
        RestAssured.defaultParser = Parser.JSON;
        ValidatableResponse then= given().baseUri("http://localhost:8080").accept(ContentType.JSON)
                .contentType(ContentType.JSON).body(req).put(pathDeviceApiKey+"/").then();
        then.statusCode(401);

    }

    @Test
    public void putDAK403Test(){
            String token = tokenManager.generateToken("USER");
            ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                    .header("Authorization", "Bearer "+"token")
                    .contentType(ContentType.JSON).put(pathDeviceApiKey+"/").then();
            then.statusCode(403);
    }
    @Test
    public void putDAK404Test(){

        PutDeviceApiKeyRequest  req = PutDeviceApiKeyRequest.builder()
                .validUntil(gecerlilikTarihi1aylik)
                .build();

        RestAssured.defaultParser = Parser.JSON;
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(req).put(pathDeviceApiKey+"/"+"notValidDAKId").then();
        then.statusCode(404);
    }

}
