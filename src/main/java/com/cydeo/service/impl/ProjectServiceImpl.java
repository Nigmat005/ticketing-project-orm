package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
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
    private final UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserRepository userRepository){
        this.projectRepository=projectRepository;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return  projectRepository.findAll().stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        if(projectDTO.getProjectStatus()==null){
            projectDTO.setProjectStatus(Status.OPEN);
        }
        projectRepository.save(projectMapper.convertToEntity(projectDTO));
    }
}
