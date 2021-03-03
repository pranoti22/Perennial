package perennialbank.excpt;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ExceptionHandler {
	Actions a;
	
	Properties prop=new Properties();
	Logact l =new Logact();
	Map<String,String > actions=new HashMap<String,String>();
	void handleException(String project,String module, Exception ex)
	{    DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
         DocumentBuilder builder;
		try {
			FileReader reader= new FileReader("ActionNames.properties");
			prop.load(reader);
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse("/Users/HP/Desktop/perennial/Try/NewFile.xml");
			
	         Element rootEle =doc.getDocumentElement();
	         String projectnm=rootEle.getAttribute("name");
	         System.out.println(projectnm);
	         NodeList moduleNodes=rootEle.getChildNodes();
	         for(int i=0;i<moduleNodes.getLength();i++)
	         {
	        	 Node moduleNode=moduleNodes.item(i);
	        	 if(moduleNode.getNodeType()==Node.ELEMENT_NODE && ((Element)moduleNode).getAttribute("name").equals(module)) {
	        		 //System.out.println(""+((Element)moduleNode).getAttribute("name"));
	        		 NodeList excptNodes=moduleNode.getChildNodes();
	        		for(int j=0;j<excptNodes.getLength();j++)
	        		{
	        			Node excpetNode=excptNodes.item(j);
	        			if(excpetNode.getNodeType()==Node.ELEMENT_NODE)
	        			{
	        				String excpetName= ((Element)excpetNode).getNodeName();
	        				if(excpetName.equals(ex.getClass().getSimpleName())) {
	        				NodeList actionNodes=excpetNode.getChildNodes();
	        				for(int l=0;l<actionNodes.getLength();l++)
	        				{
	        					Node actionNode=actionNodes.item(l);
	        					if(actionNode.getNodeType()==Node.ELEMENT_NODE)
	        					{
	        					System.out.println(((Element)actionNode).getNodeName());
	        					NodeList action=actionNode.getChildNodes();
	        					for(int m=0;m<action.getLength();m++)
	        					{  String actionName;                               ///change
	        						Node act=action.item(m);
	        						if(act.getNodeType()==Node.ELEMENT_NODE) {
	        						//System.out.println(((Element)act).getNodeName());
	        						actionName=((Element)act).getAttribute("name");
	        						 NamedNodeMap x= act.getAttributes();
	        						 Map<String,String> map=new HashMap<String,String>();     ///change
	        						 for (int p = 0; p < x.getLength(); p++) {
	        					            Attr attr = (Attr) x.item(p);
	        					             
	        					            String attrName = attr.getNodeName();
	        					            String attrValue = attr.getNodeValue();
	        					            map.put(attrName, attrValue);
	        					            
	        					             
	        					            //System.out.println("Found attribute: " + attrName + " with value: " + attrValue);
	        					             
	        					        }
	        						 /*if(map.get("name").equals("email")) {
	        							 
	        							System.out.println(new EmailAct().performAction(map));
	        						 }
	        						 else if(map.get("name").equals("log"))
	        						 {
	        							System.out.println( new Logact().performAction(map));
	        						 }
	        						 else if(map.get("name").equals("abort"))
	        						 {
	        							 System.out.println(new Revert().performAction(map));
	        						 }*/
	        						// for (Map.Entry<String, String> entry : map.entrySet()) {
	        						//	    System.out.println(" "+entry.getKey()+" "+entry.getValue()+" ");
	        						 String className=prop.getProperty(actionName);
		        						Actions actions = (Actions)Class.forName(className).newInstance();
		        						System.out.println(actions.performAction(map));
									
																					
		        						
	        							    
	        							}
	        						
	        						
	        						
	        						
	        						}
	        					}
	        					}
	        				}
	        				}
	        				
	        			}
	        		}
	        		
	               }
	             
	         //a.performAction(actions); 
	        		 
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        		 
        	 
         
	}   
}
	
	
/* NodeList exceptionNodes = moduleNode.getChildNodes();
	for(int j=0;j<exceptionNodes.getLength();j++)
     {  Node exceptionNode=exceptionNodes.item(i);
         if(exceptionNode.getNodeType()==Node.ELEMENT_NODE) {
        	 String excptName=((Element)exceptionNode).getNodeName();
            	 if(excptName.equals(ex.getClass().getSimpleName())) {
            		 NodeList actionNode=exceptionNode.getChildNodes();
            		  System.out.println(((Element)actionNode).getNodeName());
            		// for(int k=0;k<actionNode.getLength();k++)
            		 //{
            			 
            		 //}
            		 
            	 }
    	  }

  //System.out.println(exceptionName);
     }*/
	
	



