package vamk.uyen.crm.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.entity.ProjectEntity;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.util.DateVadilationUtil;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    @PostMapping
    public ResponseEntity<?> addProject(@RequestBody ProjectRequest projectRequest) {
        ProjectResponse projectResponse = projectService.addProject(projectRequest);
        return ResponseEntity.ok(projectResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody ProjectEntity projectEntity){
        try {
            DateVadilationUtil.validateDate(projectEntity.getStartDate(), projectEntity.getEndDate());
            ProjectResponse projectResponse = projectService.updateProject(id, projectEntity);
            if (projectResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found the project with id: " + id);
            } else {
                return ResponseEntity.ok(projectResponse);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
