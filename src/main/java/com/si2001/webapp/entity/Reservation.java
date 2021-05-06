package com.si2001.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "prenotazione")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "auto_id", referencedColumnName = "id")
    private Vehicle vehicleId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_di_inizio")
    private Date resBegin;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_di_fine")
    private Date resEnd;
    @Column
    private boolean approved;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getResBegin() {
        return resBegin;
    }

    public void setResBegin(Date resBegin) {
        this.resBegin = resBegin;
    }

    public Date getResEnd() {
        return resEnd;
    }

    public void setResEnd(Date resEnd) {
        this.resEnd = resEnd;
    }
}

