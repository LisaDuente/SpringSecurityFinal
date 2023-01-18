package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao dao;


}
