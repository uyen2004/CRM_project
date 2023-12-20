package vamk.uyen.crm.service;

import org.springframework.stereotype.Service;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;

import java.util.List;

@Service
public interface ProjectService {
    ProjectResponse addProject(ProjectRequest projectRequest);
    ProjectResponse updateProject(Long id, ProjectEntity projectEntity);
    List<ProjectResponse> getAllProjects();

    ProjectResponse getProject(Long id);

    void deleteProject(Long id);

}
