package vamk.uyen.crm.specificationsearch;

import org.springframework.data.jpa.domain.Specification;
import vamk.uyen.crm.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecificationBuilder {
    private final List<SearchCriteria> params;

    public final TaskSpecificationBuilder with(String key,
                                               String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }


    public TaskSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final TaskSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }

    public Specification<Task> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Task> result = new TaskSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            SearchCriteria criteriaRequest = params.get(i);
            result = SearchOperation.getDataOption(criteriaRequest.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new TaskSpecification(criteriaRequest))
                    : Specification.where(result).or(new TaskSpecification(criteriaRequest));
        }

        return result;
    }


}