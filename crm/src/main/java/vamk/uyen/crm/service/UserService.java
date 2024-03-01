package vamk.uyen.crm.service;

import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.UserResponse;
import vamk.uyen.crm.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    UserResponse findUserById(Long id);

    void addUser(UserRequest userRequest, Long roleId);

    PaginatedResponse<UserResponse> findAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

    void updateUser(Long id, UserRequest userDto);

    void deleteUserById(Long id);

    UserEntity getUser(String email);
}
