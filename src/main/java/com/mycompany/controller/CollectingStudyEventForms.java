/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.model.StudyEventForm;
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
public class CollectingStudyEventForms {
    
  
    
    public List<StudyEventForm> collectingStudyEventForms(Document doc, String studyEventOID) {
        List<StudyEventForm> lStudyEventForm = new ArrayList();
            NodeList nlMetaDataVersion = doc.getElementsByTagName("MetaDataVersion");
            NodeList nlStudyEventDef = nlMetaDataVersion.item(0).getChildNodes();
            for (int i = 0; i < nlStudyEventDef.getLength(); i++) {
                if ((nlStudyEventDef.item(i).getNodeType() == Node.ELEMENT_NODE) && (nlStudyEventDef.item(i).getNodeName().equals("StudyEventDef"))) {
                    Element eStudyEventDef = (Element) nlStudyEventDef.item(i);
                    if (eStudyEventDef.getAttribute("OID").equals(studyEventOID)) {
                        NodeList nlFormRef = nlStudyEventDef.item(i).getChildNodes();
                        for (int j = 0; j < nlFormRef.getLength(); j++) {
                            Node nFormRef = nlFormRef.item(j);
                            if ((nFormRef.getNodeType() == Node.ELEMENT_NODE) && (nFormRef.getNodeName().equals("FormRef"))) {
                                Element eFormRef = (Element) nFormRef;
                                StudyEventForm studyEventForm = new StudyEventForm(eFormRef.getAttribute("FormOID"));
                                findStudyEventFormName(doc, eFormRef.getAttribute("FormOID"), studyEventForm);
                                lStudyEventForm.add(studyEventForm); 
                            }
                        } 
                    }
                }
            }
        return lStudyEventForm;
    }
    
    public void findStudyEventFormName(Document doc,String eventFormOID, StudyEventForm studyEventForm){
         NodeList nlMetaDataVersion = doc.getElementsByTagName("MetaDataVersion");
            NodeList nlStudyEventDef = nlMetaDataVersion.item(0).getChildNodes();
            for (int i = 0; i < nlStudyEventDef.getLength(); i++) {
                if ((nlStudyEventDef.item(i).getNodeType() == Node.ELEMENT_NODE) && (nlStudyEventDef.item(i).getNodeName().equals("FormDef"))) {
                    Element eStudyEventDef = (Element) nlStudyEventDef.item(i);
                    if (eStudyEventDef.getAttribute("OID").equals(eventFormOID)) {
                       studyEventForm.setFormName(eStudyEventDef.getAttribute("Name"));
                    }
                }
            }
    }
    
    public List<StudyEventForm> collectingSubjectStudyEventForms(Document doc, String subjectOID, String studyEventOID, String studyEventRepeatingKey) {
        if (studyEventRepeatingKey.equals("0")) {
            studyEventRepeatingKey = "";
        }
        List<StudyEventForm> lSubjectStudyEventForm = new ArrayList();
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
                                        StudyEventForm studyEventForm = new StudyEventForm(eStudyEventFormData.getAttribute("FormOID"));
                                        studyEventForm.setStatus(eStudyEventFormData.getAttribute("OpenClinica:Status"));
                                        lSubjectStudyEventForm.add(studyEventForm);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return lSubjectStudyEventForm;
    }
}
