package vamk.uyen.crm.service;

import org.springframework.stereotype.Service;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.ProjectResponse;

@Service
public interface ProjectService {
    void addProject(ProjectRequest projectDto);

    void updateProject(Long id, ProjectRequest projectDto);

    PaginatedResponse<ProjectResponse> findAllProjects(int pageNo, int pageSize, String sortBy, String sortDir);

    ProjectResponse findProjectById(Long id);

    void deleteProject(Long id);

}
