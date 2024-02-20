package com.dga.homebooking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date birthdate;
    private String fullname;
    private String phone;
    private String email;
    private String isemployee;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    private Filial filial;
    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST},  fetch = FetchType.EAGER)
    private HomeReservation homeReservation;

    public People(String fullname, String phone, String email, String isemployee, Filial filial, Date birthdate, HomeReservation homeReservation) {
        this.birthdate = birthdate;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.isemployee = isemployee;
        this.filial = filial;
        this.homeReservation = homeReservation;
    }

    public People(String fullname, String phone, String email, String isemployee, Filial filial, Date birthdate) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.isemployee = isemployee;
        this.filial=filial;
        this.birthdate=birthdate;
    }
    public String getPublicBirthdayInfo(){
        //По детям - РЕБЕНОК ВОЗРАСТ
            Date ds=getHomeReservation().getSmenaDate();
            Date df=getHomeReservation().getDate_out();
            Calendar c=Calendar.getInstance();
            c.setTime(ds);
            c.add(Calendar.DATE,-4);
            Date dstomorrow=c.getTime();

            String pattern = "dd MMM";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            int ages=calculateAge(birthdate,dstomorrow);
            int agef=calculateAge(birthdate,df);
            String isEmpl="";
            if(isemployee.equals("1")){isEmpl=", сотрудник";}
            if(isemployee.equals("2")){isEmpl=", агент";}
            if(isemployee.equals("3")){isEmpl=", гость";}
            if(isemployee.equals("4")){isEmpl=", партнер";}

            String isChild="";
            if(agef<17) isChild=", "+agef+" лет";
//            System.out.println(fullname+" "+agef+" "+ages+" "+simpleDateFormat.format(birthdate)+" "+simpleDateFormat.format(dstomorrow));
            String isBirthday="";
            if(agef!=ages) isBirthday=", день рождения ("+simpleDateFormat.format(birthdate)+")";


            return isEmpl+isChild+isBirthday;
    }
    private  int calculateAge(final Date birthday, Date thisdate)
    {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.setTime(birthday);
        today.setTime(thisdate);


        dob.add(Calendar.DAY_OF_MONTH, -1);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
}
