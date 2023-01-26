package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.ContactDao;
import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Dto.UpdateUserDto;
import com.workshop.Lisa.Entity.Contact;
import com.workshop.Lisa.Entity.ContactInformation;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.Utils.DateConverter;
import com.workshop.Lisa.Utils.GenderEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao dao;
    private final ContactInformationService contactInformationService;

    public User findUserByUsername(String userName){
        return this.dao.findByUserName(userName).orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));
    }

    public User updateUser(UpdateUserDto updateUserDto, String username){
        User existingUser = this.dao.findByUserName(username).orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));

        ContactInformation ci = contactInformationService.getContactInformation(existingUser.getUserId());
        // Maybe add a try catch to check if CI is null.
        ci.setUserEmail(updateUserDto.getUserEmail());
        ci.setDiscord(updateUserDto.getDiscord());
        ci.setFacebook(updateUserDto.getFacebook());
        ci.setSnapchat(updateUserDto.getSnapchat());
        ci.setInstagram(updateUserDto.getInstagram());
        ci.setUserPhoneNumber(updateUserDto.getUserPhoneNumber());
        existingUser.setContactInformation(ci);
        existingUser.setUserFirstname(updateUserDto.getUserFirstname());
        existingUser.setUserLastName(updateUserDto.getUserLastName());
        existingUser.setUserPassword(new BCryptPasswordEncoder().encode(updateUserDto.getUserPassword()));
        existingUser.setBirthDate(Date.valueOf(updateUserDto.getBirthDate()));
        existingUser.setGender(GenderEnum.valueOf(updateUserDto.getGender()));

        return this.dao.save(existingUser);
    }

    public User findById(Long id){
        return this.dao.findById(id).orElseThrow(() -> new EntityNotFoundException("Could not find user with that id!"));
    }
}
