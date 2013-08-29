package ca.uqac.dim.XML.TraceGenerator;

import java.util.LinkedList;
import java.util.List;
//import java.util.ListIterator;

public class VariableConditions {
	
	private String VarName = "";
	private List listValueVar = new LinkedList();
	
	public VariableConditions()
	{
		
	}
	
	public void setVarName(String name)
	{
		VarName = name;
	}
	
	public String getVarName()
	{
		return VarName;
	}
	
	public void addValueVar(int value)
	{
		listValueVar.add(value);
	}
	
	/*public Object getValueVar(int pos)
	{
		return listValueVar.get(pos);
	}*/
	
	public List getListValue()
	{
		return listValueVar;
	}

}
