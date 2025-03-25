package com.aselsis.iot.gateway.userTest;

import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.*;
import com.aselsis.iot.gateway.user.enums.UserRole;
import com.aselsis.iot.gateway.user.request.PostUserRequest;
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


import static org.hamcrest.MatcherAssert.assertThat;import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")

public class PostUserTest {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
     private TokenManager tokenManager;

    String path = "/users";
    @Test
    @Order(1)
    public void postUser201Test(){
        PostUserRequest req = PostUserRequest.builder()
                .userName("TestUserPostTest201").password("12345")
                .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
                .build();
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON).contentType(ContentType.JSON).body(req)
                .post(path).then();
        then.statusCode(201);
    }


   @Test
   @Order(2)
   public void postUser400Test(){
       PostUserRequest req = PostUserRequest.builder()
               .userName("TestUserPostTest400").password("12345")
               .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
               .build();
       userService.postUser(req);
       String token = tokenManager.generateToken("ADMIN");
       ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080").accept(ContentType.JSON)
               .header("Authorization", "Bearer "+token)
               .contentType(ContentType.JSON).body(req).post(path).then();
       then.statusCode(400);
    }

    @Test
    @Order(3)
    public void postUser401Test(){
        PostUserRequest req = PostUserRequest.builder()
                .userName("TestUserPostTest401").password("12345")
                .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
                .build();
        RestAssured.defaultParser = Parser.JSON;
        userRepository.save(User.create(req));
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080").accept(ContentType.JSON)
                .contentType(ContentType.JSON).body(req).post(path).then();
        then.statusCode(401);

    }

    @Test
    @Order(4)
    public void postUser403Test(){
        PostUserRequest req = PostUserRequest.builder()
                .userName("TestUserPostTest403").password("12345")
                .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
                .build();
        RestAssured.defaultParser = Parser.JSON;
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON).contentType(ContentType.JSON).body(req).post(path).then();
        then.statusCode(403);

    }

}
