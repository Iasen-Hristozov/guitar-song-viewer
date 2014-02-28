package com.discworld.guitarsonglib;

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
 
   public int getLines()
   {
      if(sChordsLine.isEmpty() && sTextLine.isEmpty())
         return 0;
      else if ((!sChordsLine.isEmpty() && sTextLine.isEmpty()) || 
               (sChordsLine.isEmpty() && !sTextLine.isEmpty()))
         return 1;
      else
         return 2;
   }
   
   @Override
   public String toString()
   {
      return (sChordsLine.isEmpty() ? "" : sChordsLine + "\n") + sTextLine; 
   }
}
