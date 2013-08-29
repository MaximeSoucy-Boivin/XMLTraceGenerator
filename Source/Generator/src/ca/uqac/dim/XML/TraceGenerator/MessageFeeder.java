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
import java.util.Iterator;

import org.xml.sax.SAXException;

public abstract class MessageFeeder implements Iterator<String>
{
  @Override
  public boolean hasNext()
  {
    return true;
  }

  @Override
  public final void remove()
  {
    // Do nothing
  }
  
 
  public void SetOutputFile(String path)
  {
	  
  }
  
  public void setSizeOutputFile(int size)
  {
	  
  }
  
  public void setConditionsFile(String path)throws FileNotFoundException, SAXException, IOException
  {
	  
  }
  
  public void setDeepTrace(int deep)
  {
	  
  }
  
  public void setTypeTrace(String type)
  {
	  
  }
  
  public void setDeepVar(int deep)
  {
	  
  }
  
  public void setDeepValue(int deep)
  {
	  
  }
  
  public boolean getFileGenerated()
  {
	  return false;
  }

}
