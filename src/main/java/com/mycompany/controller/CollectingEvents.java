/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.model.StudyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author sa841
 */
public class CollectingEvents {

    public Object[] collectingStudyEvents() {
        List<StudyEvent> lStudyEvent = new ArrayList();
        Object[] res = new Object[2];
        try {
            File inputFile = new File("C:\\Users\\sa841\\Documents\\sync_first.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            res[0] = doc;
            NodeList nlStudyEvent = doc.getElementsByTagName("StudyEventDef");
            for (int i = 0; i < nlStudyEvent.getLength(); i++) {
                Node nStudyEvent = nlStudyEvent.item(i);
                if ((nStudyEvent.getNodeType() == Node.ELEMENT_NODE) ) {
                    Element eStydyEvent = (Element) nStudyEvent;
                    StudyEvent studyEvent = new StudyEvent(eStydyEvent.getAttribute("OID"));
                    studyEvent.setEventName(eStydyEvent.getAttribute("Name"));
                    lStudyEvent.add(studyEvent);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.getMessage();
        }
        res[1] = lStudyEvent;
        return res;
    }

    public List<StudyEvent> collectingSubjectStudyEvents(String subjectOID, Document doc) {
        List<StudyEvent> lSubjectStudyEvent = new ArrayList();
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
                            StudyEvent studyEvent = new StudyEvent(eStudyEventData.getAttribute("StudyEventOID"));
                            studyEvent.setStatus(eStudyEventData.getAttribute("OpenClinica:Status"));
                            studyEvent.setRepeatingKey(eStudyEventData.getAttribute("StudyEventRepeatKey"));
                            lSubjectStudyEvent.add(studyEvent);
                        }
                    }
                }
            }
        }
        return lSubjectStudyEvent;
    }
}
