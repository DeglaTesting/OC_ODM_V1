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
public class StudyEventForm {

    String formOID;
    String formName;
    String status;

    public StudyEventForm(String formOID) {
        this.formOID = formOID;
    }

    public String getFormOID() {
        return formOID;
    }

    public void setFormOID(String formOID) {
        this.formOID = formOID;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
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

}
