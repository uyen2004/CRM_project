package vamk.uyen.crm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "get all roles")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        var roleList = roleService.getAllRoles();

        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(summary = "create role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<String> addRole(@Valid @RequestBody RoleRequest roleDto) {
        roleService.addRole(roleDto);

        return new ResponseEntity<>("successful", HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(summary = "set role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<String> setRole(@PathVariable Long userId, @RequestBody Long roleId){
        roleService.setRole(userId, roleId);

        return new ResponseEntity<>("successful", HttpStatus.OK);
    }


    @Operation(summary = "delete role")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
