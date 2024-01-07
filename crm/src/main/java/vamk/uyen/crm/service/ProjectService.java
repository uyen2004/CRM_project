package vamk.uyen.crm.service;

import org.springframework.stereotype.Service;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;

import java.util.List;

@Service
public interface ProjectService {
    void addProject(ProjectRequest projectDto);

    void updateProject(Long id, ProjectRequest projectDto);

    List<ProjectResponse> findAllProjects();

    ProjectResponse findProjectById(Long id);

    void deleteProject(Long id);

}
