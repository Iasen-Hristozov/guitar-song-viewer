package com.discworld.guitarsonglib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlValue;

//@XmlRootElement(name = "chords-verse")
//public class CChordsVerse
//{
//   @XmlAttribute(name = "id", required = true)
//   public String sID;
////   @XmlElementWrapper(name = "chords-verse", required = true)
////   @XmlElementRef()
//   public ArrayList<CChordsLine> alChordsLines;
//   
//   public CChordsVerse()
//   {
//      sID = "";
//      alChordsLines = new ArrayList<CChordsLine>();
//   }
//   
//   public CChordsVerse(String sID, ArrayList<CChordsLine> alChordsLines)
//   {
//      this.sID = sID;
//      this.alChordsLines = alChordsLines;
//   }
//   
//   public void addChordLine(CChordsLine oChordsLine)
//   {
//      alChordsLines.add(oChordsLine);
//   }
//   
//   @Override
//   public String toString()
//   {
//      String sChordsSet = "";
//      for(CChordsLine oChordsLine: alChordsLines)
//      {
//         sChordsSet += (sChordsSet.equals("") ? "" : "\n") + oChordsLine.toString();
//      }
//      return sChordsSet;
//   }
//}
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
   public String sID;
//   @XmlElementWrapper(name = "chords-verse", required = true)
//   @XmlElementRef()
   public CChordsVerse()
   {
      super();
      sID = "";
//      alChordsLines = new ArrayList<CChordsLine>();
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
   
   @XmlElement(name = "chord-line")
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

