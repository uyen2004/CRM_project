package vamk.uyen.crm.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.service.ProjectService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/projects")
    public ResponseEntity<String> addProject(@Valid @RequestBody ProjectRequest projectDto) {
        projectService.addProject(projectDto);

        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<String> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectDto) {
        projectService.updateProject(id, projectDto);

        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> findAllProjects() {
        var projectList = projectService.findAllProjects();

        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable Long id) {
        var project = projectService.findProjectById(id);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    @DeleteMapping("/projects/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}


