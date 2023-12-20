package vamk.uyen.crm.service;

import org.springframework.stereotype.Service;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;

import java.util.List;

@Service
public interface TaskService {
    List<TaskResponse> getAllTasks();
    TaskResponse getTask(Long id);

    TaskResponse addTask(TaskRequest taskRequest);

    TaskResponse updateTask(Long id, TaskRequest taskRequest);

    void deleteTask(Long id);
}
