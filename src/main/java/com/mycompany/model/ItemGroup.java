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
public class ItemGroup {

    String itemGroupOID;
    String itemGroupName;
    String repeatingKey;

    public ItemGroup(String itemGroupOID) {
        this.itemGroupOID = itemGroupOID;
    }

    public String getItemGroupOID() {
        return itemGroupOID;
    }

    public void setItemGroupOID(String itemGroupOID) {
        this.itemGroupOID = itemGroupOID;
    }

    public String getItemGroupName() {
        return itemGroupName;
    }

    public void setItemGroupName(String itemGroupName) {
        this.itemGroupName = itemGroupName;
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
