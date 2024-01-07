package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.entity.Task;
import vamk.uyen.crm.exception.ResourceNotFoundException;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.repository.TaskRepository;
import vamk.uyen.crm.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Converter converter;

    @Override
    public List<TaskResponse> findAllTasks() {
        var taskList = taskRepository.findAll();

        return Converter.toList(taskList, TaskResponse.class);
    }

    @Override
    public TaskResponse findTaskById(Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return Converter.toModel(task, TaskResponse.class);
    }

    @Override
    public void addTask(Long projectId, TaskRequest taskDto) {
        var existingProject = projectRepository.findById(projectId).orElseThrow(() ->
                new ResourceNotFoundException("Project not found"));

        var taskList = existingProject.getTasks();
        var addedTask = converter.toModel(taskDto, Task.class);
        taskList.add(addedTask);
        existingProject.setTasks(taskList);

        projectRepository.save(existingProject);
        taskRepository.save(addedTask);
    }

    @Override
    public void updateTask(Long id, TaskRequest taskDto) {
        var existingTask = taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Task not found"));
        var addedtask = converter.toModel(taskDto, Task.class);
        existingTask.setName(addedtask.getName());
        existingTask.setStartDate(addedtask.getStartDate());
        existingTask.setEndDate(addedtask.getEndDate());
        existingTask.setStatus(addedtask.getStatus());

        taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
