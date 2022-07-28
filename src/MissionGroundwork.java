import java.util.*;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MissionGroundwork {

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        // TODO: YOUR CODE HERE
        for (Project project: projectList){
            int[] schedule = project.getEarliestSchedule();
            project.printSchedule(schedule);
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        // TODO: YOUR CODE HERE
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try{
            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(filename));

            // optional, but recommended
            // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            // get <row>
            NodeList list = doc.getElementsByTagName("Project");
            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    NodeList list1 = element.getElementsByTagName("Task");
                    List<Task> taskList = new ArrayList<>();
                    for (int temp1 = 0; temp1 < list1.getLength(); temp1++) {
                        Node node1 = list1.item(temp1);
                        if (node1.getNodeType() == Node.ELEMENT_NODE) {
                            Element element1 = (Element) node1;
                            String id1 = element1.getElementsByTagName("TaskID").item(0).getTextContent();
                            int id = Integer.parseInt(id1);
                            String description = element1.getElementsByTagName("Description").item(0).getTextContent();
                            String duration1 = element1.getElementsByTagName("Duration").item(0).getTextContent();
                            int duration = Integer.parseInt(duration1);
                            NodeList list2 = element1.getElementsByTagName("Dependencies");
                            List<Integer> dep = new ArrayList<>();
                            for (int temp2 = 0; temp2 < list2.getLength(); temp2++) {
                                Node node2 = list2.item(temp2);
                                if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                    Element element2 = (Element) node2;
                                    NodeList list3 = element2.getElementsByTagName("DependsOnTaskID");
                                    for (int temp3 = 0; temp3 < list3.getLength(); temp3++) {
                                        Node node3 = list3.item(temp3);
                                        String dependency1 = node3.getTextContent();
                                        int dependency = Integer.parseInt(dependency1);
                                        dep.add(dependency);
                                    }
                                }
                            }
                            taskList.add(new Task(id,description,duration,dep));
                        }
                    }
                    projectList.add(new Project(name, taskList));
                }
            }
            return projectList;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
