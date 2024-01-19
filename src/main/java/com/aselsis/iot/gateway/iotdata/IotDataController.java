package com.aselsis.iot.gateway.iotdata;

import com.aselsis.iot.gateway.deviceapikey.DeviceApiKeyService;
import com.aselsis.iot.gateway.security.annotations.OnlyAdmin;
import com.aselsis.iot.gateway.soketconfig.Message;
import com.aselsis.iot.gateway.exceptions.ErrorResponse;
import com.corundumstudio.socketio.SocketIOServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/iot-data")
@AllArgsConstructor
@Tag(name = "IotData Controller")
public class IotDataController {
    private final SocketIOServer socketIOServer;
    private final DeviceApiKeyService deviceApiKeyService;
    private final IotDataRepository iotDataRepository;
private final IotDataService service;

    @Operation(
            summary = "save latest json data from iot device"
            )
    @PostMapping("/actions/savejson")
    public void postData(@RequestBody Message message, @RequestParam String token){service.postData(message,token);}
    @Operation(
            summary = "save latest text data from iot device",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IotDataDto.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/actions/savetext",consumes = "text/plain")
    public void postStringData(@RequestBody String message, @RequestParam String token){service.postStringData(message,token);}
    @Operation(
            description = "returns a page of iotdata ",
            summary = "GET Pageable of iotData ",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IotDataDto.class))),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping
    public Page<IotData> getAll(Pageable pageable){return service.getAll(pageable);}
    @OnlyAdmin
    @Operation(
            description = "returns the specified iotdata ",
            summary = "Get an iotdata entity by room",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IotDataDto.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/getByRoom")
    public List<IotData> getByRoom(@RequestParam String room){
        return service.getByRoom(room);
    }
    @OnlyAdmin
    @Operation(
            description = "returns the specified iotdata ",
            summary = "Get an iotdata entity by room and by serial",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IotDataDto.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "NotFound", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("/getByRoomSerial")
    public Optional<IotData> getByRoomSerial(@RequestParam String room,@RequestParam String serial ){
        return service.getByRoomAndSerial(room,serial);
    }
}
