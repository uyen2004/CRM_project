package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.util.DateValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse addProject(ProjectRequest projectRequest) {
        if(DateValidationUtil.isDateValidate(projectRequest.getStartDate(), projectRequest.getEndDate())) {
            // convert to entity
            ProjectEntity projectEntity = Converter.toModel(projectRequest, ProjectEntity.class);
            // save database
            projectEntity = projectRepository.save(projectEntity);
            // return to use
            return Converter.toModel(projectEntity, ProjectResponse.class);
        }else{
            throw new IllegalArgumentException("The end date must be later than start date");
        }
    }
    @Override
    public ProjectResponse updateProject(Long id, ProjectEntity projectEntity) {
        //find if the update entity is exist by id
        if(DateValidationUtil.isDateValidate(projectEntity.getStartDate(), projectEntity.getEndDate())) {

            ProjectEntity updateProjectEntity = projectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No project found with id: " + id));

                //if the update entity is exist then assign the update entity fields with the field of the request entity
                updateProjectEntity.setName(projectEntity.getName());
                updateProjectEntity.setStartDate(projectEntity.getStartDate());
                updateProjectEntity.setEndDate(projectEntity.getEndDate());

                //then save the update entity to database
                projectEntity = projectRepository.save(updateProjectEntity);

                //convert and return the update entity
                return Converter.toModel(projectEntity, ProjectResponse.class);
        }else{
            throw new IllegalArgumentException("The end date must be later than start date");
        }
    }

    @Override
    public List<ProjectResponse> getAllProjects(){
        return Converter.toList(projectRepository.findAll(), ProjectResponse.class);
    }

    @Override
    public ProjectResponse getProject(Long id){
        ProjectEntity existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No project found with id: " + id));

        return Converter.toModel(existingProject, ProjectResponse.class);
    }

    @Override
    public void deleteProject(Long id) {
        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No project found with id: " + id));
        projectRepository.deleteById(id);
    }


}
