package com.discworld.guitarsonglib;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.discworld.englishhyphenator.CEnglishHyphenator;

@XmlRootElement(name = "song")
public class CSong
{
   @XmlAttribute(name = "title", required = true)
   public String sTitle = "";

   @XmlAttribute(name = "author", required = true)
   public String sAuthor = "";
   
   @XmlAttribute(name = "language", required = true)
   public String sLanguage;
   
   @XmlElementWrapper(name = "chords", required = true)
   @XmlElementRef()
   public ArrayList<CChordsVerse> alChords = new ArrayList<CChordsVerse>();

   @XmlElementWrapper(name = "text", required = true)
   @XmlElementRef()
   public ArrayList<CVerse> oText = new ArrayList<CVerse>();
   
   @XmlTransient
   public Hashtable<String, Integer> htChordsIdNdx = new Hashtable<String, Integer>();
   
   public final static String TAG_SONG = "song",
                              ATR_AUTHOR = "author",
                              ATR_TITLE = "title",
                              LANG_EN = "en",
                              LANG_BG = "bg",
                              LANG_RU = "ru",
                              LANG[] = {LANG_EN, LANG_BG, LANG_RU};
   
   public final static int ENU_LNG_UNDEFINED = 0,
                           ENU_LNG_EN = 1,
                           ENU_LNG_BG = 2,
                           ENU_LNG_RU = 3;
   
   public final static int ENU_HPN_UKN = 0,
                           ENU_HPN_LCL = 1,
                           ENU_HPN_LCL_ITEXT = 2,
                           ENU_HPN_INT_LRC_HPN = 3;   
   
   public CSong()
   {
   }
      
