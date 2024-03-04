package vamk.uyen.crm.service;

import vamk.uyen.crm.dto.request.RoleRequest;
import vamk.uyen.crm.dto.response.RoleResponse;
import vamk.uyen.crm.entity.Role;

import java.util.List;

public interface RoleService {
    void addRole(RoleRequest roleDto);

    List<RoleResponse> getAllRoles();

    void deleteRole(Long id);

    void setRole(Long userId, Long roleId);
}
