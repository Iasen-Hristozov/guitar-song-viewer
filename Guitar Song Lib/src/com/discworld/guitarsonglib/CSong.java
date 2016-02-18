package com.discworld.guitarsonglib;

import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.discworld.englishhyphenator.CEnglishHyphenator;

@XmlRootElement(name = "song")
public class CSong
{
   @XmlAttribute(name = "title", required = true)
   public String sTitle = "";
   @XmlAttribute(name = "author", required = true)
   public String sAuthor = "";
//   @XmlTransient
//   public int iEnuLanguage;
   @XmlAttribute(name = "language", required = true)
   public String sLanguage;
   @XmlElementWrapper(name = "chords", required = true)
   @XmlElementRef()
   public ArrayList<CChordsVerse> alChords = new ArrayList<CChordsVerse>();
//   public ArrayList<CTextVerse> alText;
   @XmlElementWrapper(name = "text", required = true)
   @XmlElementRef()
//   public CTextVersesSet oText;
   public ArrayList<CVerse> oText = new ArrayList<CVerse>();
   
//   public Hashtable<String, CChordsVerse> htChords;
   @XmlTransient
   public Hashtable<String, Integer> htChordsIdNdx = new Hashtable<String, Integer>();
   
   public final static String TAG_SONG = "song",
                              ATR_AUTHOR = "author",
                              ATR_TITLE = "title",
                              LANG_EN = "en",
                              LANG_BG = "bg",
                              LANG_RU = "ru",
                              LANG[] = {LANG_EN, LANG_BG, LANG_RU};
   
