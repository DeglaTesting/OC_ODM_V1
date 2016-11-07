/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controller;

import com.mycompany.model.*;
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
public class CollectingSubjects {

    List<String> subjectOutput;
    List<String> lSubjectOID;

    public CollectingSubjects() {
        subjectOutput = new ArrayList();
    }

    public void collectingSubjectMetaData(Document doc, String subjectOID) {

        //  List<StudyEvent> lSubjectStudyEvent = new ArrayList();
        CollectingEvents CollectingEvents = new CollectingEvents();
        List<StudyEvent> lSubjectStudyEvent = CollectingEvents.collectingSubjectStudyEvents(subjectOID, doc);
        for (int i = 0; i < lSubjectStudyEvent.size(); i++) {
            String sSubjectStudyEvent = lSubjectStudyEvent.get(i).getEventOID();
            sSubjectStudyEvent = sSubjectStudyEvent.concat(" " + lSubjectStudyEvent.get(i).getRepeatingKey());
            sSubjectStudyEvent = sSubjectStudyEvent.concat(" " + lSubjectStudyEvent.get(i).getStatus());
            //  List<StudyEventForm> lSubjectStudyEventForm = new ArrayList();
            CollectingStudyEventForms collectingStudyEventForms = new CollectingStudyEventForms();
            List<StudyEventForm> lSubjectStudyEventForm = collectingStudyEventForms.collectingSubjectStudyEventForms(doc, "SS_LAPTOP_S", lSubjectStudyEvent.get(i).getEventOID(), lSubjectStudyEvent.get(i).getRepeatingKey());
            for (int j = 0; j < lSubjectStudyEventForm.size(); j++) {
                String sSubjectStudyEventForm = lSubjectStudyEventForm.get(j).getFormOID();
                sSubjectStudyEventForm = sSubjectStudyEventForm.concat(" " + lSubjectStudyEventForm.get(j).getStatus());
                CollectingItemGroup collectingItemGroup = new CollectingItemGroup();
                // List<ItemGroup> lSubjectItemGroup = new ArrayList();
                List<ItemGroup> lSubjectItemGroup = collectingItemGroup.collectingSubjectItemGroups(doc, "SS_LAPTOP_S", lSubjectStudyEvent.get(i).getEventOID(), lSubjectStudyEvent.get(i).getRepeatingKey(), lSubjectStudyEventForm.get(j).getFormOID());
                for (int k = 0; k < lSubjectItemGroup.size(); k++) {
                    String sSubjectItemGroup = lSubjectItemGroup.get(k).getItemGroupOID();
                    sSubjectItemGroup = sSubjectItemGroup.concat(" " + lSubjectItemGroup.get(k).getRepeatingKey());
                    // List<Item> lSubjectItem = new ArrayList();
                    CollectingItems collectingITems = new CollectingItems();
                    List<Item> lSubjectItem = collectingITems.collectingSubjectItems(doc, "SS_LAPTOP_S", lSubjectStudyEvent.get(i).getEventOID(), lSubjectStudyEvent.get(i).getRepeatingKey(), lSubjectStudyEventForm.get(j).getFormOID(), lSubjectItemGroup.get(k).getItemGroupOID());
                    for (int m = 0; m < lSubjectItem.size(); m++) {
                        String sSubjetcItem = lSubjectItem.get(m).getItemOID();
                        sSubjetcItem = sSubjetcItem.concat(" " + lSubjectItem.get(m).getValue());
                        String out = subjectOID;
                        out = out.concat(" " + sSubjectStudyEvent).concat(" " + sSubjectStudyEventForm).concat(" " + sSubjectItemGroup).concat(" " + sSubjetcItem);
                        subjectOutput.add(out);
                    }
                }
            }
        }
    }

    public void collectingSubjects(Document doc) {
        lSubjectOID = new ArrayList();
        NodeList nlSubjectData = doc.getElementsByTagName("SubjectData");
        for (int i = 0; i < nlSubjectData.getLength(); i++) {
            Node nSubjetcData = nlSubjectData.item(i);
            if ((nSubjetcData.getNodeType() == Node.ELEMENT_NODE) && (nSubjetcData.getNodeName().equals("SubjectData"))) {
                Element eSubjetcData = (Element) nSubjetcData;
                if (lSubjectOID.contains(eSubjetcData.getAttribute("SubjectKey")) == false) {
                    lSubjectOID.add(eSubjetcData.getAttribute("SubjectKey"));
                }
            }
        }
    }

    public void collectingSubjectsMetaData() {
        try {
            File inputFile = new File("C:\\Users\\sa841\\Documents\\sync_first.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            collectingSubjects(doc);
            for (int i = 0; i < lSubjectOID.size(); i++) {
                collectingSubjectMetaData(doc, lSubjectOID.get(i));
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.getMessage();
        }
    }

    public void print() {
        for (int i = 0; i < subjectOutput.size(); i++) {
            System.out.println(subjectOutput.get(i));
        }
    }

    public static void main(String[] args) {
        CollectingSubjects collectingSubjects = new CollectingSubjects();
        collectingSubjects.collectingSubjectsMetaData();
        collectingSubjects.print();
    }
}
