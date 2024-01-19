package com.aselsis.iot.gateway.accountTest;

import com.aselsis.iot.gateway.account.LoginRequest;
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
public class LoginTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;
    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    String pathLogin = "/accounts/login";
    @Test
    public void loginTest200Test(){
        LoginRequest reg = LoginRequest.builder()
                .userName("ADMIN")
                .password("12345")
                .build();
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(reg).post(pathLogin).then();
        then.statusCode(200);
    }
    @Test
    public void loginTest401Test(){
        LoginRequest reg = LoginRequest.builder()
                .userName("ADMIN")
                .password("")
                .build();
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(reg).post(pathLogin).then();
        then.statusCode(401);

    }

}