   private final static String XML_ID = "<?xml version=\"1.0\" encoding=\"utf-8\"?>",
                               ATR_LANG = "language",
                               TAG_CHORDS = "chords",
                               TAG_CHORDS_VERSE = "chords-verse",
                               ATR_ID = "id",
                               TAG_CHORDS_LINE = "chords-line",
                               TAG_CHORD = "chord",
                               ATR_NAME = "name",
                               ATR_POS = "pos",
                               TAG_TEXT = "text",
                               TAG_TEXT_VERSE = "text-verse",
                               TAG_TEXT_LINE = "text-line",
                               ATR_CHORDS_VERSE_ID = "id-chords-verse",
                               TAG_OPEN = "<",
                               TAG_END = ">",
                               TAG_CLOSE = "</",
                               PAD_CTG = "   ",
                               PAD_VERSE = "      ",
                               PAD_LINE = "         ",
                               PAD_CHORD = "            ",
                               NEW_LINE = "\n",
                               CRD_VERSE = PAD_VERSE + TAG_OPEN + TAG_CHORDS_VERSE  + " " + ATR_ID + " = \"%s\"" + TAG_END + NEW_LINE,
//                               CRD_LINE = PAD_LINE + TAG_OPEN + TAG_CHORDS_LINE + TAG_END + "%s" + TAG_CLOSE + TAG_CHORDS_LINE + TAG_END + NEW_LINE,
                               CRD_LINE = PAD_LINE + TAG_OPEN + TAG_CHORDS_LINE + TAG_END + NEW_LINE,
                               CRD_LINE_CLOSE = PAD_LINE + TAG_CLOSE + TAG_CHORDS_LINE + TAG_END + NEW_LINE,
                               CRD_CHORD = PAD_CHORD + TAG_OPEN + TAG_CHORD + " " +  ATR_NAME + " = \"%s\" " + ATR_POS + " = \"%s\" /" + TAG_END + NEW_LINE,
                               CRD_VERSE_CLOSE = PAD_VERSE + TAG_CLOSE + TAG_CHORDS_VERSE  + TAG_END + NEW_LINE + NEW_LINE,
                               CRD_OPEN = PAD_CTG + TAG_OPEN + TAG_CHORDS + TAG_END + NEW_LINE,
                               CRD_CLOSE = PAD_CTG + TAG_CLOSE + TAG_CHORDS + TAG_END + NEW_LINE + NEW_LINE,
                               TXT_OPEN = PAD_CTG + TAG_OPEN + TAG_TEXT+ TAG_END + NEW_LINE,
                               TXT_CLOSE = PAD_CTG + TAG_CLOSE + TAG_TEXT + TAG_END + NEW_LINE,
                               TXT_VERSE = PAD_VERSE + TAG_OPEN + TAG_TEXT_VERSE + " " + ATR_CHORDS_VERSE_ID + " = \"%s\""+ TAG_END + NEW_LINE,
                               TXT_VERSE_CLOSE = PAD_VERSE + TAG_CLOSE + TAG_TEXT_VERSE  + TAG_END + NEW_LINE + NEW_LINE,
                               TXT_LINE = PAD_LINE + TAG_OPEN + TAG_TEXT_LINE + TAG_END + "%s" + TAG_CLOSE + TAG_TEXT_LINE + TAG_END + NEW_LINE,
                               SONG_CLOSE = TAG_CLOSE + TAG_SONG + TAG_END;
/*                               
                               CRD_VERSE = "      <" + TAG_CHORDS_VERSE  + " " + ATR_ID + " = \"%s\">\n",
                               CRD_LINE = "         <" + TAG_CHORDS_LINE + ">\n",
                               CRD_LINE_CLOSE = "         </" + TAG_CHORDS_LINE + ">\n",
                               CRD_CHORD = "            <" + TAG_CHORD + " " +  ATR_NAME + " = \"%s\" " + ATR_POS + " = \"%s\" />\n",
                               CRD_VERSE_CLOSE = "      </" + TAG_CHORDS_VERSE  + ">\n",
                               CRD_OPEN = "            <" + TAG_CHORDS + ">\n",
                               CRD_CLOSE = "            </" + TAG_CHORDS + ">\n\n",
                               TXT_OPEN = "   <" + TAG_TEXT +  ">\n",
                               TXT_CLOSE = "   </" + TAG_TEXT + ">\n",
                               TXT_VERSE = "      <" + TAG_TEXT_VERSE + " " + ATR_CHORDS_VERSE_ID + " = \"%s\">\n",
                               TXT_VERSE_CLOSE = "      </" + TAG_TEXT_VERSE  + ">\n\n",
                               TXT_LINE = "         <" + TAG_TEXT_LINE + ">%s</" + TAG_TEXT_LINE + ">\n",
                               SONG_CLOSE = "<" + TAG_SONG + ">";
 */
   
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
//      init();
   }
   
   public CSong(String xmlSong)
      {
         Element  eChords,
                  eText;
         
         NodeList nlChordsVerses,
                  nlChordsLines,
                  nlChords,
                  nlTextVerses,
                  nlTextLines;
         
         Node  ndChordsVerse,
               ndChordsLine,
               ndChord,
               ndTextVerse,
               ndTextLine;
         
         CChordsVerse oChordsVerse;
         
         CChordsLine oChordsLine;
         
         CChord oChord;
         
         CTextVerse oTextVerse;
         
         CTextLine oTextLine;
         
//         init();
         
         //Get the DOM Builder Factory
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
   
         try
         {
            //Get the DOM Builder
            //Load and Parse the XML document
            //document contains the complete XML as a Tree.
            
            DocumentBuilder builder = factory.newDocumentBuilder();
   //         InputSource is = new InputSource(xmlSong);
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlSong));
            
            Document document = builder.parse(is);
            //Iterating through the nodes and extracting the data.
            Element eRoot = document.getDocumentElement();
   //         Node root = document.getDocumentElement().getFirstChild();
            sTitle = eRoot.getAttributes().getNamedItem(ATR_TITLE).getNodeValue();
            sAuthor = eRoot.getAttributes().getNamedItem(ATR_AUTHOR).getNodeValue();
            // For JRE 1.7
   //         switch(eRoot.getAttributes().getNamedItem(ATR_LANG).getNodeValue())
   //         {
   //            case LANG_BG :
   //               iEnuLanguage = ENU_LNG_BG;
   //            break;
   //            
   //            case LANG_RU:
   //               iEnuLanguage = ENU_LNG_RU;
   //            break;
   //            
   //            case LANG_EN:
   //            default:
   //               iEnuLanguage = ENU_LNG_EN;
   //            break;            
   //         }
            
            sLanguage = eRoot.getAttributes().getNamedItem(ATR_LANG).getNodeValue();
   //         // For JRE 1.6
   //         if(sLanguage == LANG_BG)
   //            iEnuLanguage = ENU_LNG_BG;
   //         else if(sLanguage == LANG_RU)
   //            iEnuLanguage = ENU_LNG_RU;
   //         else if(sLanguage == LANG_EN)
   //            iEnuLanguage = ENU_LNG_EN;
            
   //         NodeList nl = eRoot.getChildNodes();
   //       Node ndChords = nlChords.item(0); 
   
            eChords = (Element)eRoot.getElementsByTagName(TAG_CHORDS).item(0);
            
            nlChordsVerses = eChords.getElementsByTagName(TAG_CHORDS_VERSE);
            for(int i = 0; i < nlChordsVerses.getLength(); i++)
            {
               ndChordsVerse =  nlChordsVerses.item(i);
               if(ndChordsVerse instanceof Element)
               {
                  oChordsVerse = new CChordsVerse();
                  oChordsVerse.sID = ndChordsVerse.getAttributes().getNamedItem(ATR_ID).getNodeValue();
                  nlChordsLines = ((Element) ndChordsVerse).getElementsByTagName(TAG_CHORDS_LINE);
                  for(int j = 0; j < nlChordsLines.getLength(); j++)
                  {
                     ndChordsLine = nlChordsLines.item(j);
                     if(ndChordsLine instanceof Element)
                     {
                        oChordsLine = new CChordsLine();
                        
                        nlChords = ((Element) ndChordsLine).getElementsByTagName(TAG_CHORD);
                        for(int k = 0; k < nlChords.getLength(); k++)
                        {
                           ndChord = nlChords.item(k);
                           if(ndChord instanceof Element)
                           {
                              oChord = new CChord(); 
                              oChord.sName = ((Element) ndChord).getAttribute(ATR_NAME);
                              oChord.iPosition = Integer.valueOf(((Element) ndChord).getAttribute(ATR_POS));
   //                           oChordsLine.alChords.add(oChord);
                              oChordsLine.add(oChord);
                           }
                        }
   //                     oChordsVerse.alChordsLines.add(oChordsLine);
                        oChordsVerse.add(oChordsLine);
                     }
                  }
                  
                  alChords.add(oChordsVerse);
                  htChordsIdNdx.put(oChordsVerse.sID, alChords.size()-1);               
               }
            }
            
            eText = (Element) eRoot.getElementsByTagName(TAG_TEXT).item(0);
            nlTextVerses = eText.getElementsByTagName(TAG_TEXT_VERSE);
            for(int i = 0; i < nlTextVerses.getLength(); i++)
            {
               ndTextVerse = nlTextVerses.item(i);
               if(ndTextVerse instanceof Element)
               {
                  oTextVerse = new CTextVerse();
                  oTextVerse.sChordsVerseID = ((Element) ndTextVerse).getAttribute(ATR_CHORDS_VERSE_ID);
                  nlTextLines = ((Element) ndTextVerse).getElementsByTagName(TAG_TEXT_LINE);
                  for(int j = 0; j < nlTextLines.getLength(); j++)
                  {
                     ndTextLine = nlTextLines.item(j);
                     if(ndTextLine instanceof Element)
                     {
                        oTextLine = new CTextLine(ndTextLine.getTextContent());
                        oTextVerse.addTextLine(oTextLine);
                     }
                  }
   //               oText.alTextVerses.add(oTextVerse);
                  oText.add(oTextVerse);
               }
            }
   
         } 
         catch(ParserConfigurationException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } 
         catch(SAXException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } 
         catch(IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } 
      }

   //   public void setLanguage(int iEunLanguage)
   //   {
   //      this.iEnuLanguage = iEunLanguage;
   //      
   //      switch(iEnuLanguage)
   //      {
   //         case ENU_LNG_BG:
   //            sLanguage = LANG_BG;
   //         break;
   //         
   //         case ENU_LNG_RU:
   //            sLanguage = LANG_RU;
   //         break;
   //         
   //         case ENU_LNG_EN:
   //         default:
   //            sLanguage = LANG_EN;
   //         break;
   //      }
   //   }
      
      public CSong(String xmlSong, int a)
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
      //         if(sLanguage == LANG_BG)
      //            this.iEnuLanguage = ENU_LNG_BG;
      //         else if(sLanguage == LANG_RU)
      //            this.iEnuLanguage = ENU_LNG_RU;
      //         else if(sLanguage == LANG_EN)
      //            this.iEnuLanguage = ENU_LNG_EN;         
      //         this.iEnuLanguage = oSong.iEnuLanguage;
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

   private void init()
   {
      sTitle = "";
      sAuthor = "";
//      iEnuLanguage = 0;
      alChords = new ArrayList<CChordsVerse>();
//      alText = new ArrayList<CTextVerse>();
//      oText = new CTextVersesSet();
      oText = new ArrayList<CVerse>();
      htChordsIdNdx = new Hashtable<String, Integer>();
//      if(sLanguage == LANG_BG)
//         iEnuLanguage = ENU_LNG_BG;
//      else if(sLanguage == LANG_RU)
//         iEnuLanguage = ENU_LNG_RU;
//      else if(sLanguage == LANG_EN)
//         iEnuLanguage = ENU_LNG_EN;
   }
   
   public String toXml()
   {
      
      String sSongXml = XML_ID;
      
//      switch(iEnuLanguage)
//      {
//         case ENU_LNG_BG:
//            sLanguage = LANG_BG;
//         break;
//         
//         case ENU_LNG_RU:
//            sLanguage = LANG_RU;
//         break;
//         
//         case ENU_LNG_EN:
//         default:
//            sLanguage = LANG_EN;
//         break;
//      }
      
      sSongXml += "\n" + TAG_OPEN + TAG_SONG + " " + (!sTitle.isEmpty() ? ATR_TITLE + " = \"" + sTitle + "\" " : " ") + (!sAuthor.isEmpty() ? ATR_AUTHOR + " = \"" + sAuthor + "\" " : " ") + ATR_LANG + " = \"" + sLanguage + "\""+ TAG_END + NEW_LINE;
      if(!alChords.isEmpty())
      {
         sSongXml += CRD_OPEN;
         for(CChordsVerse oChordsVerse : alChords)
         {
            sSongXml += String.format(CRD_VERSE, oChordsVerse.sID);
//            for(CChordsLine oChordsLine: oChordsVerse.alChordsLines)
            for(CChordsLine oChordsLine: oChordsVerse)
            {
//             sSongXml += String.format(CRD_LINE, oChordsLine.toString().replace(", ", " "));
               sSongXml += CRD_LINE;
//               for(CChord oChord: oChordsLine.alChords)
               for(CChord oChord: oChordsLine)
                  sSongXml += String.format(CRD_CHORD, oChord.sName, oChord.iPosition);
               
               sSongXml += CRD_LINE_CLOSE;
            }
            sSongXml += CRD_VERSE_CLOSE;
         }
         sSongXml += CRD_CLOSE;
      }
      
//      if(!oText.alTextVerses.isEmpty())
      if(oText.size() != 0)
      {
         sSongXml += TXT_OPEN;
         CTextVerse oTextVerse;
//         for(CTextVerse oTextVerse: oText.alTextVerses)
         for(int i = 0; i < oText.size(); i++)
         {
            oTextVerse = (CTextVerse) oText.get(i);
            sSongXml += String.format(TXT_VERSE, oTextVerse.sChordsVerseID);
            for(CTextLine oTextLine: oTextVerse.alTextLines)
            {
               sSongXml += String.format(TXT_LINE, oTextLine.toString());
            }
            sSongXml += TXT_VERSE_CLOSE;
         }
         sSongXml += TXT_CLOSE;
      }
      
      sSongXml += SONG_CLOSE;
      
      return sSongXml;
   }

   
   
