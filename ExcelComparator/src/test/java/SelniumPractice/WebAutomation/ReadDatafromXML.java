package SelniumPractice.WebAutomation;
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.w3c.dom.Node;  
import org.w3c.dom.Element;  
import java.io.File;  
public class ReadDatafromXML 
{  
public static void main(String argv[])   
{  
try   
{  
//creating a constructor of file class and parsing an XML file  
File file = new File("C:\\Users\\ankit\\Desktop\\Excel\\Book1XML.xml");  
//an instance of factory that gives a document builder  
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
//an instance of builder to parse the specified xml file  
DocumentBuilder db = dbf.newDocumentBuilder();  
Document doc = db.parse(file);  
doc.getDocumentElement().normalize();  
System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
NodeList nodeList = doc.getElementsByTagName("si");  
System.out.println("nodeList" +nodeList.getLength());
// nodeList is not iterable, so we are using for loop  
for (int itr = 0; itr < nodeList.getLength(); itr++)   
{  
Node node = nodeList.item(itr);  
System.out.println("\nNode Name :" + node.getNodeName());  
if (node.getNodeType() == Node.ELEMENT_NODE)   
{  
Element eElement = (Element) node;  
System.out.println("column ID: "+ eElement.getElementsByTagName("t").item(0).getTextContent());  
//System.out.println("Data Value: "+ eElement.getElementsByTagName("V").item(0).getTextContent());

}  
}  
}   
catch (Exception e)   
{  
e.printStackTrace();  
}  
}  
}  


//import java.io.File;  
//import javax.xml.parsers.DocumentBuilder;  
//import javax.xml.parsers.DocumentBuilderFactory;  
//import org.w3c.dom.Document;  
//import org.w3c.dom.NamedNodeMap;  
//import org.w3c.dom.Node;  
//import org.w3c.dom.NodeList;  
//public class ReadXMLFileExample2  
//{  
//public static void main(String[] args)  
//{  
//try   
//{  
//File file = new File("F:\\XMLFile.xml");  
//DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();  
//Document document = documentBuilder.parse(file);  
//System.out.println("Root element: "+ document.getDocumentElement().getNodeName());  
//if (document.hasChildNodes())   
//{  
//printNodeList(document.getChildNodes());  
//}  
//}   
//catch (Exception e)  
//{  
//System.out.println(e.getMessage());  
//}  
//}  
//private static void printNodeList(NodeList nodeList)  
//{  
//for (int count = 0; count < nodeList.getLength(); count++)   
//{  
//Node elemNode = nodeList.item(count);  
//if (elemNode.getNodeType() == Node.ELEMENT_NODE)   
//{  
//// get node name and value  
//System.out.println("\nNode Name =" + elemNode.getNodeName()+ " [OPEN]");  
//System.out.println("Node Content =" + elemNode.getTextContent());  
//if (elemNode.hasAttributes())   
//{  
//NamedNodeMap nodeMap = elemNode.getAttributes();  
//for (int i = 0; i < nodeMap.getLength(); i++)   
//{  
//Node node = nodeMap.item(i);  
//System.out.println("attr name : " + node.getNodeName());  
//System.out.println("attr value : " + node.getNodeValue());  
//}  
//}  
//if (elemNode.hasChildNodes())   
//{  
////recursive call if the node has child nodes  
//printNodeList(elemNode.getChildNodes());  
//}  
//System.out.println("Node Name =" + elemNode.getNodeName()+ " [CLOSE]");  
//}  
//}  
//}  
//}  