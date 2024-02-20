package com.dga.homebooking.repo;

import com.dga.homebooking.models.HomeReservation;
import com.dga.homebooking.models.Smena;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeReservationRepository extends CrudRepository<HomeReservation,Integer> {
        public Integer getAllBySmena(Smena smena);

}
