package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.entity.Task;
import vamk.uyen.crm.service.TaskService;
import vamk.uyen.crm.util.AppConstants;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<PaginatedResponse<TaskResponse>> findAllTasks(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAUT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        PaginatedResponse<TaskResponse> taskResponse = taskService.findAllTasks(pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> findTaskById(@PathVariable Long id) {
        var task = taskService.findTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PostMapping("/projects/{projectId}/users/{userId}/tasks")
    public ResponseEntity<String> addTask(@PathVariable Long projectId, @PathVariable Long userId, @Valid @RequestBody TaskRequest taskDto) {
        taskService.addTask(projectId, userId, taskDto);
        return new ResponseEntity<>("Successfully added task for user " + userId + " in project " + projectId, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//    @PutMapping("/tasks/{taskId}/users/{userId}")
//    public ResponseEntity<String> setImplemeter(@PathVariable Long taskId, @PathVariable Long userId) {
//        taskService.setImplementer(taskId, userId);
//
//        return new ResponseEntity<>("implementer added", HttpStatus.OK);
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PutMapping("/tasks/{id}/implementer/{implementerId}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest taskDto, @PathVariable Long implementerId) {
        taskService.updateTask(id, taskDto, implementerId);

        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
