package com.aselsis.iot.gateway.deviceapikey.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder

public class PutDeviceApiKeyRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntil;
    private String room;
}
