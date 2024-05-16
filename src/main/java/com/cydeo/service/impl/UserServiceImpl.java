package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
//    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

//    @Override
//    public List<UserDTO> listAllUsers() {
//        List<UserDTO> result=new ArrayList<>();
//        List<User> users= userRepository.findAll();
//        for (User entityUser:users){
//            UserDTO userDTO= modelMapper.map(entityUser,UserDTO.class);
//            result.add(userDTO);
//        }
//        return result;
//}

    @Override
    public List<UserDTO> listAllUsers() {
       return userRepository.findAll(Sort.by("firstName")).stream().map(userMapper::convertToUserDTO).collect(Collectors.toList());
    }
    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToUserDTO(userRepository.findByUserNameLike(username));
    }

    @Override
    public void save(UserDTO dto) {
      userRepository.save(userMapper.convertToEntity(dto));
    }

    @Override
    public UserDTO update(UserDTO dto) {
        // find current user
        User user=userRepository.findByUserNameLike(dto.getUserName());

        User userWithNoID=userMapper.convertToEntity(dto);
        userWithNoID.setId(user.getId());
        userRepository.save(userWithNoID);
        return findByUserName(dto.getUserName());
    }

//    @Override
//    public UserDTO update(UserDTO dto) {
//      // first save dto object to entity
//        User user=userMapper.convertToEntity(dto);
//        Long id;
//        if(user.getId()==null){
//             id=userRepository.findByUserNameLike(user.getUserName()).getId();
//             user.setId(id);
//        }
//        userRepository.save(user);
//        return findByUserName(dto.getUserName());
//
//    }

    @Override
    public void deleteByUserName(String username) {

    }
}
