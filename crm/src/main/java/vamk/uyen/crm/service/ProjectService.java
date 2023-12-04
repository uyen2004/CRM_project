package vamk.uyen.crm.service;

import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;

public interface ProjectService {
    ProjectResponse addProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long id, ProjectEntity projectEntity);
    Iterable<ProjectResponse> getAllProjects();
}
