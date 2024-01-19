package com.aselsis.iot.gateway.security;

import com.aselsis.iot.gateway.user.User;
import com.aselsis.iot.gateway.user.UserRepository;
import com.aselsis.iot.gateway.user.enums.UserRole;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserDetailsManager implements UserDetailsService {

    private  UserRepository userRepository;
    @PostConstruct
    public void init() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            User admin = new User();
            admin.setUserName("ADMIN");
            admin.setUserRole(UserRole.ROLE_ADMIN);
            admin.setPassword("12345");
            userRepository.save(admin);

            User user = new User();
            user.setUserName("USER");
            user.setUserRole(UserRole.ROLE_USER);
            user.setPassword("12345");
            userRepository.save(user);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUserName(userName);

        User user = optionalUser.orElseThrow(() -> new EntityNotFoundException("kullanıcı bulunamadı."));

        return new UserCustomDetails(user);
    }
}
