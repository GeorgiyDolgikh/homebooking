package com.dga.homebooking.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REFRESH,fetch =FetchType.EAGER,mappedBy = "filial")
    private List<People> peopleList;
}
