package com.example.forever.tour.CRUDClass;

/**
 * Created by Ashif Rahman on 5/7/2017.
 */

public class Expense {
    private int expId;
    private String eventId;
    private String dateTime;
    private String expDetails;
    private String expAmt;

    private String username;

    public Expense(String username) {
        this.username = username;
    }

    public Expense(String eventId, String dateTime, String expDetails, String expAmt) {
        this.eventId = eventId;
        this.dateTime = dateTime;
        this.expDetails = expDetails;
        this.expAmt = expAmt;
    }

    /*public Expense(int expId, String eventId, String expDetails, String expAmt) {
        this.expId = expId;
        this.eventId = eventId;
        this.expDetails = expDetails;
        this.expAmt = expAmt;
    }*/

    public Expense(int expId, String eventId, String dateTime, String expDetails, String expAmt, String username) {
        this.expId = expId;
        this.eventId = eventId;
        this.dateTime = dateTime;
        this.expDetails = expDetails;
        this.expAmt = expAmt;
        this.username = username;
    }

    public Expense(String eventId, String expDetails, String expAmt) {
        this.eventId = eventId;
        this.expDetails = expDetails;
        this.expAmt = expAmt;
    }

    public Expense(String expDetails, String expAmt) {
        this.expDetails = expDetails;
        this.expAmt = expAmt;
    }

    public Expense(int expId) {
        this.expId = expId;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getExpDetails() {
        return expDetails;
    }

    public void setExpDetails(String expDetails) {
        this.expDetails = expDetails;
    }

    public String getExpAmt() {
        return expAmt;
    }

    public void setExpAmt(String expAmt) {
        this.expAmt = expAmt;
    }
}