//   public void setLanguage(int iEunLanguage)
//   {
//      this.iEnuLanguage = iEunLanguage;
//      
//      switch(iEnuLanguage)
//      {
//         case ENU_LNG_BG:
//            sLanguage = LANG_BG;
//         break;
//         
//         case ENU_LNG_RU:
//            sLanguage = LANG_RU;
//         break;
//         
//         case ENU_LNG_EN:
//         default:
//            sLanguage = LANG_EN;
//         break;
//      }
//   }
   
   public String toXml2()
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
//      for(CTextVerse oTextVerse: oText.alTextVerses)
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
         
         
//         for(CChordsVerse oChordsVerse: alChords)
//         {
//            
//            if(oChordsVerse.sID.equals(oTextVerse.sChordsVerseID))
//            {
////               oChordsTextPairVerse = new CChordsTextPairVerse();
//               
//               for(int i = 0; i < oTextVerse.alTextLines.size(); i++)
//               {
//                  oTextLine = oTextVerse.alTextLines.get(i);
//                  oChordsLine = oChordsVerse.alChordsLines.get(i);
//                  
//                  sChordsLine = getChordsLineString(oTextLine.sTextLine, oChordsLine);
//                  
//                  oChordsTextPair = new CChordsTextPair(oTextLine.sTextLine, sChordsLine);
//                  oChordsTextPairVerse.add(oChordsTextPair);
//               }
//               
////               alChordsTextVerses.add(oChordsTextPairVerse);
//            }
//         }
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
      // load a WXS schema, represented by a Schema instance
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
