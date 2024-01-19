package com.aselsis.iot.gateway.user;

import com.aselsis.iot.gateway.user.request.PostUserRequest;
import com.aselsis.iot.gateway.user.request.PutUserRequest;

import java.util.List;

public interface UserService {
    List<UsersDTO> getUsers();
    UserDTO getUser(String id);
    UserDTO postUser(PostUserRequest postUserRequest);
    void deleteUser(String id);
    void deleteUserByUserName(String userName);
    UserDTO putUser(PutUserRequest putUserRequest, String id);

}
