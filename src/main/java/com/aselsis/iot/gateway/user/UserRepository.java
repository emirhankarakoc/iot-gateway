package com.aselsis.iot.gateway.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

   Optional<User> findByUserName(String userName);
   Optional<User> findById(String id);
   List<User> findAll();
   boolean existsByUserName(String userName);
   void deleteByUserName(String userName);
   boolean existsById(String id);

}
