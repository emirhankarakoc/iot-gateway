package com.aselsis.iot.gateway.account;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class LoginRequest {

    private String userName;

    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
