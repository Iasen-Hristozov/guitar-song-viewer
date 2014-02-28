package com.discworld.guitarsongviewer.dto;

import java.util.ArrayList;

import com.discworld.guitarsonglib.CChordsTextPairVerse;

public class CChordsTextPairVerseSet extends CVerseSet
{
   public ArrayList<CChordsTextPairVerse> alChordsTextPairVerses;
   
   public CChordsTextPairVerseSet()
   {
//      alVerses = new ArrayList<CChordsTextPairVerse>();
      alChordsTextPairVerses = new ArrayList<CChordsTextPairVerse>();
   }
   
   public void add(CChordsTextPairVerse oChordsTextPairVerse)
   {
      alChordsTextPairVerses.add(oChordsTextPairVerse);
   }

   @Override
   public CChordsTextPairVerse get(int iNdx)
   {
      return alChordsTextPairVerses.get(iNdx);
   }
   
   @Override
   public String toString()
   {
      String sChordsTextPairVerseSet = "";
      for(CChordsTextPairVerse oChordsTextPairVerse: alChordsTextPairVerses)
      {
         sChordsTextPairVerseSet += (sChordsTextPairVerseSet.equals("") ? "" : "\n\n") + oChordsTextPairVerse.toString();
      }
      return sChordsTextPairVerseSet;      
   }

}
