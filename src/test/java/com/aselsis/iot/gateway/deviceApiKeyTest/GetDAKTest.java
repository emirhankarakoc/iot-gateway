package com.aselsis.iot.gateway.deviceApiKeyTest;

import com.aselsis.iot.gateway.deviceapikey.DeviceApiKeyRepository;
import com.aselsis.iot.gateway.security.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class GetDAKTest {
    @Autowired
    private DeviceApiKeyRepository deviceApiKeyRepository;

    @Autowired
    private TokenManager tokenManager;
    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    String pathDeviceApiKey = "/device-api-keys";
    @Test
    public void getDAK200Test(){
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).get(pathDeviceApiKey).then();
        then.statusCode(200);
    }
    @Test
    public void getDAK401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).get(pathDeviceApiKey).then();
        then.statusCode(401);

    }
    @Test
    public void getDAK403Test(){
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).get(pathDeviceApiKey).then();
        then.statusCode(403);
    }

}
