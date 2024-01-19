package com.aselsis.iot.gateway.clientapikey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientApiKeyDTO {
    private String token;
    private String appName;
    private String room;
}
