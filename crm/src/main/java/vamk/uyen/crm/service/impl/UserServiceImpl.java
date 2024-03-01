package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.UserResponse;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ErrorCodeException;
import vamk.uyen.crm.repository.RoleRepository;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

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
    public void addUser(UserRequest userRequest, Long roleId) {
        var existingRole = roleRepository.findById(roleId).orElseThrow(() -> {
            logger.error("Could not find role with id " + roleId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(roleId));
        });

        var addedUser = Converter.toModel(userRequest, UserEntity.class);
        Set<Role> roles = new HashSet<>();
        roles.add(existingRole);
        addedUser.setRoles(roles);

        userRepository.save(addedUser);
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

//        if (userEntity.getEmail().equalsIgnoreCase(userDto.getEmail())) {
            var updatedUser = Converter.toModel(userDto, UserEntity.class);
            if(updatedUser.getUsername() != null) {
                userEntity.setUsername(updatedUser.getUsername());
            }
            if(updatedUser.getPhoneNum() != null) {
                userEntity.setPhoneNum(updatedUser.getPhoneNum());
            }

            if(updatedUser.getRoles() != null) {
                userEntity.setRoles(updatedUser.getRoles());
            }

            logger.info(updatedUser.getPhoneNum());
//        }


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
    public UserEntity getUser(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

}
