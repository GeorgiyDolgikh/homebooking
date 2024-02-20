package com.dga.homebooking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="homereservation")
public class HomeReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_in;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date_out;
    private String time_in;
    private String time_out;
    private String comment;
    private String admin_comment;
    private Integer status;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private Smena smena;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "homeReservation")
    private List<People> peoples;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private User owner;//Возможно сотрудник бронирует за других - возможно агентов. Будет проставляться автором бронирования
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Excursions> excursions;
    @ManyToOne (cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST},fetch = FetchType.EAGER)
    @ToString.Exclude
    private GroupBooking groupBooking;
    private Integer homeNumber;//Номер домика при бронировании

    public HomeReservation(String comment, Smena smena,GroupBooking groupBooking) {
        this.comment = comment;
        this.smena = smena;
        this.groupBooking=groupBooking;
        this.homeNumber=smena.getEmptyHome();
        smena.getHomeReservations().add(this);
    }
    public People getOwner(){
        if(!this.peoples.isEmpty()){
            return peoples.getFirst();
        }else{
            //групповое бронироание
            return groupBooking.getOwner();
        }
    }
    public Date getSmenaDate(){
        return this.smena.getStartdate();
    }
    public HomeReservation(Date date_in, Date date_out, String time_in, String time_out, String comment, Smena smena, List<Excursions> excursions) {
        this.date_in = date_in;
        this.date_out = date_out;
        this.time_in = time_in;
        this.time_out = time_out;
        this.comment = comment;
        this.smena = smena;
        this.excursions = excursions;
        smena.getHomeReservations().add(this);//для нумерации
        this.homeNumber=smena.getEmptyHome();
    }
    public String getdate_in_Formatted(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date_in);
    }
    public String getSmenaFormattedDate(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(smena.getStartdate());
    }
    public String getEndSmenaFormattedDate(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(smena.getEndSmena());
    }
    public String getdate_out_Formatted() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date_out);
    }
}
