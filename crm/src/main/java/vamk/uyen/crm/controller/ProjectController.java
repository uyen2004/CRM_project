package vamk.uyen.crm.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.service.ProjectService;

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

}
