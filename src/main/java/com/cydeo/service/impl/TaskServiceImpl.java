package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

   private final TaskRepository taskRepository;
   private final TaskMapper taskMapper;

   @Autowired
   public TaskServiceImpl(TaskRepository taskRepository,TaskMapper taskMapper){
       this.taskRepository=taskRepository;
       this.taskMapper=taskMapper;
   }
    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(eachTaskEntity->taskMapper.convertType(eachTaskEntity, TaskDTO.class)).collect(Collectors.toList());

    }

    @Override
    public TaskDTO findById(Long id) {
        return taskMapper.convertType(taskRepository.findById(id), TaskDTO.class);
    }

    @Override
    public void save(TaskDTO taskDTO) {
       if(taskDTO.getTaskStatus()==null){
           taskDTO.setTaskStatus(Status.OPEN);
       }
       taskDTO.setAssignedDate(LocalDate.now());
      taskRepository.save(taskMapper.convertType(taskDTO,Task.class));
    }

    @Override
    public void update(TaskDTO taskDTO) {
       Optional<Task> task=taskRepository.findById(taskDTO.getId());
       Task convertedTask=taskMapper.convertType(taskDTO,Task.class);
       if(task.isPresent()){
           convertedTask.setId(task.get().getId());
           convertedTask.setTaskStatus(task.get().getTaskStatus());
           convertedTask.setAssignedDate(task.get().getAssignedDate());
       }
       taskRepository.save(convertedTask);
    }

    @Override
    public void delete(Long id) {
      // softDelete
        Task task=taskRepository.findById(id).orElseThrow(()->new RuntimeException("No Such Task Found"));
        task.setIsDeleted(true);
        taskRepository.save(task);
    }

    @Override
    public int countUnfinishedTask(String projectCode) {
//        return (int)taskRepository.getTasksByProject(projectCode).stream().filter(eachTask->eachTask.getTaskStatus().getValue()!="Completed").count();
        return taskRepository.nonCompletedTasks(projectCode);
    }

    @Override
    public int countCompletedTask(String projectCode) {
//        return (int)taskRepository.getTasksByProject(projectCode).stream().filter(eachTask->eachTask.getTaskStatus().getValue()=="Completed").count();
        return taskRepository.completedTasks(projectCode);
    }


}
