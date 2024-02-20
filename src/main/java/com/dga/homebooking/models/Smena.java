package com.dga.homebooking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "smena_list")
public class Smena{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date startdate;
    private String description;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REFRESH,fetch =FetchType.EAGER,mappedBy = "smena")
    private List<HomeReservation> homeReservations=new ArrayList<>();
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER,mappedBy = "smena")
    List<GroupBooking>groupBookings;


    public String getFormattedDate(){
        String pattern = "dd MMM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(startdate);
    }
    public Date getEndSmena(){
        Calendar cal=Calendar.getInstance();
        cal.setTime(startdate);
        if(cal.get(Calendar.DAY_OF_WEEK)==2){
            cal.add(Calendar.DAY_OF_MONTH,3);
        }else{
            cal.add(Calendar.DAY_OF_MONTH,2);
        }
        return cal.getTime();

    }
    public HomeReservation gethomeReservationByhomeID(Integer homeId){
        return homeReservations.stream().filter(h-> Objects.equals(h.getHomeNumber(), homeId)).findFirst().orElse(null);
    }

    public Integer getEmptyHome(){
        int chome=1;
        boolean flag=true;
        for(int i=1;i<9 && flag;i++){
            flag=false;
            chome=i;
            for(HomeReservation h: getHomeReservations()){
                if(h.getHomeNumber()!=null && h.getHomeNumber()==i){flag=true;}
            }
        }
        return chome;
    }

}
