package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Dto.AuthenticationRequest;
import com.workshop.Lisa.Dto.UserRegisterDto;
import com.workshop.Lisa.Entity.ContactInformation;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.Utils.DateConverter;
import com.workshop.Lisa.Utils.GenderEnum;
import com.workshop.Lisa.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final JpaUserDetailsService jpaUDS;
    private final ContactInformationService contactInformationService;
    private final JwtUtils jwtU;
    private final UserDao userDao;

    public String generateToken(AuthenticationRequest req){
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUserName(), req.getUserPassword())
        );
        final UserDetails userDetails = jpaUDS.loadUserByUsername(req.getUserName());
        if(userDetails != null){
            return jwtU.generateToken(userDetails);
        }
        return "400";
    }

    public String registerAndLogin(UserRegisterDto userRegisterDto) {
        String password = new BCryptPasswordEncoder().encode(userRegisterDto.getUserPassword());
        Date age = new DateConverter().getDate(userRegisterDto.getBirthDate());
        GenderEnum gender = GenderEnum.valueOf(userRegisterDto.getGender());
        ContactInformation contactInfo = this.contactInformationService.createContactInfo( new ContactInformation(
                null,
                userRegisterDto.getUserEmail(),
                "",
                "",
                "",
                "",
                ""));

        User user = new User(
                contactInfo.getUserID(),
                userRegisterDto.getUserName(),
                "",
                userRegisterDto.getUserFirstname(),
                userRegisterDto.getUserLastName(),
                password,
                "USER",
                gender,
                age,
                contactInfo
                );

        //create new user
        System.out.println("user: "+user);
        try{

            userDao.save(user);
        }catch(Exception error){
            return error.getMessage();
           // return "Användarnamnet eller emejl finns redan!";
        }
        return this.generateToken(new AuthenticationRequest(userRegisterDto.getUserName(), userRegisterDto.getUserPassword()));
    }

}
