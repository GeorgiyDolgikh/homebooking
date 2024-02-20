package com.dga.homebooking.services;

import com.dga.homebooking.models.People;
import com.dga.homebooking.repo.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeopleService{
    @Autowired
    PeopleRepository peopleRepository;

    public void save(People people){
        peopleRepository.save(people);
    }
    public void remove(People people){peopleRepository.delete(people);}
}
