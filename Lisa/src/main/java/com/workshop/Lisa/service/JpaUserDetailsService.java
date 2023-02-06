package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Entity.SecurityUser;
import com.workshop.Lisa.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserDao userRepo;

    public JpaUserDetailsService(UserDao userDao){
        this.userRepo = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        return userRepo.findByUserNameOrContactInformation_UserEmail(usernameOrEmail, usernameOrEmail)
                .map(SecurityUser::new)
                .orElseThrow(() ->
                        new EntityNotFoundException("Username not found" + usernameOrEmail));

    }

}