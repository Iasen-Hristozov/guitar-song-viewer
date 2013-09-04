package com.discworld.dto;

import java.util.ArrayList;

public class CChordsVerse
{
   public String sID;
   public ArrayList<CChordsLine> alChordsLines;
   
   public CChordsVerse()
   {
      sID = "";
      alChordsLines = new ArrayList<CChordsLine>();
   }
   
   public CChordsVerse(String sID, ArrayList<CChordsLine> alChordsLines)
   {
      this.sID = sID;
      this.alChordsLines = alChordsLines;
   }
   
   public void addChordLine(CChordsLine oChordsLine)
   {
      alChordsLines.add(oChordsLine);
   }
   
   @Override
   public String toString()
   {
      String sChordsSet = "";
      for(CChordsLine oChordsLine: alChordsLines)
      {
         sChordsSet += (sChordsSet.equals("") ? "" : "\n") + oChordsLine.toString();
      }
      return sChordsSet;
   }


}
