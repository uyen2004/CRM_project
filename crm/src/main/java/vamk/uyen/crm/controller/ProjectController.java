package vamk.uyen.crm.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.util.DateValidationUtil;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody ProjectRequest projectRequest) {
        if (DateValidationUtil.isDateValidate(projectRequest.getStartDate(), projectRequest.getEndDate())) {
            ProjectResponse projectResponse = projectService.addProject(projectRequest);
            return ResponseEntity.ok(projectResponse);
        } else {
            return ResponseEntity.badRequest().body("The end date must be later than start date");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectEntity projectEntity){
        if(DateValidationUtil.isDateValidate(projectEntity.getStartDate(), projectEntity.getEndDate())){
            ProjectResponse projectResponse = projectService.updateProject(id, projectEntity);
            if (projectResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found the project with id: " + id);
            } else {
                return ResponseEntity.ok(projectResponse);
            }
        }else{
            return ResponseEntity.badRequest().body("The end date must be later than start date");
        }
    }

    @GetMapping
    public Iterable<ProjectResponse> getAllProjects(){
        return  projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        ProjectResponse projectResponse = projectService.getProject(id);

        if (projectResponse != null) {
            return ResponseEntity.ok(projectResponse);
        } else {
            // Return a 404 response if the project is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project with id " + id + " not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        ProjectResponse existingProject = projectService.getProject(id);
        if (existingProject == null) {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Project with id " + id + " deleted successfully");
        } else {
            // Return a 404 response if the project is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project with id " + id + " not found");
        }
    }

}
