package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.UserResponse;
import vamk.uyen.crm.service.UserService;
import vamk.uyen.crm.util.AppConstants;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userDto) {
        userService.updateUser(id, userDto);

        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
