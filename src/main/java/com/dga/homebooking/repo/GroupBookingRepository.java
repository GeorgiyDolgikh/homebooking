package com.dga.homebooking.repo;

import com.dga.homebooking.models.GroupBooking;
import org.springframework.data.repository.CrudRepository;

public interface GroupBookingRepository extends CrudRepository<GroupBooking,Integer> {
    GroupBooking getById(Integer id);
    void deleteById(Integer id);
}
