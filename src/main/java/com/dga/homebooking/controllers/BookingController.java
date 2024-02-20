package com.dga.homebooking.controllers;
import com.dga.homebooking.models.*;
import com.dga.homebooking.repo.FilialRepository;
import com.dga.homebooking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BookingController {
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
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private UserService userService;

    @GetMapping({"/","/index"})
    public String start(){
        return "index";
    }

    @GetMapping({"/groupbooking"})
    public String homeControl( Model model) {
        model.addAttribute("step", "1");
        return "/groupbooking";
    }

    @PostMapping({"/groupbooking"})
    public String groupPost( @RequestParam String count, @RequestParam String isHoliday, Model model) {
        model.addAttribute("step", "2");


        model.addAttribute("count", count);
        Integer dayOfWeek;
        if (isHoliday.equals("0")) {
            model.addAttribute("isHoliday", "Будние дни");
            dayOfWeek = 1;
        } else {
            dayOfWeek = 5;
            model.addAttribute("isHoliday", "Выходные дни");
        }
        //Добываем смены

        List<Smena> smenas = smenaService.getSmenasWhereHomesIsEmptyByWeekday(Integer.valueOf(count), dayOfWeek);
        model.addAttribute("smenaList", smenas);


        return "/groupbooking";
    }

    @PostMapping({"/groupbooking2", "/"})
    public String groupPost2(@RequestParam Smena startdate, @RequestParam String count, @RequestParam String fio,
                             @RequestParam String email, @RequestParam String mobile, @RequestParam String comment,
                             Model model) {
        //Smena s=smenaService.getSmenaByID(Integer.valueOf(startdate));

        GroupBooking groupBooking = new GroupBooking();
        groupBooking.setOwner(new People(fio, mobile, email, "1", null,null));
        groupBooking.setSmena(startdate);

        //List<HomeReservation> homeReservations = new ArrayList<>();
        for (int i = 0; i < Integer.valueOf(count); i++) {
  //          Smena s=smenaService.getSmenaByID(startdate.getId());
            HomeReservation h=new HomeReservation(comment, startdate,groupBooking);
            homeReservationService.save(h);
            //smenaService.save(s);
        }
//        for(HomeReservation h:homeReservations){
//
//        }
        return "redirect:/viewall";
    }

    @GetMapping("/personalbooking")
    public String personalGet(Model model) {
        List<Smena> smenas = smenaService.getSmenasWhereHomeIsEmpty();
        List<Excursions> excursions = excursionsService.getAll();
        model.addAttribute("step", "1");
        model.addAttribute("smenaList", smenas);
        model.addAttribute("excursions", excursions);
        return "/personalbooking";
    }

    @PostMapping("/personalbooking")
    public String personalPost(Principal principal, @ModelAttribute("homeReservation") HomeReservation homeReservation, Model model) {
        User author= userService.getUserByEvail(principal.getName());
        model.addAttribute("homereservation", homeReservation);
        List<Filial> filials = new ArrayList<>();
        filialRepository.findAll().forEach(filials::add);
        model.addAttribute("filiallist", filials);
        model.addAttribute("homeReservation", homeReservation);
        model.addAttribute("user",author);
        return "/personalbooking2";
    }

    @GetMapping("/confirmed")
    public String confirmGet(Model model) {
        return "/confirmed";
    }

    @PostMapping("/confirmed")
    public String confirmedPost(@RequestParam HomeReservation homeReservation, Model model) {
        return"/personalbooking";
    }
    @PostMapping("/personalbooking2")
    public String pbooking2Post(Principal principal,@ModelAttribute("homeReservation") HomeReservation homeReservation,
           @RequestParam(value = "isemployee") String[] isemployees,
           @RequestParam(value = "fullname") String[] fullnames, @DateTimeFormat(pattern = "yyyy-MM-dd")
           @RequestParam(value = "birthdate") Date[] birthdates,
           @RequestParam(value = "email") String[] emails, @RequestParam(value = "phone") String[] phones,
           @RequestParam(value = "filial") Filial[] filials, Model model) {
        User author= userService.getUserByEvail(principal.getName());
        homeReservation.setOwner(author);
        Smena smena=smenaService.getSmenaByID(homeReservation.getSmena().getId());


        homeReservation.setHomeNumber(smena.getEmptyHome());
        List<People> p = new ArrayList<>();
        for (int i = 0; i < fullnames.length; i++) {
            if (!fullnames[i].isEmpty()) {
                p.add(new People(fullnames[i], phones[i], emails[i], isemployees[i], filials[i],birthdates[i],homeReservation));

            }
        }
        //Вычисляем норер домика
        //считываем из базы актуальные данные по смене


       // homeReservation.setPeoples(p);
        for(People pl:p){
            peopleService.save(pl);
        }
        //homeReservationService.save(homeReservation);
        return"/confirmed";
    }
    @GetMapping("/removeall")
    public String removeall(Model model){
        List<GroupBooking> groupBookings=groupBookingService.getAll();
        for(GroupBooking g:groupBookings){
            groupBookingService.remove(g);
        }
        List<HomeReservation> homeReservations=homeReservationService.getAll();
        for(HomeReservation h:homeReservations){
            homeReservationService.remove(h);
        }
        return "/viewall";
    }
    @GetMapping("/editbooking/{id}")
    public String editbooking(Model model,@PathVariable Integer id,Principal principal){

        return "/personalbooking2";
    }
}