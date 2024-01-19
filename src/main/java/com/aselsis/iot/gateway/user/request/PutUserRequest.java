package com.aselsis.iot.gateway.user.request;

import com.aselsis.iot.gateway.user.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class PutUserRequest {

    private String userName;
    private  String  password;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
