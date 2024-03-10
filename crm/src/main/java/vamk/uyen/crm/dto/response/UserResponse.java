package vamk.uyen.crm.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamk.uyen.crm.dto.request.RoleRequest;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.entity.Task;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String id;
    @NotEmpty
    private String username;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNum;

    private Set<RoleResponse> roles;

    private List<TaskRequest> tasks;
}
