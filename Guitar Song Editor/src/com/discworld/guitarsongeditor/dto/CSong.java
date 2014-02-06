package com.discworld.guitarsongeditor.dto;

import java.util.ArrayList;

import javax.swing.plaf.SliderUI;

public class CSong
{
   public String sTitle;
   public String sAuthor;
   public int iEnuLanguage;
   public ArrayList<CChordsVerse> alChords;
//   public ArrayList<CTextVerse> alText;
   public CTextVersesSet oText;
   
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
}
