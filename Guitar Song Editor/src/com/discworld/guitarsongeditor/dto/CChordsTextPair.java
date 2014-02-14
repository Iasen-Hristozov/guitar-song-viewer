package com.discworld.guitarsongeditor.dto;

public class CChordsTextPair
{
   public String sChordsLine;
   public String sTextLine;
   
   public CChordsTextPair()
   {
      sChordsLine = "";
      sTextLine = "";
   }
   
   public CChordsTextPair(String sTextLine, String sChordsLine)
   {
      this.sTextLine = sTextLine;
      this.sChordsLine = sChordsLine;
   }

   public CChordsTextPair(String sTextLine)
   {
      this.sTextLine = sTextLine;
      this.sChordsLine = "";
   }
   
   @Override
   public String toString()
   {
      return (sChordsLine.isEmpty() ? "" : sChordsLine + "\n") + sTextLine; 
   }
}
