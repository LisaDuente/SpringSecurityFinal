package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.ContactDao;
import com.workshop.Lisa.Entity.Contact;
import com.workshop.Lisa.Entity.User;
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

    public Set<User> getFriendsInfo(String username) {
        long userId = this.userService.findUserByUsername(username).getUserId();

        List<Contact> userOne = contactDao.findByUserOne(userId);
        List<Contact> userTwo = contactDao.findByUserTwo(userId);

        HashSet<Long> userOneSet = (HashSet<Long>) userOne.stream().map(Contact::getUserTwo).collect(Collectors.toSet());

        HashSet<Long> userTwoSet = (HashSet<Long>) userTwo.stream().map(Contact::getUserOne).collect(Collectors.toSet());


        HashSet<User> userSet = new HashSet<User>();

        for (long id : userOneSet) {
            userSet.add(userService.findById(id));
        }

        for (long id : userTwoSet) {
            userSet.add(userService.findById(id));
        }

        return userSet;

    }
}
