package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     // Select only not deleted user to show "Select * from USERS where is_deleted=false"
//       return userRepository.findAll(Sort.by("firstName")).stream().filter(eachUserEntity-> !eachUserEntity.getIsDeleted())
//        .map(userMapper::convertToUserDTO).collect(Collectors.toList());

        // After adding @Where on top of User Entity class, it will auto concat with Query
        // e.g. in  UserRepository interface method 'User findByUserNameLike(String userName)' will
        // help us return User Entity by doing "SELECT * FROM USERS". However, after adding the @Where(clause="is_delete=false")
        // it will run like   "SELECT * FROM USERS WHERE is_delete=false"
        return userRepository.findAll(Sort.by("firstName")).stream()
                .map(userMapper::convertToUserDTO).collect(Collectors.toList());
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
    public void delete(String username) {
        // Will not delete User from User table, instead make is_Delete=true, and save, then listAllUsers will fetch Users whose is_delete=false, making deleted user not visible from view.
        User user= userRepository.findByUserNameLike(username);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public void deepDelete(String userName) {
        // will delete user from db
        userRepository.deleteUserByUserName(userName);
    }
}
