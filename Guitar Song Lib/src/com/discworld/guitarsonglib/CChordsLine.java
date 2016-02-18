package com.discworld.guitarsonglib;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

////@XmlAccessorType(XmlAccessType.FIELD)
////@XmlSeeAlso({CChord.class})
//@XmlRootElement(name = "chords-line")
//public class CChordsLine
//{
////   @XmlElement(name = "chordsss", required = true)
//   @XmlElementWrapper(name="chords-line")
//   @XmlElementRef()
//   public ArrayList<CChord> alChords;
//   
//   public CChordsLine()
//   {
//      alChords = new ArrayList<CChord>();
//   }
//   
//   public CChordsLine(ArrayList<CChord> alChordsLine)
//   {
//      this.alChords = alChordsLine;
//   }
//   
//   public void addChord(CChord oChord)
//   {
//      alChords.add(oChord);
//   }
//   
//   @Override
//   public String toString()
//   {
//      String sChordsLine = "";
//      for(CChord oChord: alChords)
//      {
//         sChordsLine += (sChordsLine.equals("") ? "" : ", ") + oChord.toString();
//      }
//      return sChordsLine;
//   }
   
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlSeeAlso({CChord.class})
//@XmlRootElement(name = "chords-line")
public class CChordsLine extends ArrayList<CChord>
{
   /**
    * 
    */
   private static final long serialVersionUID = -5073417906207483516L;
   // @XmlElement(name = "chordsss", required = true)
//   public ArrayList<CChord> alChords;

   public CChordsLine()
   {
      super();
   }

//   public CChordsLine(ArrayList<CChord> alChordsLine)
//   {
//      this = alChordsLine;
//   }

   public void addChord(CChord oChord)
   {
      add(oChord);
   }
   
   @XmlElement(name = "chord")
   public ArrayList<CChord> getChords() 
   {
     return this;
   }

   @Override
   public String toString()
   {
      String sChordsLine = "";
      for(CChord oChord : this)
      {
         sChordsLine += (sChordsLine.equals("") ? "" : ", ")
                  + oChord.toString();
      }
      return sChordsLine;
   }
}
