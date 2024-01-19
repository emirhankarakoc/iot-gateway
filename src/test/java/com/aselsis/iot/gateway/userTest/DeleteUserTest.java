package com.aselsis.iot.gateway.userTest;

import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.request.PostUserRequest;
import com.aselsis.iot.gateway.user.User;
import com.aselsis.iot.gateway.user.UserRepository;
import com.aselsis.iot.gateway.user.enums.UserRole;
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
public class DeleteUserTest {
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
    public void deleteUser204Test(){
        PostUserRequest req = PostUserRequest.builder()
                .userName("TestUser").password("12345")
                .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
                .build();
        User user = userRepository.save(User.create(req));
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).body("").delete(path+"/"+user.getId()+"" ).then();
        then.statusCode(204);
    }
    @Test
    public void deleteUser401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body("").delete(path+"/").then();
        then.statusCode(401);
    }
    @Test
    public void deleteUser403Test(){

        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")

                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).get(path+"/").then();

        then.statusCode(403);
    }
    @Test
    public void deleteUser404Test(){
        PostUserRequest req = PostUserRequest.builder()
                .userName("TestUserDelete404").password("12345")
                .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
                .build();
        userRepository.save(User.create(req));
        User user = userRepository.findByUserName("TestUserDelete404").orElseThrow();
        userRepository.delete(user);
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).body("").delete(path+"/"+user.getId()).then();
        then.statusCode(404);
    }
}
