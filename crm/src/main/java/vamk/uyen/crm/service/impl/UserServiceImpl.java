package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.crmapi.CrmApi;
import vamk.uyen.crm.crmapi.UserTest;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.UserResponse;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ErrorCodeException;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final CrmApi crmApi;

    @Override
    public UserResponse findUserById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Could not found user id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        return Converter.toModel(userEntity, UserResponse.class);
    }

    @Override
    public PaginatedResponse<UserResponse> findAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {

        // check sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<UserEntity> userPage = userRepository.findAll(pageable);

        // get content from page object
        List<UserEntity> userList = userPage.getContent();
        List<UserResponse> content = Converter.toList(userList, UserResponse.class);

        PaginatedResponse<UserResponse> userResponse = new PaginatedResponse<>();
        userResponse.setContent(content);
        userResponse.setPageNo(userPage.getNumber());
        userResponse.setPageSize(userPage.getSize());
        userResponse.setTotalElements(userPage.getTotalElements());
        userResponse.setTotalPages(userPage.getTotalPages());
        userResponse.setLast(userPage.isLast());

        return userResponse;
    }


    @Override
    public void updateUser(Long id, UserRequest userDto) {
        var userEntity = userRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Could not found user id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        if (userEntity.getEmail().equalsIgnoreCase(userDto.getEmail())) {
            var updatedUser = Converter.toModel(userDto, UserEntity.class);

            userEntity.setUsername(updatedUser.getUsername());
            userEntity.setPhoneNum(updatedUser.getPhoneNum());
            userEntity.setRoles(updatedUser.getRoles());
        }

        userRepository.save(userEntity);

    }

    @Override
    public void deleteUserById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Could not found user id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        userRepository.delete(userEntity);
    }

    @Override
    public Flux<UserTest> getAllUsers() {
        return crmApi.getAllUsers();
    }

}
