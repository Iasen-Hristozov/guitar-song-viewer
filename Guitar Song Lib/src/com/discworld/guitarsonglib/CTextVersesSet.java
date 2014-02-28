package com.discworld.guitarsonglib;

import java.util.ArrayList;

public class CTextVersesSet
{
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */

   public ArrayList<CTextVerse> alTextVerses;
   
   public CTextVersesSet()
   {
      alTextVerses = new ArrayList<CTextVerse>();
   }
   
   public void add(CTextVerse oTextVerse)
   {
      alTextVerses.add(oTextVerse);
   }
   
   @Override
   public String toString()
   {
      String sTextVersesSet = "";
      for(CTextVerse oTextVerse: alTextVerses)
      {
         sTextVersesSet += (sTextVersesSet.equals("") ? "" : "\n\n") + oTextVerse.toString();
      }
      return sTextVersesSet;
   }
}
