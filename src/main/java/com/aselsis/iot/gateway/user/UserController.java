package com.aselsis.iot.gateway.user;

import com.aselsis.iot.gateway.security.annotations.OnlyAdmin;
import com.aselsis.iot.gateway.user.request.PostUserRequest;
import com.aselsis.iot.gateway.user.request.PutUserRequest;
import com.aselsis.iot.gateway.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/users")
@Tag(name = "User Controller")

public class UserController {

    private final UserService userService;

    @OnlyAdmin
    @Operation(
            description = "returns the specified user entity",
            summary = "Get an user entity by id",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
                        })
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @OnlyAdmin
    @Operation(
            description = "returns a list of user entities",
            summary = "GET List of User entities",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Unauthorized ", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping
    public List<UsersDTO> getUsers() {
        return userService.getUsers();
    }

    @OnlyAdmin
    @Operation(
            description = "creates a user entity",
            summary = "CREATE an user entity.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO postUser(@RequestBody PostUserRequest postUserRequest) {
        return this.userService.postUser(postUserRequest);
    }

    @OnlyAdmin
    @Operation(
            description = "updates the specified user entity",
            summary = "Updates an existing user entity by ID",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO putUser(@RequestBody PutUserRequest putUserRequest, @PathVariable String id) {
      return this.userService.putUser(putUserRequest,id);
    }

    @OnlyAdmin
    @Operation(
            description = "deletes the specified user entity",
            summary = "Deletes the user entity",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content(mediaType = "application/json")),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Entity Not Found", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        this.userService.deleteUser(id);
    }
}


