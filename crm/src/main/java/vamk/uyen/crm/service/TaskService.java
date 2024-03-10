package vamk.uyen.crm.service;

import org.springframework.stereotype.Service;
import vamk.uyen.crm.dto.request.SearchRequest;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.specificationsearch.TaskSearchRequest;

import java.util.List;

@Service
public interface TaskService {
    PaginatedResponse<TaskResponse> findAllTasks(int pageNo, int pageSize, String sortBy, String sortDir);

    TaskResponse findTaskById(Long id);

    void addTask(Long projectId, TaskRequest taskDto);

    void setImplementer(Long taskId, Long userId);

    void updateTask(Long id, TaskRequest taskDto);

    void deleteTask(Long id);
    PaginatedResponse<TaskResponse> searchTasks(int pageNo, int pageSize, String sortBy, String sortDir,
                                                TaskSearchRequest searchRequest);

}
