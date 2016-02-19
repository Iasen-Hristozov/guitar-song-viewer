package com.discworld.guitarsonglib;

import java.util.ArrayList;

import com.discworld.guitarsonglib.CVerse;

public class CVerseSet extends ArrayList<CVerse>
{
    
   /**
    * 
    */
   private static final long serialVersionUID = 1405161366546673405L;

   @Override
   public String toString()
   {
      String sVerseSet = "";
      for(CVerse oVerse: this)
      {
         sVerseSet += (sVerseSet.isEmpty() ? "" : "\n\n") + oVerse.toString();
      }
      
      return sVerseSet;      
   }
}
