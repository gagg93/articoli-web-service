package com.si2001.webapp.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "VEHICLES")
@Data
public class Vehicle {

    @Id
    @Column(name = "Id")
    private int id;
    @Column
    private String casaCostruttrice;
    @Column
    private String modello;
    @Column
    private int annoImmatricolazione;
    @Column
    private String targa;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "vehicleId", orphanRemoval = true)
    @JsonManagedReference
    private Set<Reservation> reservationSet = new HashSet<>();
}
