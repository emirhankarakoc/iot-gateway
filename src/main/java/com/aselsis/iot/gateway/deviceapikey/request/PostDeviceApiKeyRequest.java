package com.aselsis.iot.gateway.deviceapikey.request;

import com.aselsis.iot.gateway.deviceapikey.enums.ProtocolType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
public class PostDeviceApiKeyRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
    private ProtocolType protocol;
    private String room;


}
