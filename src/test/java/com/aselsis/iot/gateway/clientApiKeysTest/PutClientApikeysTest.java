package com.aselsis.iot.gateway.clientApiKeysTest;

import com.aselsis.iot.gateway.clientapikey.ClientApiKey;
import com.aselsis.iot.gateway.clientapikey.ClientApiKeyRepository;
import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
import com.aselsis.iot.gateway.clientapikey.request.PutClientApiKeyRequest;
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
public class PutClientApikeysTest {


    @Autowired
    private ClientApiKeyRepository clientApiKeyRepository;
    @Autowired
    private TokenManager tokenManager;


    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathClientApiKeys = "/client-api-keys";
    @Test
    public void putClient200Test(){
        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("room3")
                .room("TestTopic11")
                .build();
        ClientApiKey apikey = clientApiKeyRepository.save(ClientApiKey.create(req));

        PutClientApiKeyRequest request = PutClientApiKeyRequest.builder()
                .room("room2")
                .appName("TestTopic12")
                .build();

        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).body(request).put(pathClientApiKeys+"/" +apikey.getId()+"").then();
        then.statusCode(200);

    }

    @Test
    public void putClient401Test(){

        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .accept(ContentType.JSON).contentType(ContentType.JSON).
                put(pathClientApiKeys+"/").then();
        then.statusCode(401);

    }

    @Test
    public void putClient403Test(){
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).delete(pathClientApiKeys+"/").then();
        then.statusCode(403);
    }

    @Test
    public void putClientd404Test(){
        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("room3")
                .room("TestTopic11")
                .build();
        ClientApiKey apikey = clientApiKeyRepository.save(ClientApiKey.create(req));
        clientApiKeyRepository.deleteById(apikey.getId());

        PutClientApiKeyRequest reqUpdate = PutClientApiKeyRequest.builder()
                .appName("TestApp")
                .room("room1")
                .build();
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).body("reqUpdate").get(pathClientApiKeys+"/"+apikey.getId()).then();
        then.statusCode(404);
    }
}
