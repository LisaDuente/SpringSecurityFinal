package com.workshop.Lisa.service;

import com.google.gson.Gson;
import com.workshop.Lisa.Dao.ContactDao;
import com.workshop.Lisa.Dto.ContactRequestDto;
import com.workshop.Lisa.Dto.StatusUpdateDto;
import com.workshop.Lisa.Dto.UserContactInfoDto;
import com.workshop.Lisa.Entity.Contact;
import com.workshop.Lisa.Entity.ContactInformation;
import com.workshop.Lisa.Entity.User;
import com.workshop.Lisa.Utils.ContactEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        HashSet<String> withoutPending = new HashSet<>(userOneTupleSet.stream().filter((tuple) -> !tuple.contains("PENDING")).collect(Collectors.toSet()));

        //HashSet<Long> userTwoSet = (HashSet<Long>) userTwo.stream().map(Contact::getUserOne).collect(Collectors.toSet());
        HashSet<String> userTwoTupleSet = new HashSet<String>();
        for (Contact contact: userTwo) {
            final String tuple = contact.getUserOne().toString()+","+(contact.getStatus().toString());
            userTwoTupleSet.add(tuple);
        }

        HashSet<String> withPending = new HashSet<>(userTwoTupleSet.stream().filter((tuple) -> tuple.contains("PENDING")).collect(Collectors.toSet()));

        HashSet<UserContactInfoDto> userSet = new HashSet<UserContactInfoDto>();

        extractUserInfo(withoutPending, userSet);

        extractUserInfo(withPending, userSet);

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
                userSet.add(new UserContactInfoDto(
                        id,
                        tempUser.getUsername(),
                        tempUser.getFirstName(),
                        tempUser.getSurname(),
                        contactInfo,
                        status));
            }else{
                ContactInformation contactInfo = new ContactInformation();
                userSet.add(new UserContactInfoDto(
                        id,
                        tempUser.getUsername(),
                        tempUser.getFirstName(),
                        tempUser.getSurname(),
                        contactInfo,
                        status));
            }
        }
    }

    public String updateStatus(String username, String userId, String status) {
        long id = this.userService.findUserByUsername(username).getUserId();
        Contact contact = this.contactDao.findContactByUserOneAndUserTwo(id, Long.parseLong(userId));
        Contact contact2 = this.contactDao.findContactByUserOneAndUserTwo( Long.parseLong(userId), id);
        if (contact == null && contact2 == null) {
            return "No friend request found";
        }
       // if(contact == null){
        //    this.contactDao.save(new Contact(id, Long.parseLong(userId), ContactEnum.valueOf(status)));
        //}
        if(contact != null){
            contact.setStatus(ContactEnum.valueOf(status));
            this.contactDao.save(contact);
        }
        if(contact2 != null){
            //if status is FRIEDNS we will accept the request
            if(status.equals("FRIENDS")){
                this.contactDao.save(new Contact(id,contact2.getUserOne(), ContactEnum.FRIENDS));
            }
            contact2.setStatus(ContactEnum.valueOf(status));
            this.contactDao.save(contact2);
        }
        //both contacts will be blocked if one of the users blocked the other one
        return "Status successfully updated!";
    }

    public String deleteEntry(String username, String id) {

        long userId = this.userService.findUserByUsername(username).getUserId();
        Contact contact = this.contactDao.findContactByUserOneAndUserTwo(userId, Long.parseLong(id));
        Contact contact2 = this.contactDao.findContactByUserOneAndUserTwo(Long.parseLong(id), userId);
        if (contact == null && contact2 == null) {
            return "No relationship/contact status found";
        }
        if (contact == null) {
            contactDao.delete(contact2);
        }
        if (contact2 == null) {
            contactDao.delete(contact);
        }
        if(contact != null && contact2 != null){
            contactDao.delete(contact);
            if(!contact.getStatus().toString().equals("BLOCKED")){
                contactDao.delete(contact2);
            }

        }
        return "successfully updated request";

    }

    public String createFriendRequest(String username, ContactRequestDto contactRequestDto) {
        long userIdOne = this.userService.findUserByUsername(username).getUserId();
        long userIdTwo = Long.parseLong(contactRequestDto.getFriendID());
        Contact contact = new Contact(userIdOne, userIdTwo, ContactEnum.PENDING);

        if(userIdOne == userIdTwo){
            return "You can not add yourself!";
        }
        Contact contactCheckOne = contactDao.findContactByUserOneAndUserTwo(userIdOne, userIdTwo);
        Contact contactCheckTwo = contactDao.findContactByUserOneAndUserTwo(userIdTwo, userIdOne);

        if(contactCheckOne == null && contactCheckTwo == null) {
            contactDao.save(contact);
            return "Friend request sent";
        }
        else if(contactCheckTwo == null) {
            return "Unable to send the request.";
        }
        else if(contactCheckTwo.getStatus().equals(ContactEnum.PENDING) && contactCheckOne == null){
            contactDao.save(new Contact(userIdOne, userIdTwo, ContactEnum.FRIENDS));
            contactDao.save(new Contact(userIdTwo, userIdOne, ContactEnum.FRIENDS));
            return "You are now friends!";
        }
        else {
            return "Friend request already sent";
        }
    }

    public String getFriendStatus(String username, String userIdTwo){
        Gson gson = new Gson();

        long userIdOne = this.userService.findUserByUsername(username).getUserId();
        Contact contact = this.contactDao.findContactByUserOneAndUserTwo(userIdOne, Long.parseLong(userIdTwo));
        //do we need a check on the second contact?
        //Contact contact2 = this.contactDao.findContactByUserOneAndUserTwo(Long.parseLong(userIdTwo), userIdOne);

//        if(contact != null && contact2 != null){
//            return contact.getStatus().toString();
//        }
        if(contact != null){
            return gson.toJson(contact.getStatus());
        }

        return gson.toJson("NO STATUS FOUND");
    }

    public Contact findContactForUserOneUserTwo(long userOneId, long userTwoId){
        return this.contactDao.findContactByUserOneAndUserTwo(userOneId, userTwoId);
    }
}
