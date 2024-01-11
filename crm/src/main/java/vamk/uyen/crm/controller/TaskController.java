package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.service.TaskService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> findAllTasks() {
        var taskList = taskService.findAllTasks();

        return new ResponseEntity<>(taskList, HttpStatus.OK);
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
    public ResponseEntity<String> setImplemeter(@PathVariable Long taskId, @PathVariable Long userId){
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
