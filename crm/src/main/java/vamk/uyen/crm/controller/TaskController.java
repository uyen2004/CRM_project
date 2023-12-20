package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.service.TaskService;
import vamk.uyen.crm.util.DateValidationUtil;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id){
        try{
        TaskResponse taskResponse = taskService.getTask(id);
            return ResponseEntity.ok(taskResponse);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody TaskRequest taskRequest){
        try{
            TaskResponse taskResponse = taskService.addTask(taskRequest);
            return ResponseEntity.ok(taskResponse);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest){
            try{
                TaskResponse taskResponse = taskService.updateTask(id, taskRequest);
                return ResponseEntity.ok(taskResponse);
            }catch(IllegalArgumentException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        try{
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task with id "+id+ "is deleted successfully");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
