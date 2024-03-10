package vamk.uyen.crm.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vamk.uyen.crm.crmapi.UserTest;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.UserResponse;

public interface UserService {
    UserResponse findUserById(Long id);

    PaginatedResponse<UserResponse> findAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

    void updateUser(Long id, UserRequest userDto);

    void deleteUserById(Long id);

    Flux<UserTest> getAllUsers();
}
