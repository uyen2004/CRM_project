package vamk.uyen.crm.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vamk.uyen.crm.dto.request.TaskRequest;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectResponse {
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private Date startDate;
    @NotEmpty
    private Date endDate;

    private List<TaskResponse> tasks;
}
