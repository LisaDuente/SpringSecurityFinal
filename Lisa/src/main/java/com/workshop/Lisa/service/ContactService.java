package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.ContactDao;
import com.workshop.Lisa.Dto.StatusUpdateDto;
import com.workshop.Lisa.Dto.UserContactInfoDto;
import com.workshop.Lisa.Entity.Contact;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.Utils.ContactEnum;
import com.workshop.Lisa.Entity.ContactInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactDao contactDao;
    private final UserService userService;
    private final ContactInformationService contactInformationService;

    public Set<UserContactInfoDto> getFriendsInfo(String username) {
        long userId = this.userService.findUserByUsername(username).getUserId();

        List<Contact> userOne = contactDao.findByUserOne(userId);
        List<Contact> userTwo = contactDao.findByUserTwo(userId);

        //HashSet<Long> userOneSet = (HashSet<Long>) userOne.stream().map(Contact::getUserTwo).collect(Collectors.toSet());
        HashSet<String> userOneTupleSet = new HashSet<String>();
        for (Contact contact: userOne) {
            final String tuple = contact.getUserTwo().toString()+","+(contact.getStatus().toString());
            userOneTupleSet.add(tuple);
        }

        //HashSet<Long> userTwoSet = (HashSet<Long>) userTwo.stream().map(Contact::getUserOne).collect(Collectors.toSet());
        HashSet<String> userTwoTupleSet = new HashSet<String>();
        for (Contact contact: userTwo) {
            final String tuple = contact.getUserOne().toString()+","+(contact.getStatus().toString());
            userTwoTupleSet.add(tuple);
        }

        HashSet<UserContactInfoDto> userSet = new HashSet<UserContactInfoDto>();

        extractUserInfo(userOneTupleSet, userSet);

        extractUserInfo(userTwoTupleSet, userSet);

        return userSet;

    }

    private void extractUserInfo(HashSet<String> userTwoTupleSet, HashSet<UserContactInfoDto> userSet) {
        for (String tuple : userTwoTupleSet) {
            String[] temp = tuple.split(",");
            long id = Long.parseLong(temp[0]);
            String status = temp[1];
            User tempUser = userService.findById(id);
            if(tempUser.getContactInformation() != null){
                ContactInformation contactInfo = contactInformationService.getContactInformation(tempUser.getUserId());
                userSet.add(new UserContactInfoDto(id,tempUser.getUserName(), tempUser.getUserEmail(), contactInfo, status));
            }else{
                ContactInformation contactInfo = new ContactInformation();
                userSet.add(new UserContactInfoDto(id,tempUser.getUserName(), tempUser.getUserEmail(), contactInfo, status));
            }

        }
    }

    public String updateStatus(String username, StatusUpdateDto dto) {
        long userId = this.userService.findUserByUsername(username).getUserId();
        Contact contact = this.contactDao.findContactByUserOneAndUserTwo(userId,Long.parseLong(dto.getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("Could not find a friend request!"));
        contact.setStatus(ContactEnum.valueOf(dto.getStatus()));
        this.contactDao.save(contact);
        return "Status successfully updated!";
    }
}
