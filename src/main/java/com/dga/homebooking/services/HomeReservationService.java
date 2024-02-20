package com.dga.homebooking.services;

import com.dga.homebooking.models.GroupBooking;
import com.dga.homebooking.models.HomeReservation;
import com.dga.homebooking.models.People;
import com.dga.homebooking.models.Smena;
import com.dga.homebooking.repo.HomeReservationRepository;
import com.dga.homebooking.repo.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class HomeReservationService {
    @Autowired
    HomeReservationRepository homeReservationRepository;
    @Autowired
    PeopleRepository peopleRepository;
    @Autowired
    GroupBookingService groupBookingService;
    @Autowired
    PeopleService peopleService;
    public void save(HomeReservation homeReservation){
        homeReservationRepository.save(homeReservation);
    }
    public int howmanybooking(Smena smena){
        //return homeReservationRepository.get
        return 1;
    }
    public HomeReservation get(Integer id){

        return homeReservationRepository.findById(id).orElse(null);
    }
    public List<HomeReservation> getAll(){
        Iterable<HomeReservation> iterable;
        List<HomeReservation>result=new ArrayList<>();
        iterable=homeReservationRepository.findAll();
        iterable.forEach(result::add);


        return result.stream().sorted(Comparator.comparing(HomeReservation::getSmenaDate)).toList();
    }
    public void remove(HomeReservation homeReservation){
//        if(homeReservation.getGroupBooking()!=null){
//            GroupBooking g=homeReservation.getGroupBooking();
//            g.getHomeReservations().remove(homeReservation);
//            if(g.getHomeReservations().isEmpty()){
//                peopleService.remove(g.getOwner());
//                homeReservationRepository.delete(homeReservation);
//                groupBookingService.remove(g);
//            }else {
//                groupBookingService.save(g);
//                homeReservationRepository.delete(homeReservation);
//            }
//
//        }
        if(homeReservation.getGroupBooking()==null) {
            homeReservationRepository.delete(homeReservation);
        }
        else{
            GroupBooking g=homeReservation.getGroupBooking();
            if(g.getHomeReservations().size()==1) {
                groupBookingService.remove(g);
            }else{
                g.getHomeReservations().remove(homeReservation);
                groupBookingService.save(g);
                homeReservationRepository.delete(homeReservation);
            }

        }





    }


}
