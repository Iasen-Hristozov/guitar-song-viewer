package com.discworld.dto;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class CTextVersesSet implements Parcelable
{
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */

   public ArrayList<CTextVerse> alTextVerses;
   
   public CTextVersesSet()
   {
      alTextVerses = new ArrayList<CTextVerse>();
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
   

   @Override
   public int describeContents()
   {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags)
   {
      dest.writeArray(alTextVerses.toArray());
   }
   
   public class CTextVersesSetCreator implements Parcelable.Creator<CTextVersesSet> 
   {
      public CTextVersesSet createFromParcel(Parcel source) 
      {
            return new CTextVersesSet(source);
      }
      public CTextVersesSet[] newArray(int size) 
      {
            return new CTextVersesSet[size];
      }
   }
   public final Parcelable.Creator<CTextVersesSet> CREATOR = new Parcelable.Creator<CTextVersesSet>()
   {
      public CTextVersesSet createFromParcel(Parcel source) 
      {
            return new CTextVersesSet(source);
      }
      public CTextVersesSet[] newArray(int size) 
      {
            return new CTextVersesSet[size];
      }
   };
   
   /** 
    * This will be used only by the MyCreator
    * @param source
    */
   public CTextVersesSet(Parcel source)
   {
        /*
         * Reconstruct from the Parcel
         */
      alTextVerses = source.readArrayList(CTextVerse.class.getClassLoader());
   }      
}
