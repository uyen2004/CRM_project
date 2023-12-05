package vamk.uyen.crm.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String name;
    private String startDate;
    private String endDate;
}
