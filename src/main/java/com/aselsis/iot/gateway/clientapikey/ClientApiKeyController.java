package com.aselsis.iot.gateway.clientapikey;

import com.aselsis.iot.gateway.clientapikey.request.PostClientApiKeyRequest;
import com.aselsis.iot.gateway.clientapikey.request.PutClientApiKeyRequest;
import com.aselsis.iot.gateway.security.annotations.OnlyAdmin;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.aselsis.iot.gateway.exceptions.EntityNotFoundException;
import com.aselsis.iot.gateway.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-api-keys")
@CrossOrigin
@AllArgsConstructor
@Tag(name = "Client Api Key Controller")

public class ClientApiKeyController {
    private ClientApiKeyService clientApiKeyService;
    private  ClientApiKeyRepository clientApiKeyRepository;


    @Operation(
            description ="returns the specified client api key entity",
                    summary = "Returns ClientApiKey by ID ",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientApiKeyDTO.class))),
                    @ApiResponse(description = "Unauthorized  ", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(description = "Forbidden ", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientApiKeyDTO.class))),
            })
    @OnlyAdmin
    @GetMapping("/{id}")
    public ClientApiKeyDTO get1ClientApiKey(@PathVariable String id){
        return clientApiKeyService.getClientApiKey(id);
    }


    @Operation(
            description = "returns a list of client api key entities",
            summary = "Returns all ClientApiKey.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation =  ClientApiKeyDTO.class))),
                    @ApiResponse(description = "Unauthorized  ", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden ", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })

    @OnlyAdmin
    @GetMapping
    public List<ClientApiKeyDTO> getAllClientApiKeys(){
        return clientApiKeyService.getClientApiKeys();
    }


    @Operation(
            description = "creates a client api key entity",
            summary = "Create ClientApiKey",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientApiKeyDTO.class))),
                    @ApiResponse(description = "Forbidden  ", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthenticated ", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            })

    @OnlyAdmin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientApiKeyDTO postClientApiKey(@RequestBody PostClientApiKeyRequest postClientApiKeyRequest){
       return clientApiKeyService.postClientApiKey(postClientApiKeyRequest);
    }

    @Operation(
            description = "updates the specified client api key entity",
            summary = "Update a ClientApi",
            responses = {
                    @ApiResponse(description = "Succes", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientApiKeyDTO.class))),
                    @ApiResponse(description = "Unauthorized  ", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden ", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientApiKeyDTO.class))),
            })
    @OnlyAdmin
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientApiKeyDTO putUser(@PathVariable String id, @RequestBody PutClientApiKeyRequest putClientApiKeyRequest){
        return clientApiKeyService.putClientApiKey(id,putClientApiKeyRequest);
    }


    @Operation(
            description = "deletes the specified client api key entity",
            summary = "Delete a ClientApi",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized  ", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden ", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClientApiKeyDTO.class))),
            })
    @OnlyAdmin
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClientApiKey(@PathVariable String id){
        ClientApiKey clientApiKey = clientApiKeyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(""));
        this.clientApiKeyService.deleteClientApiKey(id);}




}
