package com.si2001.webapp.dto;

import com.si2001.webapp.entity.Reservation;
import java.io.Serializable;
import java.util.Date;

public class ReservationDto implements Serializable {

    private int id;
    private String user;
    private String vehicle;
    private Date resBegin;
    private Date resEnd;
    private boolean approved;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public ReservationDto(Reservation reservation) {
        this.id = reservation.getId();
        this.user = reservation.getUserId().getUsername();
        this.vehicle = reservation.getVehicleId().getTarga();
        this.resBegin = reservation.getResBegin();
        this.resEnd = reservation.getResEnd();
        this.approved = reservation.isApproved();
    }

    public ReservationDto(int id, String user, String vehicle, Date resBegin, Date resEnd,boolean approved) {
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
        this.resBegin = resBegin;
        this.resEnd = resEnd;
        this.approved= approved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
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
