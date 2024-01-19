package com.aselsis.iot.gateway.deviceapikey;

import com.aselsis.iot.gateway.deviceapikey.request.PostDeviceApiKeyRequest;
import com.aselsis.iot.gateway.deviceapikey.request.PutDeviceApiKeyRequest;
import com.aselsis.iot.gateway.security.annotations.OnlyAdmin;
import com.aselsis.iot.gateway.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/device-api-keys")
@CrossOrigin
@AllArgsConstructor
@Transactional
@Tag(name = "Device Api Key Controller")
public class DeviceApiKeyController {
    private final DeviceApiKeyService deviceApiKeyService;

    @OnlyAdmin
    @Operation(
            description = "returns the specified device api key entity",
            summary = "Get a Device Api Key",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceApiKeyDTO.class))),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "UnAuthenticated", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @GetMapping("/{id}")
    public DeviceApiKeyDTO getDeviceApiKey(@PathVariable String id){
        return deviceApiKeyService.getDeviceApiKey(id);
    }
    @OnlyAdmin
    @Operation(
            description = "returns a list of device api key entities",
            summary = "Gets Device Api Key Entity List",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceApiKeyDTO.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @GetMapping
    public List<DeviceApiKeyDTO> getAllDeviceApiKeys(){
        return deviceApiKeyService.getAllDeviceApiKeys();
    }


    @OnlyAdmin
    @Operation(
            description = "creates a device api key entity",
            summary = "Create Device Api Key Entity",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceApiKeyDTO.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceApiKeyDTO postDeviceApiKey(@RequestBody PostDeviceApiKeyRequest postDeviceApiKeyRequest){
       return this.deviceApiKeyService.postDeviceApiKey(postDeviceApiKeyRequest);
    }
    @OnlyAdmin
    @Operation(
            description = "updates the specified device api key entity",
            summary = "Update Device Api Key Entity",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceApiKeyDTO.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceApiKeyDTO putDeviceApiKey(@RequestBody @Valid PutDeviceApiKeyRequest putDeviceApiKeyRequest,@PathVariable String id) {
        return this.deviceApiKeyService.putDeviceApiKey(putDeviceApiKeyRequest,id);
    }

    @OnlyAdmin
    @Operation(
            description = "deletes the specified device api key entity",
            summary = "DELETE Device Api Key",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeviceApiKey(@PathVariable String id){ this.deviceApiKeyService.deleteDeviceApiKey(id);}
}
