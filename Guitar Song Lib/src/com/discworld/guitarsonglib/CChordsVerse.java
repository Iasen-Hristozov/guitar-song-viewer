package com.discworld.guitarsonglib;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "chords-verse")
@XmlSeeAlso({CChordsLine.class})
public class CChordsVerse extends ArrayList<CChordsLine>
{
   /**
    * 
    */
   
   private static final long serialVersionUID = -6286896746218963760L;
   @XmlAttribute(name = "id", required = true)
   public String sID = "";

   public CChordsVerse()
   {
   }
   
   public CChordsVerse(String sID, ArrayList<CChordsLine> alChordsLines)
   {
      this.sID = sID;
      this.clear();
      this.addAll(alChordsLines);
   }
   
   public void addChordLine(CChordsLine oChordsLine)
   {
      add(oChordsLine);
   }
   
   @XmlElement(name = "chords-line")
   public ArrayList<CChordsLine> getChordsLines() 
   {
     return this;
   }
   
   @Override
   public String toString()
   {
      String sChordsSet = "";
      for(CChordsLine oChordsLine: this)
      {
         sChordsSet += (sChordsSet.equals("") ? "" : "\n") + oChordsLine.toString();
      }
      return sChordsSet;
   }
}

