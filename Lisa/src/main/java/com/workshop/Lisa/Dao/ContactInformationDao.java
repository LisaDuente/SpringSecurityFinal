package com.workshop.Lisa.Dao;

import com.workshop.Lisa.Entity.ContactInformation;
import org.springframework.data.repository.CrudRepository;

public interface ContactInformationDao extends CrudRepository<ContactInformation, Long> {
}
