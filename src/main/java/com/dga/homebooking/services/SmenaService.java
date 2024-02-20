package com.dga.homebooking.services;

import com.dga.homebooking.models.Smena;
import com.dga.homebooking.repo.SmenaRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmenaService {
    @Autowired
    private SmenaRepository smenaRepository;
    public List<Smena>GetSmenaByDay(Integer weekday){
        long countC;
        Calendar c = Calendar.getInstance();
        Iterable<Smena> lst=smenaRepository.findAll();
        List<Smena> smenaList=new ArrayList<>();
        List<Smena> smenaList2;
        smenaRepository.findAll().forEach(smenaList::add);
        countC=smenaList.stream().filter((p)->p.getStartdate().getDay()==weekday).count();
        smenaList2=smenaList.stream().filter((p)->p.getStartdate().getDay()==weekday).collect(Collectors.toList());
        return smenaList2;
    }
    public List<Smena>GetAll(){
        List<Smena> smenaList=new ArrayList<>();
        smenaRepository.findAll().forEach(smenaList::add);
        return smenaList;
    }
    public void save(Smena smena){
        smenaRepository.save(smena);
    }
    public Smena getSmenaByID(Integer id){return smenaRepository.getSmenaById(id);}
    public List<Smena> getSmenasWhereHomesIsEmptyByWeekday(Integer howmanyhome,Integer weekday){
        List<Smena> all=GetSmenaByDay(weekday);
        if(all==null)return null;
        List<Smena>result=new ArrayList<>();
        all.stream().filter(p->p.getHomeReservations().size()<=(8-howmanyhome)).forEach(result::add);
        return result;
    }
    public List<Smena>getSmenasWhereHomeIsEmpty(){
        List<Smena> all=GetAll();
        if(all==null)return null;
        List<Smena>result=new ArrayList<>();
        all.stream().filter(p->p.getHomeReservations().size()<8).forEach(result::add);
        return result;
    }
}
