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
    private String name;

    private String startDate;

    private String endDate;

    private List<TaskRequest> tasks;
}
