package com.aselsis.iot.gateway.clientApiKeysTest;

import com.aselsis.iot.gateway.clientapikey.ClientApiKey;
import com.aselsis.iot.gateway.clientapikey.ClientApiKeyRepository;
import com.aselsis.iot.gateway.clientapikey.ClientApiKeyService;
import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
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
public class DeleteClientApikeysTest {
    @Autowired
    private ClientApiKeyRepository clientApiKeyRepository;

    @Autowired
    private ClientApiKeyService service;

    @Autowired
    private TokenManager tokenManager;
    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathClientApiKeys = "/client-api-keys";

    @Test
    public void deleteClientApiKey204Test(){
        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp11")
                .room("room1")
                .build();
        ClientApiKey apikey = clientApiKeyRepository.save(ClientApiKey.create(req));
        String id= apikey.getId();
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).delete(pathClientApiKeys+"/"+id+"" ).then();
        then.statusCode(204);
    }
    @Test
    public void deleteClientApiKey401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).delete(pathClientApiKeys+"/").then();
        then.statusCode(401);

    }
    @Test
    public void deleteClientApiKey403Test(){
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).delete(pathClientApiKeys+"/").then();
        then.statusCode(403);
    }

    @Test
    public void deleteClientApiKey404Test(){
        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp11")
                .room("room2")
                .build();
        ClientApiKey apikey = clientApiKeyRepository.save(ClientApiKey.create(req));
        clientApiKeyRepository.deleteById(apikey.getId());
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).delete(pathClientApiKeys+"/"+apikey.getId()).then();
        then.statusCode(404);
    }


}
