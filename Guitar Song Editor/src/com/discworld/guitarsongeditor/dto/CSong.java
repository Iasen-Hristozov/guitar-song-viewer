package com.discworld.guitarsongeditor.dto;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.SliderUI;

public class CSong
{
   public String sTitle;
   public String sAuthor;
   public int iEnuLanguage;
   public ArrayList<CChordsVerse> alChords;
//   public ArrayList<CTextVerse> alText;
   public CTextVersesSet oText;
   
   public Hashtable<String, CChordsVerse> htChords;
   public Hashtable<String, Integer> htChordsIdNdx;
   
   private final static String XML_ID = "<?xml version=\"1.0\" encoding=\"utf-8\"?>",
                               TAG_SONG = "song",
                               ATR_AUTHOR = "author",
                               ATR_TITLE = "title",
                               ATR_LANG = "language",
                               TAG_CHORDS = "chords",
                               TAG_CHORDS_VERSE = "chords-couplet",
                               ATR_ID = "id",
                               TAG_CHORDS_LINE = "chords-line",
                               TAG_CHORD = "chord",
                               ATR_NAME = "name",
                               ATR_POS = "pos",
                               TAG_TEXT = "text",
                               TAG_TEXT_VERSE = "text-verse",
                               TAG_TEXT_LINE = "text-line",
                               ATR_CHORDS_VERSE_ID = "id-chords-couplet",
                               TAG_OPEN = "<",
                               TAG_END = ">",
                               TAG_CLOSE = "</",
                               LANG_EN = "en",
                               LANG_BG = "bg",
                               LANG_RU = "ru",
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
   
   public CSong()
   {
      sTitle = "";
      sAuthor = "";
      iEnuLanguage = 0;
      alChords = new ArrayList<CChordsVerse>();
//      alText = new ArrayList<CTextVerse>();
      oText = new CTextVersesSet();
      htChordsIdNdx = new Hashtable<String, Integer>();
   }
   
   public String generateXml()
   {
      String sSongXml = XML_ID,
             sLng;
      
      switch(iEnuLanguage)
      {
         case ENU_LNG_BG:
            sLng = LANG_BG;
         break;
         
         case ENU_LNG_RU:
            sLng = LANG_RU;
         break;
         
         case ENU_LNG_EN:
         default:
            sLng = LANG_EN;
         break;
      }
      
      sSongXml += "\n" + TAG_OPEN + TAG_SONG + " " + (!sTitle.isEmpty() ? ATR_TITLE + " = \"" + sTitle + "\" " : " ") + (!sAuthor.isEmpty() ? ATR_AUTHOR + " = \"" + sAuthor + "\" " : " ") + ATR_LANG + " = \"" + sLng + "\""+ TAG_END + NEW_LINE;
      if(!alChords.isEmpty())
      {
         sSongXml += CRD_OPEN;
         for(CChordsVerse oChordsVerse : alChords)
         {
            sSongXml += String.format(CRD_VERSE, oChordsVerse.sID);
            for(CChordsLine oChordsLine: oChordsVerse.alChordsLines)
            {
//             sSongXml += String.format(CRD_LINE, oChordsLine.toString().replace(", ", " "));
               sSongXml += CRD_LINE;
               for(CChord oChord: oChordsLine.alChords)
                  sSongXml += String.format(CRD_CHORD, oChord.sName, oChord.iPosition);
               
               sSongXml += CRD_LINE_CLOSE;
            }
            sSongXml += CRD_VERSE_CLOSE;
         }
         sSongXml += CRD_CLOSE;
      }
      
      if(!oText.alTextVerses.isEmpty())
      {
         sSongXml += TXT_OPEN;
         for(CTextVerse oTextVerse: oText.alTextVerses)
         {
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

   public ArrayList<CChordsTextPairVerse> getChordsTextVerses()
   {
      String sChordsLine;
      
      CTextLine oTextLine;
      
      CChordsLine oChordsLine;
      
      CChordsTextPairVerse oChordsTextPairVerse;
      
      CChordsTextPair oChordsTextPair;
      
      ArrayList<CChordsTextPairVerse> alChordsTextVerses = new ArrayList<CChordsTextPairVerse>();
      
      
      for(CTextVerse oTextVerse: oText.alTextVerses)
      {
         oChordsTextPairVerse = new CChordsTextPairVerse();
         
         Integer iChrdNdx = htChordsIdNdx.get(oTextVerse.sChordsVerseID);
         
         if(iChrdNdx != null)
         {
            CChordsVerse oChordsVerse = alChords.get(iChrdNdx);
            
            for(int i = 0; i < oTextVerse.alTextLines.size(); i++)
            {
               oTextLine = oTextVerse.alTextLines.get(i);
               oChordsLine = oChordsVerse.alChordsLines.get(i);
               
               sChordsLine = getChordsLineString(oTextLine.sTextLine, oChordsLine);
               
               oChordsTextPair = new CChordsTextPair(oTextLine.sTextLine, sChordsLine);
               oChordsTextPairVerse.add(oChordsTextPair);
            }            
         }
         else
         {
            for(CTextLine oTextLine2: oTextVerse.alTextLines)
            {
               oChordsTextPair = new CChordsTextPair(oTextLine2.sTextLine);
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
      if(iEnuLanguage == ENU_LNG_EN)
         sChordsLine = getChordsLineStringEn(sTextLine, oChordsLine);
      else
         sChordsLine = getChordsLineStringCyr(sTextLine, oChordsLine);
      return sChordsLine;
   }

   private String getChordsLineStringCyr(String sTextLine, CChordsLine oChordsLine)
   {
      int iSlbNdx = 0,
          iCrdBgn = 0,
          iCrdEnd = 0;
      
      String sChordsLine = "";
           
      Matcher   mtcText;
           
      final Pattern ptrSylablesBG = Pattern.compile("[ÀÚÎÓÅÈÞßÜàúîóåèþÿ]"),
                    ptrSylablesRU = Pattern.compile("[ÀÚÎÓÅÈÞßÜÝÛàúîóåèþÿûý¸]");
           
           
//           CChord oChord;
           
      switch(iEnuLanguage)
      {
         case ENU_LNG_BG:
            mtcText = ptrSylablesBG.matcher(sTextLine);      
         break;
              
         default:
         case ENU_LNG_RU:
            mtcText = ptrSylablesRU.matcher(sTextLine);      
         break;
      }
           
      String sTmp;
//      mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);           
      for(CChord oChord: oChordsLine.alChords)
      {
         if(oChord.iPosition >= 0)
         {
            while(mtcText.find(iCrdEnd))
            {
               iCrdEnd = mtcText.end();
                    
               if(iSlbNdx == oChord.iPosition)
               {
                  iCrdBgn = mtcText.start();
                  sTmp = String.format("%1$" +  (iCrdBgn - sChordsLine.length() + oChord.sName.length()) + "s", oChord.sName);
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
   
   private String paddedLeftString(int iPad, String s)
   {
      return String.format("%1$" +  iPad + "s", s);
   }

   private String getChordsLineStringEn(String sTextLine, CChordsLine oChordsLine)
   {
      // TODO Auto-generated method stub
      return null;
   }
}
