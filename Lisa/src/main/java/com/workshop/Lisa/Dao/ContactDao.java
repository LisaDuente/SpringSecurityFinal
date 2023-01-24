package com.workshop.Lisa.Dao;

import com.workshop.Lisa.Entity.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDao extends CrudRepository<Contact, Long> {

    List<Contact> findByUserOne(Long userId);
    List<Contact> findByUserTwo(Long userId);
}
