/*
        XMLTraceGenerator, a XML trace generator 
        Copyright (C) 2013 Maxime Soucy-Boivin

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.dim.XML.TraceGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.*;
import org.xml.sax.SAXException;

public class XMLTraceGenerator
{
  /**
   * Return codes
   */
  public static final int ERR_OK = 0;
  public static final int ERR_PARSE = 2;
  public static final int ERR_IO = 3;
  public static final int ERR_ARGUMENTS = 4;
  public static final int ERR_RUNTIME = 6;
  
  public static void main(String[] args)
  {
	String generator_name = "";
	boolean FileGenerated = false;
	    
    // Parse command line arguments
    Options options = setupOptions();
    CommandLine c_line = setupCommandLine(args, options);
   
    if (c_line.hasOption("h"))
    {
      showUsage(options);
      System.exit(ERR_OK);
    }
    
    //Contains a generator
    if (c_line.hasOption("g"))
    {
      generator_name = c_line.getOptionValue("generator");
    }
    else
    {
    	System.err.println("No Generator in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
    
    //Get the MessageFeeder for the generator
    MessageFeeder mf = instantiateFeeder(generator_name);
    
    //Contains a FileWriter
    if(c_line.hasOption("f"))
    {
    	mf.SetOutputFile(c_line.getOptionValue("FileWriter"));
    }
    else
    {
    	System.err.println("No FileWriter in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
    
    //Contains a size for the output file
    if(c_line.hasOption("s"))
    {
    	int size = Integer.parseInt(c_line.getOptionValue("Size"));
    	mf.setSizeOutputFile(size);
    }
    else
    {
    	System.err.println("No size for the Output File in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
    
    
    //Contains conditions for the generation of the XML's Trace
    if(c_line.hasOption("c"))
    {
    	try 
    	{
			mf.setConditionsFile(c_line.getOptionValue("Conditions"));
		} 
    	catch (FileNotFoundException e) 
    	{
			System.out.println("The file of the conditions wasn't found !!!");
			e.printStackTrace();
		} 
    	catch (SAXException e) 
    	{
			System.out.println("SAXException : XML Exeception !!!");
			e.printStackTrace();
		} 
    	catch (IOException e) 
    	{
			System.out.println("IOException : Input/Ouput Exception !!!");
			e.printStackTrace();
		}
    }
    
    //Contains the deep for the Traces
    if(c_line.hasOption("d"))
    {
    	mf.setDeepTrace(Integer.parseInt(c_line.getOptionValue("Deep")));
    }
    else
    {
    	System.err.println("No Deep in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
    
    //Contains the type of the variables
    if(c_line.hasOption("t")) 
    {
    	mf.setTypeTrace(c_line.getOptionValue("Type"));
    }
    else
    {
    	System.err.println("No type in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
    
    //Contains the variable deep
    if(c_line.hasOption("v"))
    {
    	mf.setDeepVar(Integer.parseInt(c_line.getOptionValue("VariableDeep")));
    }
    else
    {
    	System.err.println("No variable deep in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
    
    //Contains the value deep
    if(c_line.hasOption("w"))
    {
    	mf.setDeepValue(Integer.parseInt(c_line.getOptionValue("DeepValue")));
    }
    else
    {
    	System.err.println("No value deep in Arguments");
        System.exit(ERR_ARGUMENTS);
    }
   
    FileGenerated = mf.getFileGenerated();
    
    System.out.println("-----------------------------------------------");
    System.out.println("The generation of the Trace is start !!!");
    System.out.println("-----------------------------------------------");
    
    while(FileGenerated !=true)
    {
    	//Get The current XML Trace
    	String message = mf.next();
    	System.out.println(message);
    	
    	//Update the State of the generation
    	FileGenerated = mf.getFileGenerated();
    }
    System.out.println("-----------------------------------------------");
  }
  
  /**
   * Sets up the options for the command line parser
   * @return The options
   */
  @SuppressWarnings("static-access")
  private static Options setupOptions()
  {
    Options options = new Options();
    Option opt;
    opt = OptionBuilder
        .withLongOpt("help")
        .withDescription(
            "Display command line usage")
            .create("h");
    options.addOption(opt);
    opt = OptionBuilder
        .withLongOpt("FileWriter")
        .withArgName("x")
        .hasArg()
        .withDescription(
            "File who contain the XML generated")
            .create("f");
    options.addOption(opt);
    opt = OptionBuilder
        .withLongOpt("generator")
        .withArgName("name")
        .hasArg()
        .withDescription(
            "Use generator name")
            .create("g");
    options.addOption(opt);
    opt = OptionBuilder
        .withLongOpt("Size")
        .withArgName("n")
        .hasArg()
        .withDescription(
            "Size maximum of the output XML File (in bytes)")
            .create("s");
    options.addOption(opt);
    opt = OptionBuilder
        .withLongOpt("Type")
        .withArgName("t")
        .hasArg()
        .withDescription(
            "Type of the variables of the XML Trace (Numbers , Letters, Both)")
            .create("t");
    options.addOption(opt);
    opt = OptionBuilder
            .withLongOpt("Conditions")
            .withArgName("x")
            .hasArg()
            .withDescription(
                "File who contains the variable conditions")
                .create("c");
        options.addOption(opt);
     opt = OptionBuilder
             .withLongOpt("Deep")
             .withArgName("x")
             .hasArg()
             .withDescription(
                  "Deep of the Traces")
                  .create("d");
         options.addOption(opt);  
     opt = OptionBuilder
              .withLongOpt("VariableDeep")
              .withArgName("x")
              .hasArg()
              .withDescription(
                  "Deep of the Variables")
              .create("v");
         options.addOption(opt);
     opt = OptionBuilder
              .withLongOpt("DeepValue")
              .withArgName("x")
              .hasArg()
              .withDescription(
                  "Deep of the values")
              .create("w");
          options.addOption(opt);
    return options;
  }

  /**
   * Show the benchmark's usage
   * @param options The options created for the command line parser
   */
  private static void showUsage(Options options)
  {
    HelpFormatter hf = new HelpFormatter();
    hf.printHelp("java -jar XMLTraceGenerator.jar [options]", options);
  }

  /**
   * Sets up the command line parser
   * @param args The command line arguments passed to the class' {@link main}
   * method
   * @param options The command line options to be used by the parser
   * @return The object that parsed the command line parameters
   */
  private static CommandLine setupCommandLine(String[] args, Options options)
  {
    CommandLineParser parser = new PosixParser();
    CommandLine c_line = null;
    try
    {
      // parse the command line arguments
      c_line = parser.parse(options, args);
    }
    catch (org.apache.commons.cli.ParseException exp)
    {
      // oops, something went wrong
      System.err.println("ERROR: " + exp.getMessage() + "\n");
      System.exit(ERR_ARGUMENTS);    
    }
    return c_line;
  }
  
  public static MessageFeeder instantiateFeeder(String name)
  {
    MessageFeeder mf = null;
    if (name.compareToIgnoreCase("LTLValidator") == 0)
    	 mf = new LTLValidatorFeeder();
    return mf;
  }
  
}
