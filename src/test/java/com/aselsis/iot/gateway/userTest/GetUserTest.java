package com.aselsis.iot.gateway.userTest;

import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.UserRepository;
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
public class GetUserTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;
    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    String path = "/users";
    @Test
    public void getUser200Test(){
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).get(path).then();
        then.statusCode(200);
    }
    @Test
    public void getUser401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).get(path).then();
        then.statusCode(401);

    }
    @Test
    public void getUser403Test(){
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).get(path).then();
        then.statusCode(403);
    }

}
