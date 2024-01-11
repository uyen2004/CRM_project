package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.service.TaskService;
import vamk.uyen.crm.util.AppConstants;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
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

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> findTaskById(@PathVariable Long id) {
        var task = taskService.findTaskById(id);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<String> addTask(@PathVariable Long projectId, @Valid @RequestBody TaskRequest taskDto) {
        taskService.addTask(projectId, taskDto);

        return new ResponseEntity<>("successfull", HttpStatus.CREATED);

    }

    @PutMapping("/tasks/{taskId}/users/{userId}")
    public ResponseEntity<String> setImplemeter(@PathVariable Long taskId, @PathVariable Long userId) {
        taskService.setImplementer(taskId, userId);

        return new ResponseEntity<>("implementer added", HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest taskDto) {
        taskService.updateTask(id, taskDto);

        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}
