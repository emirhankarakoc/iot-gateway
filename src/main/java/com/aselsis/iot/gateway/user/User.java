package com.aselsis.iot.gateway.user;

import com.aselsis.iot.gateway.user.enums.UserRole;
import com.aselsis.iot.gateway.user.request.PostUserRequest;
import com.aselsis.iot.gateway.user.request.PutUserRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userName;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private  String  password;

    public static User create(PostUserRequest request){
        User response = new User();
        response.userName = request.getUserName();
        response.password = request.getPassword();
        response.firstName = request.getFirstName();
        response.lastName = request.getLastName();
        response.userRole = request.getUserRole();
        return response;}

    public static User update(User user, PutUserRequest request){
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserRole(request.getUserRole());
        return user;
    }
}
