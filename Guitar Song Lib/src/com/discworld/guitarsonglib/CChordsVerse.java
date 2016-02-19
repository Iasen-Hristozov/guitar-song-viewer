package com.discworld.guitarsonglib;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "chords-verse")
public class CChordsVerse extends ArrayList<CChordsLine>
{
   /**
    * 
    */
   
   private static final long serialVersionUID = -6286896746218963760L;
   
   @Attribute(name = "id", required = true)
   public String sID = "";

   @ElementList(name ="cv", inline=true)
   ArrayList<CChordsLine> oChordsVerse = this;
   
   public CChordsVerse()
   {
   }
   
   public CChordsVerse(String sID, ArrayList<CChordsLine> alChordsLines)
   {
      this.sID = sID;
      this.clear();
      this.addAll(alChordsLines);
   }
   
   public void addChordLine(CChordsLine oChordsLine)
   {
      add(oChordsLine);
   }
   
   public ArrayList<CChordsLine> getChordsLines() 
   {
      return this;
   }
   
   @Override
   public String toString()
   {
      String sChordsSet = "";
      for(CChordsLine oChordsLine: this)
      {
         sChordsSet += (sChordsSet.equals("") ? "" : "\n") + oChordsLine.toString();
      }
      return sChordsSet;
   }
}

