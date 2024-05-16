package com.cydeo.mapper;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // create a method converting entity object to dto object
    public UserDTO convertToUserDTO(User userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }

    // create a method converting dto object to entity object
    public User convertToEntity(UserDTO userDTO){
        return modelMapper.map(userDTO,User.class);
    }
}
