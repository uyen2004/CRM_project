package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.service.TaskService;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public Iterable<TaskResponse> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id){
        TaskResponse taskResponse = taskService.getTask(id);
        if(taskResponse == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found the task with id "+id);
        }else{
            return ResponseEntity.ok(taskResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody TaskRequest taskRequest){
        TaskResponse taskResponse = taskService.addTask(taskRequest);
        return ResponseEntity.ok(taskResponse);
    }
}
