package com.aselsis.iot.gateway.user;

import com.aselsis.iot.gateway.user.request.PostUserRequest;
import com.aselsis.iot.gateway.user.request.PutUserRequest;
import com.aselsis.iot.gateway.exceptions.EntityNotFoundException;
import com.aselsis.iot.gateway.utils.mapper.ModelMapperManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class UserManager implements UserService {

    private final UserRepository userRepository;

    private final ModelMapperManager modelMapperManager;

    @Override
    public List<UsersDTO> getUsers() {
        List<User> users = this.userRepository.findAll();
        if (users.size()==0) throw new EntityNotFoundException("Users list is null, you should add new");

        List<UsersDTO> allUsersResponse = users.stream()
                .map(user -> this.modelMapperManager.forResponse()
                        .map(user, UsersDTO.class)).collect(Collectors.toList());
        return allUsersResponse;
    }
    @Override
    public UserDTO getUser(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User Not Found"));
        UserDTO doneceknesne = this.modelMapperManager.forResponse().map(user, UserDTO.class);
        return doneceknesne;
    }
    @Override
    public UserDTO postUser(PostUserRequest req) {
        boolean requestedUserNameisExist = this.userRepository.existsByUserName(req.getUserName());
        User user = null;
        if (requestedUserNameisExist) {
            throw new UserNameExistsException("This username already exists");
        }

        user = this.userRepository.save(User.create(req));

        return this.modelMapperManager.forResponse().map(user, UserDTO.class);
    }
    @Override
    public UserDTO putUser(PutUserRequest putUserRequest, String id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            userRepository.save(User.update(updatedUser, putUserRequest));
            return this.modelMapperManager.forResponse().map(updatedUser, UserDTO.class);

        } else {
            throw new EntityNotFoundException("Id ye kayıtlı kullanıcı yoktur");
        }

    }
    @Override
    public void deleteUser(String id) {

        boolean requestedUserUserIsExist = this.userRepository.existsById(id);
        if (requestedUserUserIsExist) {
            this.userRepository.deleteById(id);
        } else {
            throw new CantDeleteUserException("The user cannot be deleted or has already been deleted.");
        }
    }
    @Override
    public void deleteUserByUserName(String userName) {
        boolean requestedUserUserIsExist = this.userRepository.existsByUserName(userName);
        if (requestedUserUserIsExist) {
            this.userRepository.deleteByUserName(userName);
        } else {
            throw new CantDeleteUserException("The user cannot be deleted or has already been deleted.");
        }
    }
}
