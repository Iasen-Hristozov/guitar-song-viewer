package com.discworld.guitarsongviewer.dto;

import java.util.ArrayList;

public class CChordsLine
{
   public ArrayList<CChord> alChords;
   
   public CChordsLine()
   {
      alChords = new ArrayList<CChord>();
   }
   
   public CChordsLine(ArrayList<CChord> alChordsLine)
   {
      this.alChords = alChordsLine;
   }
   
   public void addChord(CChord oChord)
   {
      alChords.add(oChord);
   }
   
   @Override
   public String toString()
   {
      String sChordsLine = "";
      for(CChord oChord: alChords)
      {
         sChordsLine += (sChordsLine.equals("") ? "" : ", ") + oChord.toString();
      }
      return sChordsLine;
   }
   
   
}
