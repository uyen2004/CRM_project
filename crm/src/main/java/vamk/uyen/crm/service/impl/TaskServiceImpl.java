package vamk.uyen.crm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vamk.uyen.crm.converter.Converter;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.repository.TaskRepository;
import vamk.uyen.crm.entity.TaskEntity;
import vamk.uyen.crm.service.TaskService;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Iterable<TaskResponse> getAllTasks(){
       return Converter.toList(taskRepository.findAll(), TaskResponse.class);
    }

    @Override
    public TaskResponse getTask(Long id){
        TaskEntity existingTask = taskRepository.findById(id).orElse(null);
        if(existingTask == null){
            return  null;
        }
        return Converter.toModel(existingTask, TaskResponse.class);
    }
}