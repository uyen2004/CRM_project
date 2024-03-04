package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.LoginDto;
import vamk.uyen.crm.dto.request.RegisterDto;
import vamk.uyen.crm.dto.response.JwtAuthResponse;
import vamk.uyen.crm.entity.Role;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.AuthService;
import vamk.uyen.crm.util.AuthenticationUtil;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        UserEntity user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String roleName = user.getRoles().stream()
                .map(Role::getName)
                .findFirst()
                .orElse("Default Role");

        jwtAuthResponse.setRole(roleName);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register/{roleId}")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto, @PathVariable Long roleId){
        String response = authService.register(registerDto, roleId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
