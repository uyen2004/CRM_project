package vamk.uyen.crm.dto.request;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
    private String name;
    private String startDate;
    private String endDate;
}
