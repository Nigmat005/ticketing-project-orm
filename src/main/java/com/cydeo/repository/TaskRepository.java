package com.cydeo.repository;

import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE  t.project.projectCode=?1")
    List<Task> getTasksByProject(String projectCode);

    // JPQL
    @Query(value = "SELECT COUNT(t) FROM Task t WHERE t.project.projectCode =?1 AND  t.taskStatus <>  'COMPLETE'")
    int nonCompletedTasks(String projectCode);

    @Query(nativeQuery = true,value = "Select count(*) from tasks t join projects p on t.project_id=p.id"+
            " where p.project_code=:projectCode and t.task_status='COMPLETE'")
    int completedTasks(@Param("projectCode") String projectCode);
}
