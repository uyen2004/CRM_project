package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.service.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse addProject(ProjectRequest projectRequest) {
        // convert to entity
        ProjectEntity projectEntity = Converter.toModel(projectRequest, ProjectEntity.class);
        // save database
        projectEntity = projectRepository.save(projectEntity);
        // return to user
        return Converter.toModel(projectEntity, ProjectResponse.class);
    }
}
