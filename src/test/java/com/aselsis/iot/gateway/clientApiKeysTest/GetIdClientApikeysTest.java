
package com.aselsis.iot.gateway.clientApiKeysTest;

import com.aselsis.iot.gateway.clientapikey.ClientApiKey;
import com.aselsis.iot.gateway.clientapikey.ClientApiKeyRepository;
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
public class GetIdClientApikeysTest {
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
    public void getIdClientApiKey200Test(){
        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp11")
                .room("room7")
                .build();
      ClientApiKey apikey = clientApiKeyRepository.save(ClientApiKey.create(req));
        String id= apikey.getId();


        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).get(pathClientApiKeys+"/"+id+"" ).then();
        then.statusCode(200);
    }
    @Test
    public void getIdClientApiKey401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).get(pathClientApiKeys+"/").then();
        then.statusCode(401);

    }
    @Test
    public void getIdClientApiKey403Test(){
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).get(pathClientApiKeys+"/").then();
        then.statusCode(403);
    }

    @Test
    public void getIdClientApiKeyd404Test(){
        PostClientApiKeyRequest req = PostClientApiKeyRequest.builder()
                .appName("TestApp1")
                .room("room3")
                .build();
        ClientApiKey cak = clientApiKeyRepository.save(ClientApiKey.create(req));
        clientApiKeyRepository.delete(cak);
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).body("").get(pathClientApiKeys+"/"+cak.getId()).then();
        then.statusCode(404);
    }




}

