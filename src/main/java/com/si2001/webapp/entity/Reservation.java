package com.si2001.webapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATIONS")
@Data
public class Reservation {

    @Id
    @Column
    private int id;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "Id", referencedColumnName = "id")
    @JsonBackReference
    private User userId;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "Id", referencedColumnName = "id")
    @JsonBackReference
    private Vehicle vehicleId;
    @Temporal(TemporalType.DATE)
    @Column
    private Date resBegin;
    @Temporal(TemporalType.DATE)
    @Column
    private Date resEnd;
}
