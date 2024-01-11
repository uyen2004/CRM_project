package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
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
import vamk.uyen.crm.exception.ResourceNotFoundException;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.UserService;

import java.util.List;

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
    public PaginatedResponse<UserResponse> findAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {

        // check sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<UserEntity> userPage = userRepository.findAll(pageable);

        // get content from page object
        List<UserEntity> userList = userPage.getContent();
        List<UserResponse> content = converter.toList(userList, UserResponse.class);

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
