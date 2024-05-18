package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // derived query
//    User findByUserNameLike(String userName);



    // JPQL
//    @Query(value = "select user from User user where user.userName=?1",nativeQuery = false)
//    User findByUserName(String username);

    @Query(value = "select user from User user where user.userName=:username",nativeQuery = false)
    User findByUserName(@Param("username") String username);


    // deleteByUserName
    @Transactional
    @Modifying
    void deleteUserByUserName(String userName);

    // fetch all managers
    @Query("Select user from User user where user.role.description ='Manager'")
    List<User> fetchManagers();
}
