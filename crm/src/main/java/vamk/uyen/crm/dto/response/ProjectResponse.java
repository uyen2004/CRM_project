package vamk.uyen.crm.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamk.uyen.crm.dto.request.TaskRequest;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse {
    @NotEmpty
    private String name;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;

    private List<TaskRequest> tasks = new ArrayList<>();
}
