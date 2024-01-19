package com.aselsis.iot.gateway.account;

import com.aselsis.iot.gateway.user.User;
import com.aselsis.iot.gateway.user.UserDTO;
import com.aselsis.iot.gateway.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
@AllArgsConstructor
@Tag(name = "Account Controller")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    @Operation(
            description = "login to system with username and password.",
            summary = "Account Login",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                               })
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(
            description = "returns the current logged in user",
            summary = "Who I Am ?",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                            })
    @GetMapping("/me")
    public User getMe(){
        return accountService.getMe();
    }
}
