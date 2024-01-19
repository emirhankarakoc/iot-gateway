package com.aselsis.iot.gateway.deviceapikey;

import com.aselsis.iot.gateway.deviceapikey.enums.ProtocolType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceApiKeyDTO {

        private String token;
        private LocalDate validUntil;
        @Enumerated(EnumType.STRING)
        private ProtocolType protocol;
        private String room;
    }

