package com.workshop.Lisa.service;

import com.workshop.Lisa.Dao.ContactInformationDao;
import com.workshop.Lisa.Entity.ContactInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ContactInformationService {

    private final ContactInformationDao dao;

    public ContactInformation getContactInformation(long userId){
        return this.dao.findById(userId).orElseThrow(() -> new EntityNotFoundException("Could not find contact Information for user " + userId));
    }

    public ContactInformation createContactInfo(ContactInformation contactInformation) {
        return this.dao.save(contactInformation);
    }

    public ContactInformation updateContactInformation(ContactInformation contactInformation) {
        return this.dao.save(contactInformation);
    }
}
