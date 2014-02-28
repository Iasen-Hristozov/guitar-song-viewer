package com.discworld.guitarsonglib;

import java.util.ArrayList;

public class CChordsTextPairVerse
{
   public ArrayList<CChordsTextPair> alChordsTextPairs;
   
   public CChordsTextPairVerse()
   {
      alChordsTextPairs = new ArrayList<CChordsTextPair>();
   }
   
   public void add(CChordsTextPair oChordsTextPair)
   {
      alChordsTextPairs.add(oChordsTextPair);
   }
   
   public int getLines()
   {
      int iLines = 0;
      
      for(CChordsTextPair oChordsTextPair: alChordsTextPairs)
         iLines += oChordsTextPair.getLines();
      
      return iLines;
   }
   
   @Override
   public String toString()
   {
      String s = "";
      for(CChordsTextPair oChordsTextPair: alChordsTextPairs)
         s += oChordsTextPair.toString() + "\n";
      s += "\n";
      return s;
   }
}
