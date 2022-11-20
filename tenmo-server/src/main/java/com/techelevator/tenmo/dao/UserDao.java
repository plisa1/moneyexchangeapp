package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int id);

    //List<User> findAllUsersWithUnAndUid();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}