   public CSong(String xmlSong)
   {
      StringReader sr = new StringReader(xmlSong);
      try
      {
         JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class);
         Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
         CSong oSong = (CSong)jaxbUnmarshaller.unmarshal(sr);

         this.sAuthor = oSong.sAuthor;
         this.sTitle = oSong.sTitle;
         this.sLanguage = oSong.sLanguage;
         this.alChords = oSong.alChords;
         this.oText = oSong.oText;
         
         for(CChordsVerse oChordsVerse: this.alChords)
            htChordsIdNdx.put(oChordsVerse.sID, alChords.size()-1);
         
      } 
      catch(JAXBException e)
      {
         e.printStackTrace();
      }
   }
   
   public String toXml()
   {
      String xmlSong = "";
      try
      {
         JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class);
//       JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class, CChordsVerse.class, CChordsLine.class, CChord.class);

         Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

         jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

         StringWriter sw = new StringWriter();
       
         jaxbMarshaller.marshal(this, sw);
         xmlSong = sw.toString();     
      } 
      catch(JAXBException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return xmlSong;
   }

   public ArrayList<CChordsTextPairVerse> getChordsTextVerses()
   {
      String sChordsLine;
      
      CChordsTextPairVerse oChordsTextPairVerse;
      
      CChordsTextPair oChordsTextPair;
      
      ArrayList<CChordsTextPairVerse> alChordsTextVerses = new ArrayList<CChordsTextPairVerse>();
      
      CTextVerse oTextVerse;
      for(int i = 0; i < oText.size(); i++)
      {
         oTextVerse = (CTextVerse) oText.get(i);
            
         oChordsTextPairVerse = new CChordsTextPairVerse();
         
         Integer iCrdNdx = htChordsIdNdx.get(oTextVerse.sChordsVerseID);
         
         if(iCrdNdx != null)
         {
            CChordsVerse oChordsVerse = alChords.get(iCrdNdx);
            
            if(oTextVerse.size() == 0)
            {
//               for(CChordsLine oChordsLine : oChordsVerse.alChordsLines)
               for(CChordsLine oChordsLine : oChordsVerse)
               {
                  sChordsLine = getChordsLine(oChordsLine);

                  oChordsTextPair = new CChordsTextPair("", sChordsLine);
                  oChordsTextPairVerse.add(oChordsTextPair);
               }
            }
            else
            {
               CChordsLine oChordsLine;
               
               CTextLine oTextLine;
               
               for(int j = 0; j < oTextVerse.alTextLines.size(); j++)
               {
                  oTextLine = oTextVerse.alTextLines.get(j);
//                  oChordsLine = oChordsVerse.alChordsLines.get(j);
                  oChordsLine = oChordsVerse.get(j);
                  
                  sChordsLine = getChordsLineString(oTextLine.sTextLine, oChordsLine);
                  
                  oChordsTextPair = new CChordsTextPair(oTextLine.sTextLine, sChordsLine);
                  oChordsTextPairVerse.add(oChordsTextPair);
               }            
            }
         }
         else
         {
            for(CTextLine oTextLine: oTextVerse.alTextLines)
            {
               oChordsTextPair = new CChordsTextPair(oTextLine.sTextLine);
               oChordsTextPairVerse.add(oChordsTextPair);
            }
            
         }

         alChordsTextVerses.add(oChordsTextPairVerse);
      }
      
      return alChordsTextVerses;
   }
   
   private String getChordsLineString(String sTextLine, CChordsLine oChordsLine)
   {
      String sChordsLine;

//      if(iEnuLanguage == ENU_LNG_EN)
      if(sLanguage.equals(LANG_EN))
         sChordsLine = getChordsLineStringEn(sTextLine, oChordsLine);
      else
         sChordsLine = getChordsLineStringCyr(sTextLine, oChordsLine);
      
      return sChordsLine;
   }

   private String getChordsLineStringCyr(String sTextLine, CChordsLine oChordsLine)
   {
      int iSlbNdx = 1,
          iBgn = 0,
          iEnd = 0;
      
      String sChordsLine = "",
             sTmp;
           
      Matcher   mtcText;
           
      final Pattern ptrSylablesBG = Pattern.compile("[ÀÚÎÓÅÈÞßÜàúîóåèþÿ]"),
                    ptrSylablesRU = Pattern.compile("[ÀÚÎÓÅÈÞßÜÝÛàúîóåèþÿûý¸]");
           
//      switch(iEnuLanguage)
//      {
//         case ENU_LNG_BG:
//            mtcText = ptrSylablesBG.matcher(sTextLine);      
//         break;
//              
//         default:
//         case ENU_LNG_RU:
//            mtcText = ptrSylablesRU.matcher(sTextLine);      
//         break;
//      }
      
      switch(sLanguage)
      {
         case LANG_BG:
            mtcText = ptrSylablesBG.matcher(sTextLine);      
         break;
              
         default:
         case LANG_RU:
            mtcText = ptrSylablesRU.matcher(sTextLine);      
         break;
      }
      
           
//      mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);           
//      for(CChord oChord: oChordsLine.alChords)
      for(CChord oChord: oChordsLine)
      {
         if(oChord.iPosition >= 0)
         {
            while(mtcText.find(iEnd))
            {
               iEnd = mtcText.end();
                    
               if(iSlbNdx == oChord.iPosition)
               {
                  iBgn = mtcText.start();
                  sTmp = paddedLeftString((iBgn - sChordsLine.length() + oChord.sName.length()), oChord.sName);
//                  sTmp = String.format("%1$" +  (iCrdBgn - sChordsLine.length() + oChord.sName.length()) + "s", oChord.sName);
                  sChordsLine += sTmp;
                  iSlbNdx++;
                  break;
               }
               
               iSlbNdx++;
            }
         }
         else
         {
            sTmp = paddedLeftString(((sChordsLine.length() < sTextLine.length() ?  sTextLine.length() - sChordsLine.length() : 0) + oChord.sName.length() + 1), oChord.sName);
//            if(sChordsLine.length() < sTextLine.length())
//               sTmp = paddedLeftString((sTextLine.length() - sChordsLine.length() + oChord.sName.length() + 1), oChord.sName);
//            else
//               sTmp = paddedLeftString(oChord.sName.length() + 1, oChord.sName);
            sChordsLine += sTmp;
         }
      }
           
      return sChordsLine;
   }
   
   private String getChordsLineStringEn(String sTextLine, CChordsLine oChordsLine)
   {
      int      iSlbNdx = 1,
               iBgn = 0,
               iEnd = 0,
               i = 0,
               iPos = 0,
               iNxtSlb = 0;
           

      String   hyphenated_word,
               word,
               sSyllable,   
               tsSylables[],
               sChordsLine = "",
               sTmp;
      
      CEnglishHyphenator oHyphenator = new CEnglishHyphenator(CEnglishHyphenator.ENU_HPN_INT_LRC_HPN);
      
      ArrayList<String> alSyllables = new ArrayList<String>();
      
      Pattern ptrEngWord = Pattern.compile("\\w+");
      
      Matcher   mtcText = ptrEngWord.matcher(sTextLine);
      
      while(mtcText.find(iEnd))
      {
         iBgn = mtcText.start();
         if(iBgn != iEnd)
         {
            alSyllables.add(sTextLine.substring(iEnd, iBgn));
         }
         iEnd = mtcText.end();
         
         word = sTextLine.substring(iBgn, iEnd);
         
         hyphenated_word = oHyphenator.hyphenate(word);
         tsSylables = hyphenated_word.split("-");
         for(int j = 0; j < tsSylables.length; j++)
            alSyllables.add(tsSylables[j]);

         System.out.println(hyphenated_word); 
      }
      
      for(CChord oChord: oChordsLine)
//      for(CChord oChord: oChordsLine.alChords)
      {
         if(oChord.iPosition >= 0)
         {         
            for(i = iNxtSlb; i < alSyllables.size(); i++)
            {
               sSyllable = alSyllables.get(i);
               
               if(sSyllable.matches("[\\W]"))
               {
                  // It is not a syllable
                  iPos += sSyllable.length();
                  continue;
               }
               else
               {
                  // It is a syllable
                  if(iSlbNdx == oChord.iPosition)
                  {
                     sTmp = paddedLeftString(iPos - sChordsLine.length() + oChord.sName.length(), oChord.sName);
                     sChordsLine += sTmp;
                     iSlbNdx++;
                     iNxtSlb = i + 1;
                     iPos += sSyllable.length();
                     break;
                  }
                  iSlbNdx++;
                  iPos += sSyllable.length();
               }
               
            }
         }
         else
         {
            sTmp = paddedLeftString(((sChordsLine.length() < sTextLine.length() ?  sTextLine.length() - sChordsLine.length() : 0) + oChord.sName.length() + 1), oChord.sName);
            sChordsLine += sTmp;
         }
      }

      return sChordsLine;
   }

   private String paddedLeftString(int iPad, String s)
   {
      return String.format("%1$" +  iPad + "s", s);
   }

   private String getChordsLine(CChordsLine oChordsLine)
   {
      String sChordsLine = "";

//      for(CChord oChord: oChordsLine.alChords)
      for(CChord oChord: oChordsLine)
         sChordsLine += (sChordsLine.isEmpty() ? "" : " ") + oChord.sName;
      return sChordsLine;
   }

   public static void validate(String xml) throws SAXException
   {
      URL schemaFile = CSong.class.getResource("/XMLSchema.xsd");
      Source srcXml = new StreamSource(new StringReader(xml));
      
      SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      try 
      {
         Schema schema = schemaFactory.newSchema(schemaFile);
         Validator validator = schema.newValidator();

         validator.validate(srcXml);
//         System.out.println("xml is valid");
      } 
      catch (SAXException e) 
      {
//         System.out.println("xml is NOT valid");
//         System.out.println("Reason: " + e.getLocalizedMessage());
         throw e;
      } 
      catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
