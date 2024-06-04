package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final ModelMapper modelMapper;


    @Autowired
    public  TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

   public <T> T convertType(Object source, Class<T> resultClass){
     return modelMapper.map(source, resultClass);
   }

}
