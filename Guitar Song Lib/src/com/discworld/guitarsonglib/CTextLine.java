package com.discworld.guitarsonglib;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "text-line")
public class CTextLine
{
   @XmlValue
   public String sTextLine;
   
   public CTextLine()
   {
      sTextLine = "";
   }
   
   public CTextLine(String sTextLine)
   {
      this.sTextLine = sTextLine;
   }
   
   @Override
   public String toString()
   {
      return sTextLine;
   }
}
