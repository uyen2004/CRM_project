package vamk.uyen.crm.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.entity.TaskStatus;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponse {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;
    @NotEmpty
    private List<UserRequest> implementers;
    @NotEmpty
    private TaskStatus status;
}

