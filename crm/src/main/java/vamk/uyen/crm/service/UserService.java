package vamk.uyen.crm.service;

import vamk.uyen.crm.dto.request.RegisterDto;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse findUserById(Long id);

    List<UserResponse> findAllUser();

    void updateUser(Long id, UserRequest userDto);

    void deleteUserById(Long id);
}
