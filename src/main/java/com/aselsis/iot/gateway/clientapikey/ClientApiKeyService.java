package com.aselsis.iot.gateway.clientapikey;

import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
import com.aselsis.iot.gateway.clientapikey.request.PutClientApiKeyRequest;

import java.util.List;

public interface ClientApiKeyService {
    ClientApiKeyDTO postClientApiKey(PostClientApiKeyRequest postClientApiKeyRequest);
    List<ClientApiKeyDTO> getClientApiKeys();

    ClientApiKeyDTO getClientApiKey(String id);

    void deleteClientApiKey(String id);
    ClientApiKeyDTO putClientApiKey(String id, PutClientApiKeyRequest putClientApiKeyRequest);
}
