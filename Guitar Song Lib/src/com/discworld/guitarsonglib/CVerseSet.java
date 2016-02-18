package com.discworld.guitarsonglib;

import java.util.ArrayList;

import com.discworld.guitarsonglib.CVerse;

public class CVerseSet
{
   protected ArrayList<CVerse> alVerses = new ArrayList<CVerse>();
   
   public CVerseSet()
   {
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
         sVerseSet += (sVerseSet.isEmpty() ? "" : "\n\n") + oVerse.toString();
      }
      
      return sVerseSet;      
   }
}
