package com.discworld.guitarsonglib;

public class CChordsTextPair
{
   public String sChordsLine = "";
   public String sTextLine = "";
   
   public CChordsTextPair()
   {
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

   public int size()
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
      return (sChordsLine.isEmpty() ? "" : sChordsLine) + (sTextLine.isEmpty() ? "" : (sChordsLine.isEmpty() ? "" : "\n") + sTextLine); 
   }
}
