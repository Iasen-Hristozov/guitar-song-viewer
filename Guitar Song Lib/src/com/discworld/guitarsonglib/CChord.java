package com.discworld.guitarsonglib;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "chord")
public class CChord
{

   @Attribute(name = "name", required = true)
   public String sName = "";

   @Attribute(name = "pos", required = true)
   public int iPosition = 0;
   
   public CChord()
   {
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
