package com.aselsis.iot.gateway.clientapikey.request;

import lombok.*;

@Data@AllArgsConstructor@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostClientApiKeyRequest {

    private String appName;
    private String room;
}
