package com.aselsis.iot.gateway.clientapikey;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientApiKeyRepository extends JpaRepository<ClientApiKey,String> {
    Optional<ClientApiKey> findByToken(String token);


}
