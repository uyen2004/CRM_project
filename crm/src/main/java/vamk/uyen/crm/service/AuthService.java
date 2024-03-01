package vamk.uyen.crm.service;

import vamk.uyen.crm.dto.request.LoginDto;
import vamk.uyen.crm.dto.request.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto, Long roleId);
}
