/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.model.Item;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author sa841
 */
public class CollectingItems {

    public List<Item> collectingItems(Document doc, String studyItemGroupOID) {
        List<Item> lItem = new ArrayList();
        NodeList nlItemGroup = doc.getElementsByTagName("ItemGroupDef");
        for (int i = 0; i < nlItemGroup.getLength(); i++) {
            Node nItemGroup = nlItemGroup.item(i);
            if (nItemGroup.getNodeType() == Node.ELEMENT_NODE) {
                Element eItemGroup = (Element) nItemGroup;
                if (eItemGroup.getAttribute("OID").equals(studyItemGroupOID)) {
                    NodeList nlItem = nItemGroup.getChildNodes();
                    for (int j = 0; j < nlItem.getLength(); j++) {
                        Node nItem = nlItem.item(j);
                        if ((nItem.getNodeType() == Node.ELEMENT_NODE) && (nItem.getNodeName().equals("ItemRef"))) {
                            Element eItem = (Element)nItem;
                            Item item = new Item(eItem.getAttribute("ItemOID"));
                            findItemName(doc, eItem.getAttribute("ItemOID"), item);
                            lItem.add(item);
                        }
                    }
                }
            }
        }
        return lItem;
    }
    
    public void findItemName(Document doc, String itemOID, Item item){
        NodeList nlItem = doc.getElementsByTagName("ItemDef");
        for (int i = 0; i < nlItem.getLength(); i++) {
            Node nItem = nlItem.item(i);
            if (nItem.getNodeType() == Node.ELEMENT_NODE) {
                Element eItem= (Element) nItem;
                if (eItem.getAttribute("OID").equals(itemOID)) {
                    item.setItemName(eItem.getAttribute("Name"));
                }
            }
        }  
    }

    public List<Item> collectingSubjectItems(Document doc, String subjectOID, String studyEventOID, String studyEventRepeatingKey, String formOID, String groupItemOID) {
        List<Item> lSubjectItem = new ArrayList();

        if (studyEventRepeatingKey.equals("0")) {
            studyEventRepeatingKey = "";
        }
        NodeList nlClinicalData = doc.getElementsByTagName("ClinicalData");
        NodeList nlSubjectData = nlClinicalData.item(0).getChildNodes();
        for (int i = 0; i < nlSubjectData.getLength(); i++) {
            Node nSubjectData = nlSubjectData.item(i);
            if ((nSubjectData.getNodeType() == Node.ELEMENT_NODE) && (nSubjectData.getNodeName().equals("SubjectData"))) {
                Element eSubjectData = (Element) nSubjectData;
                if (eSubjectData.getAttribute("SubjectKey").equals(subjectOID)) {
                    NodeList nlStudyEventData = eSubjectData.getChildNodes();
                    for (int j = 0; j < nlStudyEventData.getLength(); j++) {
                        Node nStudyEventData = nlStudyEventData.item(j);
                        if ((nStudyEventData.getNodeType() == Node.ELEMENT_NODE) && (nStudyEventData.getNodeName().equals("StudyEventData"))) {
                            Element eStudyEventData = (Element) nStudyEventData;
                            if ((eStudyEventData.getAttribute("StudyEventOID").equals(studyEventOID)) && (eStudyEventData.getAttribute("StudyEventRepeatKey").equals(studyEventRepeatingKey))) {
                                NodeList nlStudyEventFormData = eStudyEventData.getChildNodes();
                                for (int k = 0; k < nlStudyEventFormData.getLength(); k++) {
                                    Node nStudyEventFormData = nlStudyEventFormData.item(k);
                                    if ((nStudyEventFormData.getNodeType() == Node.ELEMENT_NODE) && (nStudyEventFormData.getNodeName().equals("FormData")) == true) {
                                        Element eStudyEventFormData = (Element) nStudyEventFormData;
                                        if (eStudyEventFormData.getAttribute("FormOID").equals(formOID)) {
                                            NodeList nlItemGroupData = nStudyEventFormData.getChildNodes();
                                            for (int l = 0; l < nlItemGroupData.getLength(); l++) {
                                                Node nItemGroupData = nlItemGroupData.item(l);
                                                if ((nItemGroupData.getNodeType() == Node.ELEMENT_NODE) && (nItemGroupData.getNodeName().equals("ItemGroupData"))) {
                                                    Element eItemGroupData = (Element) nItemGroupData;
                                                    if (eItemGroupData.getAttribute("ItemGroupOID").equals(groupItemOID)) {
                                                        NodeList nlItemData = nItemGroupData.getChildNodes();
                                                        for (int m = 0; m < nlItemData.getLength(); m++) {
                                                            Node nItemData = nlItemData.item(m);
                                                            if ((nItemData.getNodeType() == Node.ELEMENT_NODE) && (nItemData.getNodeName().equals("ItemData"))) {
                                                                Element eItemData = (Element) nItemData;
                                                                Item item = new Item(eItemData.getAttribute("ItemOID"));
                                                                item.setValue(eItemData.getAttribute("Value"));
                                                                lSubjectItem.add(item);

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return lSubjectItem;
    }
}
