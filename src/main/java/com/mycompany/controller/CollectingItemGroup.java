/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.model.ItemGroup;
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
public class CollectingItemGroup {

    public List<ItemGroup> collectingItemGroup(Document doc, String studyEventFormOID) {
        List<ItemGroup> lItemGroup = new ArrayList();
        NodeList nlFormDef = doc.getElementsByTagName("FormDef");
        for (int i = 0; i < nlFormDef.getLength(); i++) {
            Node nFormDef = nlFormDef.item(i);
            if (nFormDef.getNodeType() == Node.ELEMENT_NODE) {
                Element eFormDef = (Element) nFormDef;
                if (eFormDef.getAttribute("OID").equals(studyEventFormOID)) {
                    NodeList nlItemGroupRef = nFormDef.getChildNodes();
                    for (int j = 0; j < nlItemGroupRef.getLength(); j++) {
                        Node nItemGroupRef = nlItemGroupRef.item(j);
                        if ((nItemGroupRef.getNodeType() == Node.ELEMENT_NODE) && (nItemGroupRef.getNodeName().equals("ItemGroupRef"))) {
                            Element eItemGroupRef = (Element)nItemGroupRef;
                            ItemGroup itemGroup = new ItemGroup(eItemGroupRef.getAttribute("ItemGroupOID"));
                            findItemGroupName(doc, eItemGroupRef.getAttribute("ItemGroupOID"), itemGroup);
                            lItemGroup.add(itemGroup);
                        }
                    }
                }
            }
        }
        return lItemGroup;
    }

    public void findItemGroupName(Document doc, String itemGroupOID, ItemGroup itemGroup) {
        NodeList nlGroupDef = doc.getElementsByTagName("ItemGroupDef");
        for (int i = 0; i < nlGroupDef.getLength(); i++) {
            Node nGroupDef = nlGroupDef.item(i);
            if (nGroupDef.getNodeType() == Node.ELEMENT_NODE) {
                Element eGroupDef = (Element) nGroupDef;
                if (eGroupDef.getAttribute("OID").equals(itemGroupOID)) {
                    itemGroup.setItemGroupName(eGroupDef.getAttribute("Name"));
                }
            }
        }
    }

    public List<ItemGroup> collectingSubjectItemGroups(Document doc, String subjectOID, String studyEventOID, String studyEventRepeatingKey, String formOID) {
        List<ItemGroup> lSubjectItemGroup = new ArrayList();
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
                                                    ItemGroup itemGroup = new ItemGroup(eItemGroupData.getAttribute("ItemGroupOID"));
                                                    itemGroup.setRepeatingKey(eItemGroupData.getAttribute("ItemGroupRepeatKey"));
                                                    lSubjectItemGroup.add(itemGroup);
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

        return lSubjectItemGroup;
    }

}
