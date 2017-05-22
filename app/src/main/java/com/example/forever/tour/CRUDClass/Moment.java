package com.example.forever.tour.CRUDClass;

import java.io.Serializable;

/**
 * Created by Ashif Rahman on 5/7/2017.
 */

public class Moment implements Serializable {
    private int momentId;
    private String eventId;
    private String mDateTime;
    private String momentPhotopath;
    private String momentDetails;

    private String username;

    public Moment(int momentId) {
        this.momentId = momentId;
    }

    public Moment(int momentId,String username, String eventId, String mDateTime, String momentPhotopath, String momentDetails) {
        this.username = username;
        this.momentId = momentId;
        this.eventId = eventId;
        this.mDateTime = mDateTime;
        this.momentPhotopath = momentPhotopath;
        this.momentDetails = momentDetails;
    }

    public Moment(String eventId, String mDateTime, String momentPhotopath, String momentDetails) {
        this.eventId = eventId;
        this.mDateTime = mDateTime;
        this.momentPhotopath = momentPhotopath;
        this.momentDetails = momentDetails;
    }

    public Moment(String momentPhotopath, String momentDetails) {
        this.momentPhotopath = momentPhotopath;
        this.momentDetails = momentDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMomentId() {
        return momentId;
    }

    public void setMomentId(int momentId) {
        this.momentId = momentId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getmDateTime() {
        return mDateTime;
    }

    public void setmDateTime(String mDateTime) {
        this.mDateTime = mDateTime;
    }

    public String getMomentPhotopath() {
        return momentPhotopath;
    }

    public void setMomentPhotopath(String momentPhotopath) {
        this.momentPhotopath = momentPhotopath;
    }

    public String getMomentDetails() {
        return momentDetails;
    }

    public void setMomentDetails(String momentDetails) {
        this.momentDetails = momentDetails;
    }
}
