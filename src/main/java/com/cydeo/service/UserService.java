package com.cydeo.service;


import com.cydeo.dto.UserDTO;


import java.util.List;

public interface UserService{

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO dto);
    UserDTO update(UserDTO dto);
    void delete(String username);
    void deepDelete(String userName);





}
