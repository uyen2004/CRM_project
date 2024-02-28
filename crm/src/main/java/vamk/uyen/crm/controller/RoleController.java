package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.RoleRequest;
import vamk.uyen.crm.dto.response.RoleResponse;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class RoleController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        var roleList = roleService.getAllRoles();

        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<String> addRole(@Valid @RequestBody RoleRequest roleDto) {
        roleService.addRole(roleDto);

        return new ResponseEntity<>("successful", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<String> setRole(@PathVariable Long userId, @RequestBody Role role){
        roleService.setRole(userId, role);

        return new ResponseEntity<>("successful", HttpStatus.OK);
    }


    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
