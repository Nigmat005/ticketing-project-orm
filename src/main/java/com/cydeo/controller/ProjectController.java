package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    ProjectService projectService;
    UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createProject(Model model) {

        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.listAllByRole("Manager"));
        return "/project/create";

    }

    @PostMapping("/create")
    public String insertProject(@Valid @ModelAttribute("project") ProjectDTO project, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("projects", projectService.listAllProjects());
//            model.addAttribute("managers", userService.listAllManagers());
            model.addAttribute("managers", userService.listAllByRole("Manager"));
            return "/project/create";

        }

        projectService.save(project);
        return "redirect:/project/create";

    }
    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model) {

        model.addAttribute("project", projectService.findByProjectCode(projectCode));
        model.addAttribute("projects", projectService.listAllProjects());
//        model.addAttribute("managers", userService.listAllManagers());
        model.addAttribute("managers", userService.listAllByRole("Manager"));

        return "/project/update";

    }

    @PostMapping("/update")
    public String updateProject(@Valid @ModelAttribute("project") ProjectDTO project, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("projects", projectService.listAllProjects());
            model.addAttribute("managers",userService.listAllByRole("Manager"));

            return "/project/update";

        }

        projectService.update(project);
        return "redirect:/project/create";

    }
    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.delete(projectCode);
        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectCode}")
    public String completeProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return "redirect:/project/create";
    }


//
//    @GetMapping("/manager/project-status")
//    public String getProjectByManager(Model model) {
//
//        UserDTO manager = userService.findById("john@cydeo.com");
//
//        List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager);
//
//        model.addAttribute("projects", projects);
//
//        return "/manager/project-status";
//    }
//
//    @GetMapping("/manager/complete/{projectCode}")
//    public String managerCompleteProject(@PathVariable("projectCode") String projectCode) {
//        projectService.complete(projectService.findById(projectCode));
//        return "redirect:/project/manager/project-status";
//    }

}
