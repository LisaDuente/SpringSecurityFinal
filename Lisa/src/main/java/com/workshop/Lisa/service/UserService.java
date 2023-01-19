package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao dao;

    public User findUserByUsername(String userName){
        return this.dao.findByUserName(userName).orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));
    }
}
