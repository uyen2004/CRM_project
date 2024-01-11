package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.RoleRequest;
import vamk.uyen.crm.dto.response.RoleResponse;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.exception.ResourceNotFoundException;
import vamk.uyen.crm.repository.RoleRepository;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.RoleService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Converter converter;
    private final UserRepository userRepository;

    public void addRole(RoleRequest roleDto) {
        roleRepository.save(converter.toModel(roleDto, Role.class));
    }

    public List<RoleResponse> getAllRoles() {
        var roleList = roleRepository.findAll();

        return converter.toList(roleList, RoleResponse.class);
    }

    public void deleteRole(Long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roleRepository.delete(role);
    }

    @Override
    public void setRole(Long userId, Role role) {
        var settedrole = roleRepository.findByName(role.getName()).orElseThrow(() ->
                new ResourceNotFoundException("Role not found"));

        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Role> roleSet = user.getRoles();
        roleSet.add(settedrole);

        user.setRoles(roleSet);

        userRepository.save(user);
    }
}
