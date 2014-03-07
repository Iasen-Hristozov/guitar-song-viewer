package com.discworld.guitarsonglib;

import java.util.ArrayList;

public class CTextVersesSet extends CVerseSet
{
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */

//   public ArrayList<CTextVerse> alTextVerses;
   
   public CTextVersesSet()
   {
//      alTextVerses = new ArrayList<CTextVerse>();
      super();
   }
   
   public ArrayList<CVerse> getTextVersesSet()
   {
      return alVerses;
   }
   
   
//   public void add(CTextVerse oTextVerse)
//   {
////      alTextVerses.add(oTextVerse);
//      super.add(oTextVerse);
//   }
   
//   @Override
//   public String toString()
//   {
//      String sTextVersesSet = "";
//      for(CTextVerse oTextVerse: alTextVerses)
//      {
//         sTextVersesSet += (sTextVersesSet.isEmpty() ? "" : "\n") + oTextVerse.toString();
//      }
//      return sTextVersesSet;
//   }
}
