package vamk.uyen.crm.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.request.UserRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.*;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ErrorCodeException;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.util.AuthenticationUtil;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private static final Logger logger = LogManager.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Override
    public void addProject(ProjectRequest projectDto) {
        String userEmail = authenticationUtil.getAccount().getEmail();

        UserEntity originator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        Project project = Converter.toModel(projectDto, Project.class);
        project.setOriginator(originator);
        originator.getProjects().add(project);

        projectRepository.save(project);
    }


    @Override
    public void updateProject(Long id, ProjectRequest projectDto, Long originatorId) {
        var project = projectRepository.findById(id).orElseThrow(() -> {
            logger.error("Could not find project id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        var updatedProject = Converter.toModel(projectDto, Project.class);
        var existingOriginator = userRepository.findById(originatorId)
                .orElseThrow(() -> {
                    logger.error("Could not find user with id " + originatorId);
                    throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(originatorId));
                });
        // Update project properties if they are not null in the request
        if (projectDto.getName() != null) {
            project.setName(updatedProject.getName());
        }
        if (projectDto.getStartDate() != null) {
            project.setStartDate(updatedProject.getStartDate());
        }
        if (projectDto.getEndDate() != null) {
            project.setEndDate(updatedProject.getEndDate());
        }
        if(projectDto.getOriginator() != null){
            project.setOriginator(existingOriginator);
            existingOriginator.getProjects().add(project);
        }
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
        List<ProjectResponse> content = Converter.toList(projectList, ProjectResponse.class);

        PaginatedResponse<ProjectResponse> projectResponse = new PaginatedResponse<>();
        projectResponse.setContent(content);
        projectResponse.setPageNo(projectPage.getNumber());
        projectResponse.setPageSize(projectPage.getSize());
        projectResponse.setTotalElements(projectPage.getTotalElements());
        projectResponse.setTotalPages(projectPage.getTotalPages());
        projectResponse.setLast(projectPage.isLast());

        var user =  authenticationUtil.getAccount();
        Long id = user.getId();
        Set<Role> roles = user.getRoles();
        logger.info(id);
        String roleName = roles.stream()
                .map(Role::getName)
                .filter(name -> name.equals("ROLE_MANAGER") || name.equals("ROLE_STAFF") || name.equals("ROLE_ADMIN"))
                .findFirst()
                .orElse("Role not found");

        if (roleName.equals("ROLE_MANAGER")) {
            projectResponse.setContent(getProjects(id));
        } else if (roleName.equals("ROLE_STAFF")) {
            List<Task> tasks = user.getTasks();
            List<ProjectResponse> projects = tasks.stream()
                    .map(Task::getProject)
                    .map(project -> Converter.toModel(project, ProjectResponse.class))
                    .collect(Collectors.toList());
            projectResponse.setContent(projects);
        }

        return projectResponse;

    }

    @Override
    public ProjectResponse findProjectById(Long id) {
        var project = projectRepository.findById(id).orElseThrow(()
                        -> {
                    logger.error("Could not found project id " + id);
                    throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
                }
        );

        return Converter.toModel(project, ProjectResponse.class);
    }

    @Override
    public void deleteProject(Long id) {
        var project = projectRepository.findById(id).orElseThrow(()
                        -> {
                    logger.error("Could not found project id " + id);
                    throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
                }
        );

        for (Task task : project.getTasks().stream().toList()) {
            if (task.getStatus() != TaskStatus.DONE) {
                throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
            }
        }

        projectRepository.delete(project);
    }

    @Override
    public List<ProjectResponse> getProjects(Long originatorId) {
        List<Project> projects = projectRepository.findByOriginatorId(originatorId);
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for (Project project : projects) {
            ProjectResponse projectResponse = Converter.toModel(project, ProjectResponse.class);
            projectResponses.add(projectResponse);
        }

        return projectResponses;
    }

}



