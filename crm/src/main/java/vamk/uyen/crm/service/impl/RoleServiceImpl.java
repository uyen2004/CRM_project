package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.RoleRequest;
import vamk.uyen.crm.dto.response.RoleResponse;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ErrorCodeException;
import vamk.uyen.crm.repository.RoleRepository;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.RoleService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(RoleServiceImpl.class);

    public void addRole(RoleRequest roleDto) {

        roleRepository.save(Converter.toModel(roleDto, Role.class));
    }

    public List<RoleResponse> getAllRoles() {
        var roleList = roleRepository.findAll();

        return Converter.toList(roleList, RoleResponse.class);
    }

    public void deleteRole(Long id) {
        var role = roleRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Could not found role id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });
        roleRepository.delete(role);
    }

    @Override
    public void setRole(Long userId, Role role) {
        var settedrole = roleRepository.findByName(role.getName()).orElseThrow(()
                -> {
            logger.error("Could not found role name " + role.getName());
            throw new ApiException(ErrorCodeException.NOT_FOUND, role.getName());
        });

        var user = userRepository.findById(userId).orElseThrow(()
                -> {
            logger.error("Could not found user id " + userId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(userId));
        });

        Set<Role> roleSet = user.getRoles();
        roleSet.add(settedrole);

        user.setRoles(roleSet);

        userRepository.save(user);
    }
}
