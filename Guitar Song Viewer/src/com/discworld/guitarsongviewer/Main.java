package com.discworld.guitarsongviewer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.discworld.dto.CChord;
import com.discworld.dto.CChordsCouplet;
import com.discworld.dto.CChordsLine;
import com.discworld.dto.CSong;
import com.discworld.dto.CTextLine;
import com.discworld.dto.CTextVerse;
import com.discworld.dto.CTextVersesSet;
import com.discworld.dto.IDataExchange;

public class Main extends FragmentActivity implements IDataExchange
{
   public final static int ENU_LNG_UNKNOWN = 0,
                           ENU_LNG_ENGLISH = 1,
                           ENU_LNG_BULGARIAN = 2,
                           ENU_LNG_RUSSIAN = 3;
   public final static String SONGS_FOLDER = "Songs";
   
   public final static String SONGS_SUFFIX = ".xml";
   
   public final static String TAG_SONG = "song",
                              ATR_TITLE = "title",
                              ATR_AUTHOR = "author",
                              ATR_LANGUAGE = "language",
                              TAG_CHORDS = "chords",
                              TAG_CHORDS_COUPLET = "chords-couplet",
                              TAG_CHORDS_LINE = "chords-line",
                              TAG_CHORD = "chord",
                              TAG_TEXT = "text",
                              TAG_TEXT_VERSE = "text-verse",
                              TAG_TEXT_LINE = "text-line",
                              ATR_ID = "id",
                              ATR_ID_CHORD_COUPLET = "id-chords-couplet",
                              ATR_ID_IS_CHORUS = "is-chorus";
                              
   public final static String LNG_ENGLISH = "english",
                              LNG_BULGARIAN = "български",
                              LNG_RUSSIAN = "руский";
   
   private final static int ENU_TEXT_TYPE_UNKNOWN = 0,
                            ENU_TEXT_TYPE_CHORDS = 1,
                            ENU_TEXT_TYPE_TEXT = 2;

   private final static int ENU_SHOW_CHORDS_NONE = 1,
                            ENU_SHOW_CHORDS_ALL = 0,
                            ENU_SHOW_CHORDS_RELATED = 2,
                            ENU_SHOW_CHORDS_ABOVE = 3;
   
   
   protected static final int   SHOW_PREFERENCES = 1,
                                SHOW_ABOUT = 2,
                                SHOW_OPEN = 3;

   private int iLinesNbr = 0,
               iTextSize = 12,
               iEnuChordsShow = 0;
   
   private CSong oSong;
   
   ArrayList<CTextVersesSet> alPages;

   LayoutInflater inflater;   //Used to create individual pages
   
   ViewPager vp;   //Reference to class to swipe views

   private CPagerAdapter mPagerAdapter;
   
   TextView tvTitle,
            tvChords;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      super.setContentView(R.layout.activity_main);

      updateFromPreferences();
      
      File fSongsDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/" + SONGS_FOLDER);
      fSongsDir.mkdirs();      

      oSong = getSongFromResources();
//      getSongFromFile("");
      
      tvTitle = (TextView) findViewById(R.id.tvTitle);
      tvChords = (TextView) findViewById(R.id.tvChords);

