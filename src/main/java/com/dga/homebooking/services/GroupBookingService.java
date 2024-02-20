package com.dga.homebooking.services;

import com.dga.homebooking.models.GroupBooking;
import com.dga.homebooking.repo.GroupBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupBookingService {
    @Autowired
    private GroupBookingRepository groupBookingRepository;
    public void save(GroupBooking groupBooking){groupBookingRepository.save(groupBooking);}
    public GroupBooking getByID(Integer id){return groupBookingRepository.getById(id);}
    public void delete(Integer id){groupBookingRepository.deleteById(id);}
    public List<GroupBooking> getAll(){
        List<GroupBooking> result=new ArrayList<>();
        Iterable<GroupBooking> iterable=groupBookingRepository.findAll();
        iterable.forEach(result::add);
        return result;
    }
    public void remove(GroupBooking groupBooking){groupBookingRepository.deleteById(groupBooking.getId());}
}
