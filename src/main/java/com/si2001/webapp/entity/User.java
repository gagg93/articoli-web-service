package com.si2001.webapp.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
public class User implements Serializable {

    @Id
    @Column(name = "Id")
    private int id;
    @Column
    private boolean admin;
    @Column
    private String username;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String password;
    @Temporal(TemporalType.DATE)
    @Column
    private Date birthDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "userId", orphanRemoval = true)
    @JsonManagedReference
    private Set<Reservation> reservationSet = new HashSet<>();
}