      //initialsie the pager
      this.initialisePaging();      
   }

   private CSong getSongNew(XmlPullParser xmlSong)
   {
      CSong oSong = new CSong();
      
      CChordsCouplet oChordsCouplet = new CChordsCouplet();
      CChordsLine oChordsLine = new CChordsLine();
      CChord oChord;
    
      CTextLine oTextLine = new CTextLine(); 
      CTextVerse oTextVerse = new CTextVerse();
      
      try
      {
//         xmlSong.nextTag();
//         xmlSong.next();
         xmlSong.next();
         xmlSong.require(XmlPullParser.START_TAG, null, TAG_SONG);
         oSong.sTitle = xmlSong.getAttributeValue(null, ATR_TITLE);
         oSong.sAuthor = xmlSong.getAttributeValue(null, ATR_AUTHOR);
       
         if(xmlSong.getAttributeValue(null, ATR_LANGUAGE).equalsIgnoreCase(LNG_ENGLISH))
            oSong.iEnuLanguage = ENU_LNG_ENGLISH;
         else if(xmlSong.getAttributeValue(null, ATR_LANGUAGE).equalsIgnoreCase(LNG_BULGARIAN))
            oSong.iEnuLanguage = ENU_LNG_BULGARIAN;
         else if(xmlSong.getAttributeValue(null, ATR_LANGUAGE).equalsIgnoreCase(LNG_RUSSIAN))
            oSong.iEnuLanguage = ENU_LNG_RUSSIAN;
         
         while (xmlSong.nextTag() == XmlPullParser.START_TAG) 
         {
            xmlSong.require(XmlPullParser.START_TAG, null, TAG_CHORDS);
            while (xmlSong.nextTag() == XmlPullParser.START_TAG) 
            {
               xmlSong.require(XmlPullParser.START_TAG, null, TAG_CHORDS_COUPLET);
               oChordsCouplet = new CChordsCouplet();
               oChordsCouplet.sID = xmlSong.getAttributeValue(null, ATR_ID);
               
               while(xmlSong.nextTag() == XmlPullParser.START_TAG) 
               {
                  xmlSong.require(XmlPullParser.START_TAG, null, TAG_CHORDS_LINE);
                  oChordsLine = new CChordsLine();

//                  String ss = xmlSong.getText();
                  String ss = xmlSong.nextText();
                  String[] sChords = ss.split(" ");
                  for(int i = 0; i < sChords.length; i++)
                  {
                     oChord = new CChord(sChords[i]);
                     oChordsLine.addChord(oChord);
                  }
                  xmlSong.require(XmlPullParser.END_TAG, null, TAG_CHORDS_LINE);
                  oChordsCouplet.alChordsLines.add(oChordsLine);
               }
               xmlSong.require(XmlPullParser.END_TAG, null, TAG_CHORDS_COUPLET);
               oSong.alChords.add(oChordsCouplet);
               
            }
            xmlSong.require(XmlPullParser.END_TAG, null, TAG_CHORDS);
            xmlSong.nextTag();
            xmlSong.require(XmlPullParser.START_TAG, null, TAG_TEXT);
            while(xmlSong.nextTag() == XmlPullParser.START_TAG) 
            {
               xmlSong.require(XmlPullParser.START_TAG, null, TAG_TEXT_VERSE);
               
               oTextVerse = new CTextVerse();
               oTextVerse.sChordsCoupletID = xmlSong.getAttributeValue(null, ATR_ID_CHORD_COUPLET);
               String sIsChorus = xmlSong.getAttributeValue(null, ATR_ID_IS_CHORUS);
               if(sIsChorus == null)
                  oTextVerse.bIsChorus = false;
               else
                  oTextVerse.bIsChorus = Integer.parseInt(sIsChorus) == 1;
               
               while(xmlSong.nextTag() == XmlPullParser.START_TAG)
               {
                  xmlSong.require(XmlPullParser.START_TAG, null, TAG_TEXT_LINE);
                  oTextLine = new CTextLine();
//                  oTextLine.sTextLine = xmlSong.getText();
                  oTextLine.sTextLine = xmlSong.nextText();
                  xmlSong.require(XmlPullParser.END_TAG, null, TAG_TEXT_LINE);
                  oTextVerse.addTextLine(oTextLine);
               }
               xmlSong.require(XmlPullParser.END_TAG, null, TAG_TEXT_VERSE);
               oSong.oText.alTextVerses.add(oTextVerse);
            }
            xmlSong.require(XmlPullParser.END_TAG, null, TAG_TEXT);
         }
         xmlSong.require(XmlPullParser.END_TAG, null, TAG_SONG);         
      } 
      catch(XmlPullParserException e)
      {
         e.printStackTrace();
      } 
      catch(IOException e)
      {
         e.printStackTrace();
      }
      
      return oSong;
   }   
   
   private CSong getSongFromResources()
   {
      XmlResourceParser xmlSong = getResources().getXml(R.xml.antonina);
      CSong oSong = null;
      try
      {
         xmlSong.next();
         oSong = getSongNew(xmlSong);
      } catch(XmlPullParserException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      
      return oSong;
   }
   
//   private CSong getSongFromFile(String sFile)
   private void getSongFromFile(String sFile) 
   {
      try
      {
         XmlPullParserFactory oXmlPullParserFactory = XmlPullParserFactory.newInstance();
         
         oXmlPullParserFactory.setValidating(false);
         XmlPullParser xmlSong = oXmlPullParserFactory.newPullParser();

         xmlSong.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

         InputStream in = getApplicationContext().getAssets().open("antonina.xml");
         xmlSong.setInput(in, null);
         
         oSong = getSongNew(xmlSong);
         
         setTitle();
         
         if(iEnuChordsShow == ENU_SHOW_CHORDS_ALL)
            setChords();
         
      } catch(FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch(XmlPullParserException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

//      return oSong;
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {  
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item) 
   {
      // Handle item selection
      switch (item.getItemId()) 
      {
         case R.id.action_open:
            startActivityForResult(new Intent(this, Open.class), SHOW_OPEN);
            return true;

         
         case R.id.action_settings:
            startActivityForResult(new Intent(this, Preferences.class), SHOW_PREFERENCES);
            return true;

         case R.id.action_about:
            startActivityForResult(new Intent(this, About.class), SHOW_ABOUT);
            return true;
            
         default:
            return super.onOptionsItemSelected(item);
      }
   }
   
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
   {
      super.onActivityResult(requestCode, resultCode, intent);
      
      if(requestCode == SHOW_PREFERENCES)
      {
         if(resultCode == Activity.RESULT_CANCELED)
         {
            updateFromPreferences();
            if(mPagerAdapter != null)
            {
               mPagerAdapter.deleteAllItems();
               mPagerAdapter.notifyDataSetChanged();
            }
            iLinesNbr = 0;
            initialisePaging();
         }
      }
      else if(requestCode == SHOW_OPEN)
      {
         if(resultCode == Activity.RESULT_OK)
         {
            Bundle extras = intent.getExtras();
            String sFile = extras.getString("file", "");
            if(!sFile.isEmpty())
            {
//               oSong = getSongFromFile(sFile);
               getSongFromFile(sFile);
               if(mPagerAdapter != null)
               {
                  mPagerAdapter.deleteAllItems();
                  mPagerAdapter.notifyDataSetChanged();
               }
               iLinesNbr = 0;
               initialisePaging();               
            }
         }
      }
   }
   
   private void initialisePaging() 
   {
      Fragment fPage;
      Bundle bundle;
      
      List<Fragment> fragments = new Vector<Fragment>();
      fPage = Fragment.instantiate(this, CTextFragment.class.getName());
      bundle = new Bundle();
      bundle.putInt("index", 0);
      bundle.putInt("text_size", iTextSize);
      fPage.setArguments(bundle);
      fragments.add(fPage);
      this.mPagerAdapter  = new CPagerAdapter(super.getSupportFragmentManager(), fragments);
      
      ViewPager pager = (ViewPager)super.findViewById(R.id.viewPager);
      pager.setAdapter(this.mPagerAdapter);
   }

   @Override
   public void setLinesNbr(int iLinesNbr)
   {
      this.iLinesNbr = iLinesNbr;
      
      preparePages();
      
      if(alPages.size() > 1)
      {
         Fragment fPage;
         Bundle bundle;
         
         for(int iPageNdx = 1; iPageNdx < alPages.size(); iPageNdx++)
         {
            fPage = Fragment.instantiate(this, CTextFragment.class.getName());
            bundle = new Bundle();
            bundle.putInt("index", iPageNdx);
            bundle.putInt("text_size", iTextSize);
            fPage.setArguments(bundle);
            this.mPagerAdapter.addItem(fPage);
         }
         
         this.mPagerAdapter.notifyDataSetChanged();
      }
   }

   private void preparePages()
   {
      int iTextSize = 0,
      iTextVerseNdx = 0;

      CTextVerse oTextVerse;
      
      CTextVersesSet oPage = new CTextVersesSet();
   
      alPages = new ArrayList<CTextVersesSet>();
      for(iTextVerseNdx = 0; iTextVerseNdx < oSong.oText.alTextVerses.size(); iTextVerseNdx++)
      {
         oTextVerse = oSong.oText.alTextVerses.get(iTextVerseNdx);
         if(iTextSize != 0)
            iTextSize++;
         iTextSize += oTextVerse.alTextLines.size();
         if(iTextSize > iLinesNbr)
         {
            alPages.add(oPage);
         
            oPage = new CTextVersesSet();
         
           iTextSize = 0;
           iTextVerseNdx--;
         }
         else
         {
            oPage.alTextVerses.add(oTextVerse);
         }
      }
   
      if(oPage.alTextVerses.size() != 0)
      {
         alPages.add(oPage);
      }      
   }
   
   @Override
   public int getLinesNbr()
   {
      return iLinesNbr;
   }   
   
   @Override
   public ArrayList<CTextVerse> getPage(int iNdx)
   {
      return alPages.get(iNdx).alTextVerses;
   }
   
   private void updateFromPreferences()
   {
      Context context = getApplicationContext();
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
      
      
      iEnuChordsShow = Integer.parseInt(prefs.getString(Preferences.PREF_SHOW_CHORDS, "0"));
      int iTextSize = Integer.parseInt(prefs.getString(Preferences.PREF_TEXT_SIZE, "16"));
      
      if(this.iTextSize != iTextSize)
      {
         this.iTextSize = iTextSize;
      }
   }

   @Override
   public int getTextSize()
   {
      return iTextSize;
   }
   
   private void setTitle()
   {
      tvTitle.setText(oSong.sAuthor + " - " + oSong.sTitle);
   }
   
   private void setChords()
   {
      tvChords.setText(null);
      for(CChordsCouplet oChordsCouplet: oSong.alChords)
      {
         tvChords.append(oChordsCouplet.toString());
         tvChords.append("\n\n");
      }
   }
}
