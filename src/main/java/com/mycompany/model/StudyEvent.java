/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.model;


/**
 *
 * @author sa841
 */
public class StudyEvent {

    String eventOID;
    String eventName;
    String status;
    String repeatingKey;

    public StudyEvent(String eventOID) {
        this.eventOID = eventOID;
    }

    public String getEventOID() {
        return eventOID;
    }

    public void setEventOID(String eventOID) {
        this.eventOID = eventOID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.length() < 1) {
            this.status = "unknown";
        } else {
            this.status = status;
        }
    }

    public String getRepeatingKey() {
        return repeatingKey;
    }

    public void setRepeatingKey(String repeatingKey) {
        if (repeatingKey.length() < 1) {
            this.repeatingKey = "0";
        } else {
            this.repeatingKey = repeatingKey;
        }
    }

}
