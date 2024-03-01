package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.entity.Task;
import vamk.uyen.crm.entity.TaskStatus;
import vamk.uyen.crm.entity.UserEntity;
import vamk.uyen.crm.exception.ApiException;
import vamk.uyen.crm.exception.ErrorCodeException;
import vamk.uyen.crm.repository.ProjectRepository;
import vamk.uyen.crm.repository.TaskRepository;
import vamk.uyen.crm.repository.UserRepository;
import vamk.uyen.crm.service.TaskService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    @Override
    public PaginatedResponse<TaskResponse> findAllTasks(int pageNo, int pageSize, String sortBy, String sortDir) {

        // check sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Task> taskPage = taskRepository.findAll(pageable);

        // get content from page object
        List<Task> taskList = taskPage.getContent();
        List<TaskResponse> content = Converter.toList(taskList, TaskResponse.class);

        PaginatedResponse<TaskResponse> taskResponse = new PaginatedResponse<>();
        taskResponse.setContent(content);
        taskResponse.setPageNo(taskPage.getNumber());
        taskResponse.setPageSize(taskPage.getSize());
        taskResponse.setTotalElements(taskPage.getTotalElements());
        taskResponse.setTotalPages(taskPage.getTotalPages());
        taskResponse.setLast(taskPage.isLast());

        return taskResponse;
    }

    @Override
    public TaskResponse findTaskById(Long id) {
        var task = taskRepository.findById(id).orElseThrow(()
                -> {
            logger.error("Could not found task id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        return Converter.toModel(task, TaskResponse.class);
    }

    @Override
    public void addTask(Long projectId, TaskRequest taskDto, Long userId) {
        var existingProject = projectRepository.findById(projectId).orElseThrow(() -> {
            logger.error("Could not find project with id " + projectId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(projectId));
        });

        var existingUser = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("Could not find user with id " + userId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(userId));
        });

        var addedTask = Converter.toModel(taskDto, Task.class);
        addedTask.setStatus(TaskStatus.NOT_STARTED);
        addedTask.setProject(existingProject);

        List<Task> userTaskList = existingUser.getTasks();
        userTaskList.add(addedTask);
        existingUser.setTasks(userTaskList);

        List<Task> projectTaskList = existingProject.getTasks();
        projectTaskList.add(addedTask);
        existingProject.setTasks(projectTaskList);

        taskRepository.save(addedTask);

    }

    @Override
    public void setImplementer(Long taskId, Long userId) {
        var existingTask = taskRepository.findById(taskId).orElseThrow(()
                -> {
            logger.error("Could not found task id " + taskId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(taskId));
        });

        var existingUser = userRepository.findById(userId).orElseThrow(()
                ->{
            logger.error("Could not found user id " + userId);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(userId));
        });

        List<Task> taskList = existingUser.getTasks();
        taskList.add(existingTask);
        existingUser.setTasks(taskList);

        userRepository.save(existingUser);
    }

    @Override
    public void updateTask(Long id, TaskRequest taskDto) {
        var existingTask = taskRepository.findById(id).orElseThrow(() -> {
            logger.error("Could not find task id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        var updatedTask = Converter.toModel(taskDto, Task.class);

        if (taskDto.getName() != null) {
            existingTask.setName(updatedTask.getName());
        }
        if (taskDto.getStartDate() != null) {
            existingTask.setStartDate(updatedTask.getStartDate());
        }
        if (taskDto.getEndDate() != null) {
            existingTask.setEndDate(updatedTask.getEndDate());
        }
        if (taskDto.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        taskRepository.save(existingTask);
    }


    @Override
    public void deleteTask(Long id) {
        // Find the task by id
        var task = taskRepository.findById(id).orElseThrow(() ->
        {
            logger.error("Could not found task id " + id);
            throw new ApiException(ErrorCodeException.NOT_FOUND, String.valueOf(id));
        });

        // Remove the association from the implementers' side
        for (UserEntity implementer : task.getImplementers()) {
            implementer.getTasks().remove(task);
            userRepository.save(implementer);
        }

        // Clear the implementers and save the task to ensure changes are flushed
        task.getImplementers().clear();
        taskRepository.saveAndFlush(task);

        taskRepository.delete(task);
    }

}
