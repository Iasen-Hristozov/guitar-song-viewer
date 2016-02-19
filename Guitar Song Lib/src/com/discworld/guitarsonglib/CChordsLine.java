package com.discworld.guitarsonglib;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "chords-line")
public class CChordsLine extends ArrayList<CChord>
{
   /**
    * 
    */
   private static final long serialVersionUID = -5073417906207483516L;

   @ElementList(inline=true)
   ArrayList<CChord> oChordsLine = this;
   
   public CChordsLine()
   {
      super();
   }

   public void addChord(CChord oChord)
   {
      add(oChord);
   }
   
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
