package ca.uqac.dim.XML.TraceGenerator;

import java.util.LinkedList;
import java.util.List;

public class VariableConditions {
	
	private String VarName = "";
	private List<Integer> listValueVarInt = new LinkedList<Integer>();
	private List<String> listValueVarStr = new LinkedList<String>();
	
	public VariableConditions()
	{
		
	}
	
	/**
	* Sets up the name of the variable
	* @param name the name in a string format
	*/
	public void setVarName(String name)
	{
		VarName = name;
	}
	
	/**
	* Gets the name of the variable
	* @return the name in a string format
	*/
	public String getVarName()
	{
		return VarName;
	}
	
	/**
	* Sets up a value in the value list of the variable . Used for the type Numbers
	* @param value the value in a integer format
	*/
	public void addValueVar(int value)
	{
		listValueVarInt.add(value);
	}
	
	/**
	* Gets the list of the value of the variable. Used for the type Numbers
	* @return the list in a format of List<Integer>
	*/
	public List<Integer> getListValueInt()
	{
		return listValueVarInt;
	}
	
	/**
	* Sets up a value in the value list of the variable. Used for the type Letters and Both 
	* @param value the value in a string format
	*/
	public void addValueVar(String value)
	{
		listValueVarStr.add(value);
	}
	
	/**
	* Gets the list of the value of the variable
	* @return the list in a format of List<String>
	*/
	public List<String> getListValueStr()
	{
		return listValueVarStr;
	}

}
