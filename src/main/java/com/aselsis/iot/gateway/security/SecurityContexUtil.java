package com.aselsis.iot.gateway.security;

import com.aselsis.iot.gateway.exceptions.UnAuthorizedException;
import com.aselsis.iot.gateway.user.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContexUtil implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }
    public User getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof AnonymousAuthenticationToken) {
            throw new UnAuthorizedException("Lütfen giriş yapın");
        }
        UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
