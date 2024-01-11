package vamk.uyen.crm.service;

import org.springframework.data.domain.Pageable;
import vamk.uyen.crm.dto.request.RegisterDto;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedRespone;
import vamk.uyen.crm.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse findUserById(Long id);

    PaginatedRespone<UserResponse> findAllUser(int pageNo, int pageSize, String sortBy, String sortDir);

    void updateUser(Long id, UserRequest userDto);

    void deleteUserById(Long id);
}
