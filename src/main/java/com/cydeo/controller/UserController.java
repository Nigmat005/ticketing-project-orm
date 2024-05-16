package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import jdk.jfr.Threshold;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
//@Transactional
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String createUser(Model model) {

        model.addAttribute("user", new UserDTO());
        model.addAttribute("roles", roleService.listAllRoles());
        model.addAttribute("users", userService.listAllUsers());

        return "/user/create";
    }


    @PostMapping("/create")
    public String insertUser(
//            @Valid
                                 @ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("roles", roleService.listAllRoles());
            model.addAttribute("users", userService.listAllUsers());

            return "/user/create";

        }

        userService.save(user);
        return "redirect:/user/create";

    }

    @GetMapping("/update/{userName}")
    public String editUser(@PathVariable("userName") String userName, Model model) {

        model.addAttribute("user", userService.findByUserName(userName));
        model.addAttribute("roles", roleService.listAllRoles());
        model.addAttribute("users", userService.listAllUsers());

        return "/user/update";

    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("roles", roleService.listAllRoles());
            model.addAttribute("users", userService.listAllUsers());

            return "/user/update";

        }

        userService.update(user);
        return "redirect:/user/create";

    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        userService.deleteByUserName(username);
        return "redirect:/user/create";
    }

}
