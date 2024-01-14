package vamk.uyen.crm.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ProjectRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String startDate;
    @NotEmpty
    private String endDate;

    private List<TaskRequest> tasks;
}
