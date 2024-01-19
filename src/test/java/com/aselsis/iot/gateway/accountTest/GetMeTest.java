package com.aselsis.iot.gateway.accountTest;


import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.User;
import com.aselsis.iot.gateway.user.UserRepository;
import com.aselsis.iot.gateway.user.UserService;
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
public class GetMeTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;
    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    String pathMe = "/accounts/me";
    @Test
  public void getMe200Test(){
      User user = userRepository.findByUserName("ADMIN").orElseThrow();
      String token = tokenManager.generateToken("ADMIN");
      ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
              .header("Authorization", "Bearer "+token)
              .contentType(ContentType.JSON)
              .get(pathMe )
              .then();
      then.statusCode(200);
  }
    @Test
    public void getMe401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .get(pathMe)
                .then();
        then.statusCode(401);
    }


}
