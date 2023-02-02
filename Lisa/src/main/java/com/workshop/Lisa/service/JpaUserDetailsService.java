package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Entity.SecurityUser;
import com.workshop.Lisa.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserDao userRepo;

    public JpaUserDetailsService(UserDao userDao){
        this.userRepo = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //check if username is a valid username, then convert it into a SecurityUser
        return userRepo.findByUserName(username)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found " + username));

    }

    public UserDetails loadUserByEmail(String userMail) throws UsernameNotFoundException {
        //check if username is a valid username, then convert it into a SecurityUser
        return userRepo.findByContactInformationUserEmail(userMail)
                .map(SecurityUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found " + userMail));

    }
}