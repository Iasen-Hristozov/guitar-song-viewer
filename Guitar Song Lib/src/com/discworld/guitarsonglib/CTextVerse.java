package com.discworld.guitarsonglib;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "text-verse")
@XmlType(name = "text-verse")
public class CTextVerse extends CVerse
{
   @XmlAttribute(name = "id-chords-verse", required = true)
   public String sChordsVerseID;
   @XmlElement(name = "text-line")
   public ArrayList<CTextLine> alTextLines;
   
   public CTextVerse()
   {
      sChordsVerseID = "";
      alTextLines = new ArrayList<CTextLine>();
   }
   
   public CTextVerse(String sChordsCoupletID,
                     ArrayList<CTextLine> alTextLines)
   {
      this.sChordsVerseID = sChordsCoupletID;
      this.alTextLines = alTextLines;
   }
   
   public void addTextLine(CTextLine oTextLine)
   {
      alTextLines.add(oTextLine);
   }
   
   @Override
   public String toString()
   {
      String sTextVerse = "";
      for(CTextLine oTextLine: alTextLines)
      {
         sTextVerse += (sTextVerse.isEmpty() ? "" : "\n") + oTextLine.toString();
      }
      return sTextVerse;
   }

   @Override
   public int size()
   {
      return alTextLines.size();
   }
}
