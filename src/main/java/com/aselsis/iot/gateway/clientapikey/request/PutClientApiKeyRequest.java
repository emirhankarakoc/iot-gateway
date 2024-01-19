package com.aselsis.iot.gateway.clientapikey.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PutClientApiKeyRequest {
    private String token;
    private String appName;
    private String room;

}
