package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.entity.Project;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper){
        this.projectRepository=projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return  projectRepository.findAll().stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {

        projectDTO.setProjectStatus(Status.OPEN);

        projectRepository.save(projectMapper.convertToEntity(projectDTO));
    }

    @Override
    public void delete(String projectCode) {
       Project project= projectRepository.findByProjectCode(projectCode);
       project.setIsDeleted(true);
       projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO projectDTO) {
        // since id of dto object is null we need to get id from entity
        Project project =projectRepository.findByProjectCode(projectDTO.getProjectName());

        // set id of entity from db to DTO project
        Project projectWithId=projectMapper.convertToEntity(projectDTO);
        projectWithId.setId(project.getId());
        projectRepository.save(projectWithId);
    }

    @Override
    public ProjectDTO findByProjectCode(String projectCode) {
        return  projectMapper.convertToDto(projectRepository.findByProjectCode(projectCode));
    }

    @Override
    public void complete(String projectCode) {
        Project project=projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }
}
