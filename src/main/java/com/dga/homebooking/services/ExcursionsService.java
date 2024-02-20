package com.dga.homebooking.services;

import com.dga.homebooking.models.Excursions;
import com.dga.homebooking.repo.ExcursionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcursionsService {
    @Autowired
    ExcursionsRepository excursionsRepository;

    public List<Excursions> getAll(){
        List<Excursions> result=new ArrayList<>();
        excursionsRepository.findAll().forEach(result::add);
        return result;
    }
}
