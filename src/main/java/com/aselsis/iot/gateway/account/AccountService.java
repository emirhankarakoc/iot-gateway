package com.aselsis.iot.gateway.account;

import com.aselsis.iot.gateway.user.User;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    User getMe();
    ResponseEntity<String> login(LoginRequest loginRequest);
}
