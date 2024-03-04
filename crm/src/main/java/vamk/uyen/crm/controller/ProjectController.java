package vamk.uyen.crm.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.ProjectRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.util.AppConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "create project")
    @PostMapping("/projects")
    public ResponseEntity<String> addProject(@Valid @RequestBody ProjectRequest projectDto) {
        projectService.addProject(projectDto);

        return new ResponseEntity<>("successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "update project")
    @PutMapping("/projects/{id}")
    public ResponseEntity<String> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectDto) {
        projectService.updateProject(id, projectDto);

        return new ResponseEntity<>("updated", HttpStatus.OK);
    }

    @Operation(summary = "get all projects")
    @GetMapping("/projects")
    public ResponseEntity<PaginatedResponse<ProjectResponse>> findAllProjects(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAUT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        PaginatedResponse<ProjectResponse> projectResponse = projectService.findAllProjects(pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @Operation(summary = "get project by id")
    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectResponse> findProjectById(@PathVariable Long id) {
        var project = projectService.findProjectById(id);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(summary = "delete project")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}


