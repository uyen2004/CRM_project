package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.UserResponse;
import vamk.uyen.crm.service.UserService;
import vamk.uyen.crm.util.AppConstants;
import vamk.uyen.crm.util.AuthenticationUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class UserController {

    private final UserService userService;
    private final AuthenticationUtil authenticationUtil;


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping()
    public ResponseEntity<PaginatedResponse<UserResponse>> findAllUser(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAUT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PaginatedResponse<UserResponse> userResponse = userService.findAllUser(pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        var userDto = userService.findUserById(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/profile")
    public UserResponse getProfile() {
        String userEmail = authenticationUtil.getAccount().getEmail();
        return userService.getProfile(userEmail);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/role/{roleId}")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest, @PathVariable Long roleId){
        userService.addUser(userRequest, roleId);
        return new ResponseEntity<>("Successfully added new user ", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userDto) {
        userService.updateUser(id, userDto);

        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
