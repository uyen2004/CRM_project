package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.RegisterDto;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.UserResponse;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ResourceNotFoundException;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Converter converter;

    @Override
    public UserResponse findUserById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return converter.toModel(userEntity, UserResponse.class);
    }

    @Override
    public List<UserResponse> findAllUser() {
        var userList = userRepository.findAll();

        return converter.toList(userList, UserResponse.class);
    }



    @Override
    public void updateUser(Long id, UserRequest userDto) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userEntity.getEmail().equalsIgnoreCase(userDto.getEmail())) {
            var updatedUser = converter.toModel(userDto, UserEntity.class);

            userEntity.setUsername(updatedUser.getUsername());
            userEntity.setPhoneNum(updatedUser.getPhoneNum());
            userEntity.setRoles(updatedUser.getRoles());
        }

        userRepository.save(userEntity);

    }

    @Override
    public void deleteUserById(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(userEntity);
    }
}
