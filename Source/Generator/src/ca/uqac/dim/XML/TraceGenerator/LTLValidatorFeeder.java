package ca.uqac.dim.XML.TraceGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import javax.xml.parsers.ParserConfigurationException;


//Example from: http://examples.javacodegeeks.com/core-java/xml/dom/parse-xml-file-with-dom/
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LTLValidatorFeeder extends MessageFeeder{

	protected static final Random m_random = new Random();
	
	private Writer writer = null;
	private File FileIO = null;
	private int SizeMaxIO = 0;
	private int SizeFile = 0;
	private List<VariableConditions> listConditions = new LinkedList<VariableConditions>();
	private int deepTrace = 0;
	private String typeTrace = "";
	private int DeepVar = 0;
	private int DeepValue = 0;
	private boolean FileGenerated = false;
	private String StringLetters = "abcdefghijklmnopqrstuwxyz";
	
	public LTLValidatorFeeder()
	{
		super();
	}
	
	public void SetOutputFile(String path)
	{
		try 
    	{
    		//What ever the file path is.
    		FileIO = new File(path);
    		FileOutputStream is = new FileOutputStream(FileIO);
    		OutputStreamWriter osw = new OutputStreamWriter(is); 
    		writer = new BufferedWriter(osw);
    		writer.write("<Trace>\n");
    		writer.write(" \n");
    	} 
		catch (IOException e) 
    	{
    		 System.err.println("Problem to open the output File");
        }
	}
	
	private void closeFile()
	{
		try 
		{
			writer.close();
		} catch (IOException e) 
		{
			 System.err.println("Problem to close the output File");
		}
	}
	
	 public void setSizeOutputFile(int size)
	 {
		 SizeMaxIO = size;
	 }
	 
	 public int getSizeOutputFile()
	 {
		 return SizeMaxIO;
	 }
	 
	 public void setConditionsFile(String path) throws FileNotFoundException, SAXException, IOException
	 {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 dbf.setValidating(false);
		 DocumentBuilder db;
		 
		try 
		{
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(new FileInputStream(new File(path)));
			   
			//Element RootNode = (Element) doc.getElementsByTagName("conditions").item(0);
			
			NodeList itemsList = doc.getElementsByTagName("var");  
			int itemsCount = itemsList.getLength();
			int itemValueCount = 0;
			
			for (int i = 0; i<itemsCount; i++) 
			{
				//System.out.println("Item: ");
				VariableConditions Var = new VariableConditions();
				
				Node itemNode = itemsList.item(i);
				Element item = (Element) itemNode;
				 
			    //Get the name of the variable
				Node itemNameNode = item.getElementsByTagName("name").item(0);
				String itemName = itemNameNode.getFirstChild().getNodeValue();
				Var.setVarName(itemName);
				//System.out.println("Name: " + itemName);       
				
				//Get all of the value of the variable
				NodeList ListValue = item.getElementsByTagName("value");
				itemValueCount= ListValue.getLength();
				
				//Set all of the value in the object
				for (int j =0; j<itemValueCount; j++)
				{
					Node itemValueNode = item.getElementsByTagName("value").item(j);
					String itemRes = itemValueNode.getFirstChild().getNodeValue();
					//System.out.println("Value: " + itemRes); 
					
					int value = Integer.parseInt(itemRes);
					Var.addValueVar(value);
				}   
				listConditions.add(Var);

			}

		} 
		catch (ParserConfigurationException e) 
		{
			System.out.println("Parsing error for the Conditions File");
			e.printStackTrace();
		}
		 

	 }
	 
	 public void setDeepTrace(int deep)
	 {
		  deepTrace = deep;
	 }
	 
	 public void setTypeTrace(String type)
	 {
		 typeTrace = type;
	 }
	 
	 public void setDeepVar(int deep)
	 {
		 DeepVar = deep;
	 }
	 
	 public void setDeepValue(int deep)
	 {
		 DeepValue = deep;
	 }
	  
	 public boolean getFileGenerated()
	 {
		  return FileGenerated;
	 }

	 private void setFileGenerated(boolean value)
	 {
		 FileGenerated = value;
	 }
	 
	 private String ValueVariable()
	 {
		 String value ="";
		 Random m_RandValue = new Random();
		 int rand = m_RandValue.nextInt(DeepValue);
		 
		 if(typeTrace.equals("Numbers"))
		 {
			 value = String.valueOf(rand);
		 }
		 else if(typeTrace.equals("Letters"))
		 {
			 /*if(rand < 25)
			 {
				value =  StringLetters.substring(rand, rand+1);
			 }
			 else
			 {
				 while(rand >=25)
				 {
					 rand = rand - 24;
				 }
				 value =  StringLetters.substring(rand, rand+1);
			 }*/
			 
			 if(rand >= 25)
			 {
				 while(rand >=25)
				 {
					 rand = rand - 24;
				 }
			 }
			 value =  StringLetters.substring(rand, rand+1);
		 }
		 else if(typeTrace.equals("Both"))
		 {
			 m_RandValue = new Random();
			 int  test = m_RandValue.nextInt(100);
			 
			 if(test < 51) // Numbers Choice
			 {
				 value = String.valueOf(rand);
			 }
			 else // Letters Choices
			 {
				 if(rand >= 25)
				 {
					 while(rand >=25)
					 {
						 rand = rand - 24;
					 }
				 }
				 value =  StringLetters.substring(rand, rand+1);
			 }
		 }
		 else
		 {
			 value = String.valueOf(rand);
		 }
		 
		 return value;
	 }
	 
	 private void UpdateSizeFile(int addSize)
	 {
		 SizeFile += addSize;
	 }
	 
	 private int getSizeFile()
	 {
		 return SizeFile;
	 }
	 
	 private boolean FileFinish()
	 {
		 boolean Finish = false;
		 
		 int sizeFile = getSizeFile();
		 int sizeMax = getSizeOutputFile();
		 
		 if(sizeMax < sizeFile)
		 {
			Finish = true;
			 
			//Set the end of the generation
			setFileGenerated(Finish);
		 }
		/* long size = FileIO.length();
		 
		 if(SizeMaxIO < size)
		 {
			 Finish = true;
			 
			//Close the output file
			closeFile();
			//Set the end of the generation
			setFileGenerated(Finish);
		 }*/
		 
		 return Finish;
	 }
	@Override
	public String next()
	{
		boolean Finish = FileFinish();
		
		//Means the end of the generation
		if(Finish == true)
		{
			try 
			{
				writer.write("</Trace>");
				
				//Close the output file
				closeFile();
			} 
			catch (IOException e) 
			{
				System.out.println("Writing error for the end of the Output File");
				e.printStackTrace();
				
			}
			return "The generation of the Trace is over !!! ";
		}
		
		boolean ConditionFound = false;
		String XmlEvent = "<Event>\n";
		int test = m_random.nextInt(100);
		
		for(int i =0; i< deepTrace; i++)
		{
			if( test <=51)// Create a random numbers of variable
			{
				//m_random = new Random();
				int name = m_random.nextInt(DeepVar);
				String VarName = "<p" + name + ">";
				XmlEvent += VarName;
				
				//Check if their are some conditions on the variables
				if(listConditions.size() == 0)
				{
					String Value = ValueVariable();
					XmlEvent += Value;
					XmlEvent += "</p" + name + ">";
				}
				else
				{
					VariableConditions Cond = new VariableConditions();
					for(int j = 0; j< listConditions.size(); j++)
					{
						Cond = listConditions.get(j);
						String nameCond = Cond.getVarName();
						
						if(nameCond.equals("p"+ name))
						{
							List listValue = new LinkedList();
							listValue = Cond.getListValue();
							
							if(listValue.size() == 1)
							{
								XmlEvent += listValue.get(0).toString();
							}
							else
							{
								Random m_RandValue = new Random();
								int  sizeList = m_RandValue.nextInt(listValue.size());
								XmlEvent += listValue.get(sizeList).toString();
							}
							
							XmlEvent += "</p" + name + ">\n";
							ConditionFound = true;
							
							//Stop the for --> Because condition found
							j = listConditions.size();
							
						}// if(nameCond.equals(VarName))
					
					}//for listConditions
					
					if(ConditionFound != true)
					{
						String Value = ValueVariable();
						XmlEvent += Value;
						XmlEvent += "</p" + name + ">\n";
					}
					
				}// else listConditions.size() == 0
			}// end test <= 51
			test = m_random.nextInt(100);
		
			//Test
			ConditionFound = false;
		}
		XmlEvent +="</Event>\n";
		XmlEvent += " \n";
		
		int eventLenght = XmlEvent.length();
		UpdateSizeFile(eventLenght);
		
		//Write the new event in the Output File
		try 
		{
			writer.write(XmlEvent);
		} catch (IOException e) 
		{
			System.out.println("Writing error for the XML Trace Generated");
			e.printStackTrace();
		}
		
		return XmlEvent;
	}
}
