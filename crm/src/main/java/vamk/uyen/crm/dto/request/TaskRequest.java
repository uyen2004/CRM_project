package vamk.uyen.crm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamk.uyen.crm.entity.TaskStatus;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TaskRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;
}

