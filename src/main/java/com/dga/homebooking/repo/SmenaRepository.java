package com.dga.homebooking.repo;

import com.dga.homebooking.models.Smena;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface SmenaRepository extends CrudRepository<Smena,Integer> {
//
//    @Query("select u from Smena  u where WEEKDAY(u.startdate)=?1")
//    //@Query("select u.*,IF(c IS NULL,0,c) countbooking from smena u LEFT JOIN (select smena_id sid, count(*) as c FROM home_reservation GROUP BY smena_id )vv ON u.id=vv.sid;")
//    //@Query("select u.*,IF(c IS NULL,0,c) countbooking from smena u LEFT JOIN (select smena_id sid, count(*) as c FROM home_reservation GROUP BY smena_id )vv ON u.id=vv.sid where WEEKDAY(u.startdate)=0")
//    List<Smena> getSmenaByWeekday(Integer weekday);
//
//    @Query("select u, h from Smena u LEFT JOIN HomeReservation h ON u.id=h.id where  WEEKDAY(u.startdate)=?1" )
//
//    Smena getSmenaByStartdate(Date date);
//
//    @Query("select u from Smena u where WEEKDAY(u.startdate)=?1 AND u.countbooking<=?2")
//    List<Smena> getAvailableSmenaByWeekday(Integer weekday, Integer howmanyhome);
//
//
    public Smena getSmenaById(Integer id);


}
