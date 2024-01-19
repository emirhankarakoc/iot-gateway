package com.aselsis.iot.gateway.deviceApiKeyTest;
import com.aselsis.iot.gateway.deviceapikey.DeviceApiKey;
import com.aselsis.iot.gateway.deviceapikey.DeviceApiKeyRepository;
import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.deviceapikey.enums.ProtocolType;
import com.aselsis.iot.gateway.security.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.util.Locale;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class DeleteDAKTest {
    @Autowired
    private DeviceApiKeyRepository deviceApiKeyRepository;

    @Autowired
    private TokenManager tokenManager;

    @BeforeAll
    public static void beforeAll(){
        Locale.setDefault(Locale.ENGLISH);
    }
    private LocalDate gecerlilikTarihi1aylik = LocalDate.now().plusMonths(1);
    String pathDeviceApiKey = "/device-api-keys";

    @Test
        public void deleteDAK204Test(){
        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .validUntil(gecerlilikTarihi1aylik)
                .protocol(ProtocolType.Tcp)
                .build();

        DeviceApiKey createdApiKey
                = deviceApiKeyRepository.save(DeviceApiKey.create(req));


        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured
                .given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)

                .contentType(ContentType.JSON).body("")
                .delete(pathDeviceApiKey+"/"+createdApiKey.getId()+"" )
                .then();
        then.statusCode(204);
    }
    @Test
    public void deleteDAK401Test(){
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body("").delete(pathDeviceApiKey+"/").then();
        then.statusCode(401);
    }
    @Test
    public void deleteDAK403Test(){
        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .validUntil(gecerlilikTarihi1aylik)
                .protocol(ProtocolType.Tcp)
                .build();
        DeviceApiKey createdApiKey
                = deviceApiKeyRepository.save(DeviceApiKey.create(req));
        String token = tokenManager.generateToken("USER");
        ValidatableResponse then= RestAssured
                .given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)

                .contentType(ContentType.JSON).body("")
                .delete(pathDeviceApiKey+"/"+createdApiKey.getId()+"" )
                .then();
        then.statusCode(403);
    }
    @Test
    public void deleteDAK404Test(){
        PostDeviceApiKeyRequest req = PostDeviceApiKeyRequest.builder()
                .protocol(ProtocolType.Tcp)
                .validUntil(gecerlilikTarihi1aylik)
                .build();
        DeviceApiKey dak =  deviceApiKeyRepository.save(DeviceApiKey.create(req));
        deviceApiKeyRepository.delete(dak);
        String token = tokenManager.generateToken("ADMIN");
        ValidatableResponse then= RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer "+token)
                .contentType(ContentType.JSON).body("").delete(pathDeviceApiKey+"/"+dak.getId()).then();
        then.statusCode(404);
    }
}


