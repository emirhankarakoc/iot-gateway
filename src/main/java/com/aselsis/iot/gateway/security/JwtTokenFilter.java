package com.aselsis.iot.gateway.security;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    private TokenManager tokenManager;

    private final UserDetailsManager userDetailsManager;


    protected void doWork(HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse
    ) throws ServletException, IOException {

        String username = null;
        String token = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        /**
         * "Bearer 123hab2355"
         */
        final String authHeader = httpServletRequest.getHeader(HEADER_STRING);

        if (authHeader == null){
             return;
      }

        if (!authHeader.startsWith(TOKEN_PREFIX)) {
            return;
        }
        token = authHeader.replace(TOKEN_PREFIX, "");

        username = tokenManager.getUsernameToken(token);

        UserCustomDetails userDetails = (UserCustomDetails) userDetailsManager.loadUserByUsername(username);

        if (tokenManager.tokenValidate(token)) {
            UsernamePasswordAuthenticationToken upassToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(upassToken);
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        try {
            doWork(req, res);
        } catch (Exception e) {
            e.getCause();
        }

        chain.doFilter(req, res);
    }
}
