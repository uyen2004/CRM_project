package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.Project;
import vamk.uyen.crm.exception.ResourceNotFoundException;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.service.ProjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final Converter converter;

    @Override
    public void addProject(ProjectRequest projectDto) {
        projectRepository.save(converter.toModel(projectDto, Project.class));
    }

    @Override
    public void updateProject(Long id, ProjectRequest projectDto) {
        var project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        var updatedProject = converter.toModel(projectDto, Project.class);

        project.setName(updatedProject.getName());
        project.setStartDate(updatedProject.getStartDate());
        project.setEndDate(updatedProject.getEndDate());

        projectRepository.save(project);
    }

    @Override
    public List<ProjectResponse> findAllProjects() {
        var projectList = projectRepository.findAll();

        return converter.toList(projectList, ProjectResponse.class);
    }

    @Override
    public ProjectResponse findProjectById(Long id) {
        var project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return converter.toModel(project, ProjectResponse.class);
    }

    @Override
    public void deleteProject(Long id) {
        var project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectRepository.delete(project);
    }
}



