package com.dga.homebooking.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class GroupBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "groupBooking")
    private List<HomeReservation> homeReservations;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private People owner;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST},fetch = FetchType.EAGER)
    Smena smena;
}
