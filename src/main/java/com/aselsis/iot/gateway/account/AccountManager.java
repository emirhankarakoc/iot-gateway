package com.aselsis.iot.gateway.account;

import com.aselsis.iot.gateway.security.SecurityContexUtil;
import com.aselsis.iot.gateway.security.TokenManager;
import com.aselsis.iot.gateway.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountManager implements AccountService {
    private final SecurityContexUtil securityContextUtil;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;
    @Override
    public User getMe() {
        return securityContextUtil.getCurrentUser();
    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
              authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        return ResponseEntity.ok(tokenManager.generateToken(loginRequest.getUsername()));
    }
}
