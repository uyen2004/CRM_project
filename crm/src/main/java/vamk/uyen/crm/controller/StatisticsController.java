package vamk.uyen.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vamk.uyen.crm.dto.response.PaginatedResponse;
import vamk.uyen.crm.dto.response.ProjectResponse;
import vamk.uyen.crm.dto.response.TaskResponse;
import vamk.uyen.crm.entity.Task;
import vamk.uyen.crm.repository.TaskRepository;
import vamk.uyen.crm.service.ProjectService;
import vamk.uyen.crm.service.TaskService;
import vamk.uyen.crm.util.AppConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController{
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;
    @GetMapping("/taskStatistics")
    public Map<String, Long> taskStatistics(@RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                               @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAUT_SORT_BY, required = false) String sortBy,
                                               @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){

        PaginatedResponse<TaskResponse> tasks = taskService.findAllTasks(pageNo,pageSize,sortBy, sortDir);
        Long totalTasks = tasks.getTotalElements();
        List<TaskResponse> taskResponseList = tasks.getContent();
        int completedTasks = 0;
        int inProgressTasks = 0;
        int notStartedTasks = 0;

        for (TaskResponse task : taskResponseList) {
            switch (task.getStatus()) {
                case DONE:
                    completedTasks++;
                    break;
                case IN_PROGRESS:
                    inProgressTasks++;
                    break;
                case NOT_STARTED:
                    notStartedTasks++;
                    break;
                default:
                    break;
            }
        }

        Map<String, Long> percentages = new HashMap<>();
        percentages.put("completed", calculatePercentage(completedTasks, totalTasks));
        percentages.put("inProgress", calculatePercentage(inProgressTasks, totalTasks));
        percentages.put("notStarted", calculatePercentage(notStartedTasks, totalTasks));

        return percentages;
    }
    private Long calculatePercentage(int count, Long total) {
        return total == 0 ? 0 : ( count / total) * 100;
    }

    private int calculatePercentage(int count, int total) {
        return total == 0 ? 0 : ( count / total) * 100;
    }

    @GetMapping("/jobStatistic/{projectId}")
    public Map<String, Integer> jobStatistic(@PathVariable Long projectId){
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        int totalTasks = tasks.size();
        int completedTasks = 0;
        int inProgressTasks = 0;
        int notStartedTasks = 0;

        for (Task task : tasks) {
            switch (task.getStatus()) {
                case DONE:
                    completedTasks++;
                    break;
                case IN_PROGRESS:
                    inProgressTasks++;
                    break;
                case NOT_STARTED:
                    notStartedTasks++;
                    break;
                default:
                    break;
            }
        }

        Map<String, Integer> percentages = new HashMap<>();
        percentages.put("completed", calculatePercentage(completedTasks, totalTasks));
        percentages.put("inProgress", calculatePercentage(inProgressTasks, totalTasks));
        percentages.put("notStarted", calculatePercentage(notStartedTasks, totalTasks));

        return percentages;
    }
}

