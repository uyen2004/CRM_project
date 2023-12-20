package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.request.TaskRequest;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.repository.TaskRepository;
import vamk.uyen.crm.entity.TaskEntity;
import vamk.uyen.crm.service.TaskService;
import vamk.uyen.crm.util.DateValidationUtil;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskResponse> getAllTasks(){
       return Converter.toList(taskRepository.findAll(), TaskResponse.class);
    }

    @Override
    public TaskResponse getTask(Long id){
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No task found with id: " + id));
        return Converter.toModel(existingTask, TaskResponse.class);
    }

    @Override
    public TaskResponse addTask(TaskRequest taskRequest){
        if(DateValidationUtil.isDateValidate(taskRequest.getStartDate(), taskRequest.getEndDate())) {
            TaskEntity newTaskEntity = Converter.toModel(taskRequest, TaskEntity.class);
            newTaskEntity = taskRepository.save(newTaskEntity);
            return Converter.toModel(newTaskEntity, TaskResponse.class);
        }else{
            throw new IllegalArgumentException("the end date must be later than start date");
        }
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        if (DateValidationUtil.isDateValidate(taskRequest.getStartDate(), taskRequest.getEndDate())) {
            TaskEntity updateTask = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No task found with id: " + id));

            updateTask.setName(taskRequest.getName());
            updateTask.setStartDate(taskRequest.getStartDate());
            updateTask.setEndDate(taskRequest.getEndDate());

            updateTask = taskRepository.save(updateTask);
            return Converter.toModel(updateTask, TaskResponse.class);
        } else {
            throw new IllegalArgumentException("The end date must be later than start date");
        }
    }

    @Override
    public void deleteTask(Long id){
            TaskEntity taskEntity = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No task found with id: " + id));
        taskRepository.deleteById(id);
    }
}
