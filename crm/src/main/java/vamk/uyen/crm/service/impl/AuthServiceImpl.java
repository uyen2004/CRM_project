package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.Security.JwtTokenProvider;
import vamk.uyen.crm.dto.request.LoginDto;
import vamk.uyen.crm.dto.request.RegisterDto;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ErrorCodeException;
import vamk.uyen.crm.repository.RoleRepository;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.AuthService;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.Logger;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto, Long roleId) {
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw new IllegalArgumentException("Username is already exists!");
        }

        var existingRole = roleRepository.findById(roleId).orElseThrow(() -> {
//            logger.error("Could not find role with id " + roleId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(roleId));
        });

        UserEntity newUser = new UserEntity();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setPhoneNum(registerDto.getPhoneNum());

        Set<Role> roles = new HashSet<>();
        roles.add(existingRole);
        newUser.setRoles(roles);
        userRepository.save(newUser);

        return "User registered successfully!";
    }
}
