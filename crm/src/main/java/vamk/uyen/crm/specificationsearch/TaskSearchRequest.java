package vamk.uyen.crm.specificationsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskSearchRequest {
    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;
}