package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.entity.Project;
import vamk.uyen.crm.entity.Task;
import vamk.uyen.crm.entity.TaskStatus;
import vamk.uyen.crm.exception.ApiException;
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
    public PaginatedResponse<ProjectResponse> findAllProjects(int pageNo, int pageSize, String sortBy, String sortDir) {

        // check sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Project> projectPage = projectRepository.findAll(pageable);

        // get content from page object
        List<Project> projectList = projectPage.getContent();
        List<ProjectResponse> content = converter.toList(projectList, ProjectResponse.class);

        PaginatedResponse<ProjectResponse> projectResponse = new PaginatedResponse<>();
        projectResponse.setContent(content);
        projectResponse.setPageNo(projectPage.getNumber());
        projectResponse.setPageSize(projectPage.getSize());
        projectResponse.setTotalElements(projectPage.getTotalElements());
        projectResponse.setTotalPages(projectPage.getTotalPages());
        projectResponse.setLast(projectPage.isLast());

        return projectResponse;
    }

    @Override
    public ProjectResponse findProjectById(Long id) {
        var project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        return converter.toModel(project, ProjectResponse.class);
    }

    @Override
    public void deleteProject(Long id) {
        var project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        for(Task task : project.getTasks().stream().toList()){
            if (task.getStatus() != TaskStatus.DONE){
                throw new ApiException(HttpStatus.BAD_REQUEST, "Project cannot be deleted");
            }
        }

        projectRepository.delete(project);
    }

}



