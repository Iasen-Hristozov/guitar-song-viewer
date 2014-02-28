package com.discworld.guitarsonglib;

public class CTextLine
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
}
