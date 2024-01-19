package com.aselsis.iot.gateway.userTest;

import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.*;
import com.aselsis.iot.gateway.user.enums.UserRole;
import com.aselsis.iot.gateway.user.request.PostUserRequest;
import com.aselsis.iot.gateway.user.request.PutUserRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class PutUserTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenManager tokenManager;

    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    String path = "/users";
    User user;
    @PostConstruct
    public  void init(){
        PostUserRequest req = PostUserRequest.builder()
                .userName("TestUser").password("12345")
                .firstName("TestName").lastName("Test").userRole(UserRole.ROLE_ADMIN)
                .build();
       user = userRepository.save(User.create(req));

    }

    @Test
    public void putUser200Test() {

        PutUserRequest putUserRequest = PutUserRequest.builder()
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .firstName("TestPutFirstName")
                    .lastName("TestPutLastName")
                    .userRole(UserRole.ROLE_ADMIN)
                    .build();
       // RestAssured.defaultParser = Parser.JSON;
        String token = tokenManager.generateToken("ADMIN");
    ValidatableResponse then = given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + token)
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(putUserRequest)
                .put(path+"/" + user.getId())
                .then();
        then.statusCode(200);
        UserDTO response = then.extract().as(UserDTO.class);
        assertThat(response.getUserName(), equalTo("TestUser"));
            assertThat(response.getPassword(), equalTo("12345"));
            assertThat(response.getFirstName(), equalTo("TestPutFirstName"));
            assertThat(response.getLastName(), equalTo("TestPutLastName"));
            assertThat(response.getUserRole(), equalTo(UserRole.ROLE_ADMIN));
        }




    @Test
    public void putUser401Test(){
         PutUserRequest req = PutUserRequest.builder()
                 .userName("Test401").password("123453")
                 .firstName("Tfname").lastName("Tlname").userRole(UserRole.ROLE_ADMIN)
                 .build();
        RestAssured.defaultParser = Parser.JSON;
        ValidatableResponse then= given().baseUri("http://localhost:8080").accept(ContentType.JSON)
                .contentType(ContentType.JSON).body(req).put(path+"/"+user.getId()).then();
        then.statusCode(401);

    }

    @Test
    public void putUser403Test(){
        PutUserRequest req = PutUserRequest.builder()
                .userName("Test404Put").password("12345").firstName("Tfname")
                .lastName("Tlname").userRole(UserRole.ROLE_ADMIN)
                .build();
        RestAssured.defaultParser = Parser.JSON;

        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+"token")
                .contentType(ContentType.JSON).get(path+"/"+user.getId()).then();

        then.statusCode(403);

    }
    @Test
    public void putUser404Test(){

        PutUserRequest req = PutUserRequest.builder()
                .userName("Test404Put").password("12345").firstName("Tfname")
                .lastName("Tlname").userRole(UserRole.ROLE_ADMIN)
                .build();
        RestAssured.defaultParser = Parser.JSON;
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(req).put(path+"/"+user.getId()+"1").then();
        then.statusCode(404);
    }
}
