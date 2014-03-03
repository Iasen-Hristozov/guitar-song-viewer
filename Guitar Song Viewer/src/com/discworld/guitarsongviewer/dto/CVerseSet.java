package com.discworld.guitarsongviewer.dto;

import java.util.ArrayList;

import com.discworld.guitarsonglib.CVerse;

public class CVerseSet
{
   private ArrayList<CVerse> alVerses;
   
   public CVerseSet()
   {
      alVerses = new ArrayList<CVerse>();
   }
   
   public void add(CVerse oVerse)
   {
      alVerses.add(oVerse);
   }
   
   public CVerse get(int iNdx)
   {
      return alVerses.get(iNdx);
   }
   
   public ArrayList<CVerse> getAll()
   {
      return alVerses;
   }
   
   public int size()
   {
      return alVerses.size();
   }
    
   @Override
   public String toString()
   {
      String sVerseSet = "";
      for(CVerse oVerse: alVerses)
      {
//         sVerseSet += (sVerseSet.equals("") ? "" : "\n") + oVerse.toString();
         sVerseSet += oVerse.toString();
      }
      return sVerseSet;      
   }
}
