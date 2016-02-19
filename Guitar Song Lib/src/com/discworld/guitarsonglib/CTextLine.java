package com.discworld.guitarsonglib;


import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "text-line")
public class CTextLine
{
   @Text
   public String sTextLine = "";
   
   public CTextLine()
   {
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
