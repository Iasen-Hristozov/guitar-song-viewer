package com.discworld.guitarsonglib;

public class CChord
{

   public String sName;
   public int iPosition;
   
   public CChord()
   {
      sName = "";
      iPosition = 0;
   }
   
   public CChord(String sName)
   {
      this.sName = sName;
      this.iPosition = 0;
   }

   @Override
   public String toString()
   {
      return sName;
   }
}
