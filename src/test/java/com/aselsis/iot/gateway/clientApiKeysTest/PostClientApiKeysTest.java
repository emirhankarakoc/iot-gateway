package com.aselsis.iot.gateway.clientApiKeysTest;

import com.aselsis.iot.gateway.clientapikey.ClientApiKeyRepository;
import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
import com.aselsis.iot.gateway.security.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class PostClientApiKeysTest {

    @Autowired
    private ClientApiKeyRepository clientApiKeyRepository;
    @Autowired
    private TokenManager tokenManager;
    String pathClientApiKeys = "/client-api-keys";

    @Test
    public void postClient201Test(){

        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp11")
                .room("room4")
                .build();

        String token = tokenManager.generateToken("ADMIN");

        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON).contentType(ContentType.JSON).body(req).post(pathClientApiKeys).then();
        then.statusCode(201);

    }

    @Test
    public void postUser401Test(){

        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp1")
                .room("room5")
                .build();
        RestAssured.defaultParser = Parser.JSON;
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .accept(ContentType.JSON).contentType(ContentType.JSON).body(req).post(pathClientApiKeys).then();
        then.statusCode(401);

    }

    @Test
    public void postUser403Test(){

        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp2")
                .room("room6")
                .build();
        RestAssured.defaultParser = Parser.JSON;
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON).contentType(ContentType.JSON).body(req).post(pathClientApiKeys).then();
        then.statusCode(403);

    }
}
