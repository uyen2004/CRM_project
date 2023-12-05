package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.util.DateVadilationUtil;

import static vamk.uyen.crm.util.DateVadilationUtil.validateDate;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse addProject(ProjectRequest projectRequest) {
        if(projectRequest.getStartDate() != null && projectRequest.getEndDate() != null){
            LocalDate startDate = LocalDate.parse(projectRequest.getStartDate());
            LocalDate endDate = LocalDate.parse(projectRequest.getEndDate());

            if(endDate.isBefore(startDate)){
                throw new IllegalArgumentException("End date must be later than start date");
            }
        }
        // convert to entity
        ProjectEntity projectEntity = Converter.toModel(projectRequest, ProjectEntity.class);
        // save database
        projectEntity = projectRepository.save(projectEntity);
        // return to user
        return Converter.toModel(projectEntity, ProjectResponse.class);
    }
    @Override
    public ProjectResponse updateProject(Long id, ProjectEntity projectEntity) {
        //find if the update entity is exist by id
        ProjectEntity updateProjectEntity = projectRepository.findById(id).orElse(null);

        //if no update entity was found then return null
        if(updateProjectEntity == null){
            return null;
        }else{
            //if the update entity is exist then assign the update entity fields with the field of the request entity
            updateProjectEntity.setName(projectEntity.getName());
            updateProjectEntity.setStartDate(projectEntity.getStartDate());
            updateProjectEntity.setEndDate(projectEntity.getEndDate());

            //then save the update entity to database
            projectEntity = projectRepository.save(updateProjectEntity);

            //convert and return the update entity
            return Converter.toModel(projectEntity, ProjectResponse.class);
        }

    }

    @Override
    public Iterable<ProjectResponse> getAllProjects(){
        return Converter.toList(projectRepository.findAll(), ProjectResponse.class);
    }
}
