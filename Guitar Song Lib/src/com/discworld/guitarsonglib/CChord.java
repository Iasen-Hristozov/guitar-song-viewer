package com.discworld.guitarsonglib;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "chord")
public class CChord
{

   @XmlAttribute(name = "name", required = true)
   public String sName = "";
   @XmlAttribute(name = "pos", required = true)
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
