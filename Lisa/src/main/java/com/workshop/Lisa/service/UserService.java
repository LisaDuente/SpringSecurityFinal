package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Dto.UpdatePreferenceDto;
import com.workshop.Lisa.Dto.UpdateUserDto;
import com.workshop.Lisa.Dto.UpdateUserInformationDto;
import com.workshop.Lisa.Dto.UserDto;
import com.workshop.Lisa.Entity.*;
import com.workshop.Lisa.Utils.GenderEnum;
import com.workshop.Lisa.Utils.Match;
import com.workshop.Lisa.Utils.Matcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao dao;
    private final ContactInformationService contactInformationService;
    private final PreferenceService preferenceService;
    private final HobbyService hobbyService;
    private final RegionService regionService;
    private final Matcher matcher;

    public User findUserByUsername(String userName){
        return this.dao.findByUsername(userName).orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));
    }

    public User updateUser(UpdateUserDto updateUserDto, String username){
        User existingUser = this.dao.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));
        long userId = existingUser.getUserId();
        ContactInformation ci = contactInformationService.getContactInformation(userId);
        // Maybe add a try catch to check if CI is null.
        ci.setEmail(updateUserDto.getUserEmail());
        ci.setDiscord(updateUserDto.getDiscord());
        ci.setFacebook(updateUserDto.getFacebook());
        ci.setSnapchat(updateUserDto.getSnapchat());
        ci.setInstagram(updateUserDto.getInstagram());
        ci.setPhoneNumber(updateUserDto.getUserPhoneNumber());

        Preference pref = preferenceService.getPrefById(userId);
        pref.setGender(Collections.singleton(updateUserDto.getGender()));
        pref.setMaxAge(updateUserDto.getMaxAge());
        pref.setMinAge(updateUserDto.getMinAge());

        pref.setHobbies(stringArrayToSet(updateUserDto.getHobbies(), "HOBBY"));
        pref.setRegions(stringArrayToSet(updateUserDto.getRegions(), "REGION"));

        existingUser.setPreferences(pref);
        existingUser.setContactInformation(ci);
        existingUser.setFirstName(updateUserDto.getUserFirstname());
        existingUser.setSurname(updateUserDto.getUserLastName());
        existingUser.setBirthdate(updateUserDto.getBirthDate());
        existingUser.setGender(GenderEnum.valueOf(updateUserDto.getGender()));

        Region userRegion = regionService.findRegionByName(updateUserDto.getUserRegion());
        existingUser.setRegion(userRegion);

        existingUser.setDescription(updateUserDto.getDescription());

        return this.dao.save(existingUser);
    }

    public User findById(Long id){
        return this.dao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find user with that id!"));
    }

    public UserDto getUserById(String userId){
        long id = Long.parseLong(userId);
        User user = this.findById(id);

            //there will be a better way but too lazy to write a new class
        UserDto sendBackUser = new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getDescription(),
                user.getFirstName(),
                user.getSurname(),
                user.getGender().toString(),
                user.getBirthdate(),
                user.getContactInformation(),
                user.getPreferences(),
                user.getRegion()
        );

        //maybe add check if they are friends show contactInfo
        user.setContactInformation(new ContactInformation());
        return sendBackUser;
    }

    public String updateUserPreference(UpdatePreferenceDto dto, String username){
        User existingUser = this.dao.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));
        long userId = existingUser.getUserId();


        Preference pref = preferenceService.getPrefById(userId);
        Set<String> preferredGender = new HashSet<>();
        if(dto.getPreferredGender() != null){
            if (dto.getPreferredGender().length == 1 && dto.getPreferredGender()[0].equals("Samtliga")){
                preferredGender.add("MAN");
                preferredGender.add("WOMAN");
                preferredGender.add("OTHER");
            }
            else {
                preferredGender = Set.of(dto.getPreferredGender());
            }
        }
        pref.setGender(preferredGender);
        pref.setMaxAge(dto.getMaxAge());
        pref.setMinAge(dto.getMinAge());

        if(dto.getHobbies() != null) {
            pref.setHobbies(stringArrayToSet(dto.getHobbies(), "HOBBY"));
        }
        if(dto.getRegions() != null) {
            pref.setRegions(stringArrayToSet(dto.getRegions(), "REGION"));
        }

        existingUser.setPreferences(pref);
        this.dao.save(existingUser);

        return "update was successful";
    }

    public String updateUserInformation(UpdateUserInformationDto updateUserInformationDto, String username) {
        User existingUser = this.dao.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Could not cast Optional into User"));

        long userId = existingUser.getUserId();
        ContactInformation ci = contactInformationService.getContactInformation(userId);
        // Maybe add a try catch to check if CI is null.
        ci.setEmail(updateUserInformationDto.getUserEmail());
        ci.setDiscord(updateUserInformationDto.getDiscord());
        ci.setFacebook(updateUserInformationDto.getFacebook());
        ci.setSnapchat(updateUserInformationDto.getSnapchat());
        ci.setInstagram(updateUserInformationDto.getInstagram());
        ci.setPhoneNumber(updateUserInformationDto.getUserPhoneNumber());


        existingUser.setContactInformation(ci);
        existingUser.setFirstName(updateUserInformationDto.getUserFirstname());
        existingUser.setSurname(updateUserInformationDto.getUserLastName());
        existingUser.setBirthdate(updateUserInformationDto.getBirthDate());
        existingUser.setGender(GenderEnum.valueOf(updateUserInformationDto.getGender()));

        Region userRegion = regionService.findRegionByName(updateUserInformationDto.getUserRegion());
        existingUser.setRegion(userRegion);

        this.dao.save(existingUser);

        return "update was successful!";
    }

    public HashSet stringArrayToSet(String[] array, String type){
        if(type.equals("HOBBY")){
            HashSet<Hobby> prefSet = new HashSet<>();
            for(String strHobby : array) {
                Hobby hobby = hobbyService.findByHobby(strHobby);
                prefSet.add(hobby);
            }
            return prefSet;
        }
        if(type.equals("REGION")){
            HashSet<Region> prefSet = new HashSet<>();
            for(String strRegion : array) {
                Region region = regionService.findRegionByName(strRegion);
                prefSet.add(region);
            }
            return prefSet;
        }

        return new HashSet();

    }

    public List<User> getAllUsers(){
        List<User> allUsers = ((List<User>) this.dao.findAll());
        return allUsers;
    }

    public List<Match> getMatches(String username){
        User meUser = this.findUserByUsername(username);
        List<User> allUsers = this.getAllUsers();
        return matcher.matchWithAllUsers(meUser.getPreferences(),meUser,allUsers);
//        return matcher.matchWithAllUsersWithUsernameSearch(meUser.getPreferences(),meUser,allUsers);
    }

   public String uploadUserPicture(String username, String path){
        System.out.println("picture path: "+path);
       User existingUser = this.dao.findByUsername(username)
               .orElseThrow(() -> new EntityNotFoundException("could not find user"));
       existingUser.setPhoto(path);
       this.dao.save(existingUser);
       return "picture saved";
   }

   public byte[] getImage(String username){
       User existingUser = this.dao.findByUsername(username)
               .orElseThrow(() -> new EntityNotFoundException("could not find user"));

       BufferedImage img = null;
       try {
           img = ImageIO.read(new File(existingUser.getPhoto()));
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           ImageIO.write(img, "jpg", baos);
           return baos.toByteArray();
       } catch (IOException e) {
           System.out.println(e);
       }
       return null;
   }
}
