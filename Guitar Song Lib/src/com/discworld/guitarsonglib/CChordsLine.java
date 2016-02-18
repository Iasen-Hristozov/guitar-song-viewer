package com.discworld.guitarsonglib;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class CChordsLine extends ArrayList<CChord>
{
   /**
    * 
    */
   private static final long serialVersionUID = -5073417906207483516L;

   public CChordsLine()
   {
      super();
   }

   public void addChord(CChord oChord)
   {
      add(oChord);
   }
   
   @XmlElement(name = "chord")
   public ArrayList<CChord> getChords() 
   {
     return this;
   }

   @Override
   public String toString()
   {
      String sChordsLine = "";
      for(CChord oChord : this)
      {
         sChordsLine += (sChordsLine.equals("") ? "" : ", ")
                  + oChord.toString();
      }
      return sChordsLine;
   }
}
