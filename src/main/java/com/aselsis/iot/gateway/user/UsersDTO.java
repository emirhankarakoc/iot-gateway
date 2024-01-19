package com.aselsis.iot.gateway.user;

import com.aselsis.iot.gateway.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class UsersDTO {
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private UserRole userRole;
}
