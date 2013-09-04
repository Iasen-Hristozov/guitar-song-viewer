package com.discworld.dto;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class CTextVerse implements Parcelable
{
   public boolean bIsChorus;
   public String sChordsCoupletID;
   public ArrayList<CTextLine> alTextLines;
   
   public CTextVerse()
   {
      bIsChorus = false;
      sChordsCoupletID = "";
      alTextLines = new ArrayList<CTextLine>();
   }
   
   public CTextVerse(String sChordsCoupletID,
                     ArrayList<CTextLine> alTextLines,
                     boolean bIsChorus)
   {
      this.bIsChorus = bIsChorus;
      this.sChordsCoupletID = sChordsCoupletID;
      this.alTextLines = alTextLines;
   }
   
   public void addTextLine(CTextLine oTextLine)
   {
      alTextLines.add(oTextLine);
   }
   
   @Override
   public String toString()
   {
      String sTextVerse = "";
      for(CTextLine oTextLine: alTextLines)
      {
         sTextVerse += (sTextVerse.equals("") ? "" : "\n") + oTextLine.toString();
      }
      return sTextVerse;
   }

   @Override
   public int describeContents()
   {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags)
   {
      dest.writeInt(bIsChorus ? 1 : 0);
      dest.writeString(sChordsCoupletID);
      dest.writeArray(alTextLines.toArray());
   }
   
   public class CTextVerseCreator implements Parcelable.Creator<CTextVerse> 
   {
      public CTextVerse createFromParcel(Parcel source) 
      {
            return new CTextVerse(source);
      }
      public CTextVerse[] newArray(int size) 
      {
            return new CTextVerse[size];
      }
   }
   public final Parcelable.Creator<CTextVerse> CREATOR = new Parcelable.Creator<CTextVerse>()
   {
      public CTextVerse createFromParcel(Parcel source) 
      {
            return new CTextVerse(source);
      }
      public CTextVerse[] newArray(int size) 
      {
            return new CTextVerse[size];
      }
   };
   
   /** 
    * This will be used only by the MyCreator
    * @param source
    */
   public CTextVerse(Parcel source)
   {
        /*
         * Reconstruct from the Parcel
         */
      bIsChorus = source.readInt() == 1? true : false;
      sChordsCoupletID = source.readString();
//      alTextLines = Arrays.asList(source.readParcelableArray(CTextLine.class.getClassLoader()));
      alTextLines = source.readArrayList(CTextLine.class.getClassLoader());
//      alTextLines = new ArrayList<CTextLine>(Arrays.asList(source.readArray(CTextLine.class.getClassLoader())));
   }      
}
