package vamk.uyen.crm.service;

import org.springframework.stereotype.Service;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;

import java.util.List;

@Service
public interface TaskService {
    List<TaskResponse> findAllTasks();

    TaskResponse findTaskById(Long id);

    void addTask(Long projectId, TaskRequest taskDto);

    void updateTask(Long id, TaskRequest taskDto);

    void deleteTask(Long id);
}
