package com.discworld.dto;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class CTextLine implements Parcelable
{
   public String sTextLine;
   
   public CTextLine()
   {
      sTextLine = "";
   }
   
   public CTextLine(String sTextLine)
   {
      this.sTextLine = sTextLine;
   }
   
   @Override
   public String toString()
   {
      return sTextLine;
   }

   @Override
   public int describeContents()
   {
      // TODO Auto-generated method stub
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flaks)
   {
      dest.writeString(sTextLine);
   }
   
   /**
    * It will be required during un-marshaling data stored in a Parcel 
    * @author prasanta
    */
   
//   public class CTextLineCreator implements Parcelable.Creator<CTextLine> 
   public final Parcelable.Creator<CTextLine> CREATOR = new Parcelable.Creator<CTextLine>()
   {
      public CTextLine createFromParcel(Parcel source) 
      {
            return new CTextLine(source);
      }
      public CTextLine[] newArray(int size) 
      {
            return new CTextLine[size];
      }
   };
   /** 
    * This will be used only by the MyCreator
    * @param source
    */
   public CTextLine(Parcel source)
   {
        /*
         * Reconstruct from the Parcel
         */
        sTextLine = source.readString();
   }   
}
