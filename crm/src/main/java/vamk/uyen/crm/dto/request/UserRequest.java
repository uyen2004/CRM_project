package vamk.uyen.crm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamk.uyen.crm.entity.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    @JsonProperty("username")
    private String username;

    private String email;

    private String phoneNum;

    private String password;

    private Set<Role> roles;
}
