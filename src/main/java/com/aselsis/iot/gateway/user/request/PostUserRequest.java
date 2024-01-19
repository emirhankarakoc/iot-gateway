package com.aselsis.iot.gateway.user.request;

import com.aselsis.iot.gateway.user.enums.UserRole;
import lombok.*;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class PostUserRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private  String  password;

    @NonNull
    private UserRole userRole;
}
