package com.example.forever.tour.CRUDClass;

/**
 * Created by Ashif Rahman on 5/4/2017.
 */

public class Event {
    private int evid;
    private int  userId;
    private String  userName;

    private String  traDestination;
    private String  traBudget;
    private String  traFromDate;
    private String  traToDate;

    private String  totalExpAmt;


    public Event(String traDestination, String traBudget, String traFromDate, String traToDate) {
        this.traDestination = traDestination;
        this.traBudget = traBudget;
        this.traFromDate = traFromDate;
        this.traToDate = traToDate;
    }




    public Event(int evid, String traDestination, String traBudget, String traFromDate, String traToDate) {
        this.evid = evid;
        this.traDestination = traDestination;
        this.traBudget = traBudget;
        this.traFromDate = traFromDate;
        this.traToDate = traToDate;
    }

    public Event(int evid, String userName, String traDestination, String traBudget, String traFromDate, String traToDate, String totalExpAmt) {
        this.evid = evid;
        this.userName = userName;
        this.traDestination = traDestination;
        this.traBudget = traBudget;
        this.traFromDate = traFromDate;
        this.traToDate = traToDate;
        this.totalExpAmt = totalExpAmt;
    }

    public Event(int evid, String userName, String traDestination, String traBudget, String traFromDate, String traToDate) {
        this.evid = evid;
        this.userName = userName;
        this.traDestination = traDestination;
        this.traBudget = traBudget;
        this.traFromDate = traFromDate;
        this.traToDate = traToDate;
    }

    public Event(String userName, String traDestination, String traBudget, String traFromDate, String traToDate) {
        this.userName = userName;
        this.traDestination = traDestination;
        this.traBudget = traBudget;
        this.traFromDate = traFromDate;
        this.traToDate = traToDate;
    }

    public Event(String userName) {
        this.userName = userName;
    }

    public int getEvid() {
        return evid;
    }

    public void setEvid(int evid) {
        this.evid = evid;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTraDestination() {
        return traDestination;
    }

    public void setTraDestination(String traDestination) {
        this.traDestination = traDestination;
    }

    public String getTraBudget() {
        return traBudget;
    }

    public void setTraBudget(String traBudget) {
        this.traBudget = traBudget;
    }

    public String getTraFromDate() {
        return traFromDate;
    }

    public void setTraFromDate(String traFromDate) {
        this.traFromDate = traFromDate;
    }

    public String getTraToDate() {
        return traToDate;
    }

    public void setTraToDate(String traToDate) {
        this.traToDate = traToDate;
    }

    public String getTotalExpAmt() {
        return totalExpAmt;
    }

    public void setTotalExpAmt(String totalExpAmt) {
        this.totalExpAmt = totalExpAmt;
    }
}
