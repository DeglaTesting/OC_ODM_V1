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
public class Item {
    String itemOID;
    String itemName;
    String value;

    public Item(String itemOID) {
        this.itemOID = itemOID;
    }


    public String getItemOID() {
        return itemOID;
    }

    public void setItemOID(String itemOID) {
        this.itemOID = itemOID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
}
