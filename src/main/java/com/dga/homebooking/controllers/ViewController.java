package com.dga.homebooking.controllers;

import com.dga.homebooking.models.*;
import com.dga.homebooking.repo.FilialRepository;
import com.dga.homebooking.services.ExcursionsService;
import com.dga.homebooking.services.GroupBookingService;
import com.dga.homebooking.services.HomeReservationService;
import com.dga.homebooking.services.SmenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Controller
public class ViewController {
    @Autowired
    private SmenaService smenaService;
    @Autowired
    private HomeReservationService homeReservationService;
    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private GroupBookingService groupBookingService;
    @Autowired
    private ExcursionsService excursionsService;
    private Smena smena;

    @PostMapping ("/deletereservation")
    public String deleteReservation(HomeReservation homeReservation, Model model){

        Smena s=homeReservation.getSmena();
        s.getHomeReservations().remove(homeReservation);
        homeReservationService.remove(homeReservation);
        setModelForViewDay(model,s);
        return "/views/viewday";
    }

    @GetMapping("/viewall")
    public String viewAll(Model model){
        List<GroupBooking> groupBookings=groupBookingService.getAll();
        List<HomeReservation> allreservation=homeReservationService.getAll();
        model.addAttribute("allreservation", allreservation);
        List<Smena> smenas=smenaService.GetAll();
        Smena smena1=smenaService.getSmenaByID(2);
        HomeReservation h1=smena1.gethomeReservationByhomeID(1);
        HomeReservation h2=smena1.gethomeReservationByhomeID(2);

        model.addAttribute("smenas",smenas);
        return "/views/viewall";
    }
    @GetMapping("/viewhomereservation/{id}")
    public String showhomereservation(@PathVariable Integer id, Model model){
        HomeReservation homeReservation=homeReservationService.get(id);
        model.addAttribute(homeReservation);
        return "/views/viewhomereservation";
    }
    @GetMapping("/viewday")
    public String selectday(Model model){
        List<Smena> smenas=smenaService.GetAll();
        model.addAttribute("smenas",smenas);
        return "/views/viewday";
    }
    @GetMapping("/viewday/{id}")
    public String viewdayGet(@PathVariable Integer id,Model model){
        Smena  smena=smenaService.getSmenaByID(id);
        setModelForViewDay(model,smena);
        return "/views/viewday";
    }

    @PostMapping("/viewday")
    public String viewday (Model model, Smena smena){
        setModelForViewDay(model,smena);

        return "/views/viewday";
    }
    private void  setModelForViewDay(Model model,Smena smena){
        List<Smena> smenas=smenaService.GetAll();
        model.addAttribute("smenas",smenas);
        model.addAttribute("smena",smena);
        TreeMap<String,Integer> excursions=new TreeMap<>();
        //  агрегируем экскурсии
        if(smena.getHomeReservations()!=null){
            for(HomeReservation h:smena.getHomeReservations()){
                if(!h.getExcursions().isEmpty()){
                    for (Excursions e:h.getExcursions()){
                        if(excursions.containsKey(e.getName()))excursions.put(e.getName(),excursions.get(e.getName())+1);
                        else excursions.put(e.getName(),1);
                    }
                }
            }
        }
        model.addAttribute("excursions", excursions);
    }
}
