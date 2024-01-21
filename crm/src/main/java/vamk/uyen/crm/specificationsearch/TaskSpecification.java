package vamk.uyen.crm.specificationsearch;


import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import vamk.uyen.crm.entity.Project;
import vamk.uyen.crm.entity.Task;

import java.util.Objects;

public class TaskSpecification implements Specification<Task> {

    private final SearchCriteria searchCriteria;

    public TaskSpecification(final SearchCriteria
                                     searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
                if (searchCriteria.getFilterKey().equalsIgnoreCase("name")) {
                    return criteriaBuilder.like(root.<String>get(searchCriteria.getFilterKey()), "%" + strToSearch + "%");
                }

                if (searchCriteria.getFilterKey().equalsIgnoreCase("project")) {
                    Join<Task, Project> taskProject = root.join("projects");
                    return criteriaBuilder.like(taskProject.get("name"), "%" + strToSearch + "%");
                }
            case EQUAL:
                if (searchCriteria.getFilterKey().equalsIgnoreCase("status")) {
                    return criteriaBuilder.equal(root.<String>get(searchCriteria.getFilterKey()), strToSearch);
                }
        }

        return null;
    }
}