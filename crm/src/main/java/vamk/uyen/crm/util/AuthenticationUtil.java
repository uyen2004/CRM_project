package vamk.uyen.crm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Component;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.service.UserService;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
public class AuthenticationUtil {
    @Autowired
    private final UserService userService;

    public AuthenticationUtil(UserService userService) {
        this.userService = userService;
    }
    public UserEntity getAccount() {
        var authentication = getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? null
                : userService.getUser(authentication.getName());
    }
}