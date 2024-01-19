package com.aselsis.iot.gateway.clientapikey;


import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
import com.aselsis.iot.gateway.clientapikey.request.PutClientApiKeyRequest;
import com.aselsis.iot.gateway.exceptions.EntityNotFoundException;
import com.aselsis.iot.gateway.utils.mapper.ModelMapperManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data@AllArgsConstructor@NoArgsConstructor
public class ClientApiKeyManager implements ClientApiKeyService{
    @Autowired
    private ClientApiKeyRepository clientApiKeyRepository;
    @Autowired
    private ModelMapperManager modelMapperManager;

    @Override
    public ClientApiKeyDTO postClientApiKey(PostClientApiKeyRequest request) {
        ClientApiKey  clientApiKey = clientApiKeyRepository.save(ClientApiKey.create(request));
        return this.modelMapperManager.forResponse().map(clientApiKey, ClientApiKeyDTO.class);
    }

    @Override
    public List<ClientApiKeyDTO> getClientApiKeys() {
        {
            List<ClientApiKey> keys = this.clientApiKeyRepository.findAll();
            List<ClientApiKeyDTO> allKeysResponse = keys.stream()
                    .map(key -> this.modelMapperManager.forResponse()
                            .map(key,ClientApiKeyDTO.class)).toList();
            return allKeysResponse;
        }
    }


    @Override
    public ClientApiKeyDTO getClientApiKey(String id) {
        ClientApiKey clientApiKey = this.clientApiKeyRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Client Api Key Not Found"));
        ClientApiKeyDTO dto = this.modelMapperManager.forResponse().map(clientApiKey, ClientApiKeyDTO.class);
        return dto;
    }

    @Override
    public void deleteClientApiKey( String id) {
        clientApiKeyRepository.deleteById(id);
    }

    @Override
    public ClientApiKeyDTO putClientApiKey(String id, PutClientApiKeyRequest putClientApiKeyRequest) {
        Optional<ClientApiKey> clientApiKey = clientApiKeyRepository.findById(id);
        if (clientApiKey.isPresent()) {
            ClientApiKey updatedClientApikey = clientApiKey.get();
            clientApiKeyRepository.save(ClientApiKey.update(updatedClientApikey, putClientApiKeyRequest));
            return modelMapperManager.forResponse().map(updatedClientApikey, ClientApiKeyDTO.class);
        }else throw new EntityNotFoundException("Client Api Key Not Found");

    }
}
