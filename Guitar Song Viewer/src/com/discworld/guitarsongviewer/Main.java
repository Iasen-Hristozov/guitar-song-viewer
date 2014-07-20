package com.discworld.guitarsongviewer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.discworld.guitarsonglib.CChordsVerse;
import com.discworld.guitarsonglib.CSong;
import com.discworld.guitarsonglib.CVerse;
import com.discworld.guitarsonglib.CVerseSet;
import com.discworld.guitarsongviewer.dto.*;

public class Main extends FragmentActivity implements IDataExchange
{
   public final static int ENU_LNG_UNKNOWN = 0,
                           ENU_LNG_ENGLISH = 1,
                           ENU_LNG_BULGARIAN = 2,
                           ENU_LNG_RUSSIAN = 3;
   public final static String SONGS_FOLDER = "Songs";
   
   public final static String SONGS_SUFFIX = ".xml";
                              
   public final static String LNG_ENGLISH = "en",
                              LNG_BULGARIAN = "bg",
                              LNG_RUSSIAN = "ru";

   public final static int ENU_DISPLAY_SONG_NONE = 0,
                           ENU_DISPLAY_SONG_PAGER = 1,
                           ENU_DISPLAY_SONG_SCROLL = 2;
   
   public final static int ENU_DISPLAY_CHORDS_NONE = 1,
                           ENU_DISPLAY_CHORDS_ALL = 0,
                           ENU_DISPLAY_CHORDS_RELATED = 2,
                           ENU_DISPLAY_CHORDS_ABOVE = 3;
   
   
   
   protected static final int   SHOW_PREFERENCES = 1,
                                SHOW_ABOUT = 2,
                                SHOW_OPEN = 3;

   private int iLinesNbr = 0,
               iTextSize = 12,
               iEnuDisplayChords = 0,
               iEnuDisplaySong = 1;
   
   private CSong oSong;
   
   ArrayList<CVerseSet> alPages;
//   ArrayList<CChordsTextPairVerseSet> alPages1;

   LayoutInflater inflater;   //Used to create individual pages
   
   ViewPager vp;   //Reference to class to swipe views

   private CPagerAdapter mPagerAdapter;
   
   private TextView tvTitle,
                    tvChords,
                    tvChords1,
                    tvText;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      
      updateFromPreferences();

      File fSongsDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/" + SONGS_FOLDER);
      fSongsDir.mkdirs();      
      
      oSong = getSongFromResources();
      
      if(iEnuDisplaySong == ENU_DISPLAY_SONG_PAGER)
      {
         super.setContentView(R.layout.activity_main);

         tvTitle = (TextView) findViewById(R.id.tvTitle);
         tvChords = (TextView) findViewById(R.id.tvChords);

         if(iEnuDisplayChords != ENU_DISPLAY_CHORDS_ALL)
            tvChords.setVisibility(View.GONE);
         else
            tvChords.setVisibility(View.VISIBLE);         
         
         setTitle();
         
         if(iEnuDisplayChords == ENU_DISPLAY_CHORDS_ALL)
            setChords();
         
         
         
         //initialsie the pager
         this.initialisePaging();      
         
//         startActivityForResult(new Intent(this, Open.class), SHOW_OPEN);
      }
      else if(iEnuDisplaySong == ENU_DISPLAY_SONG_SCROLL)
      {
         super.setContentView(R.layout.activity_main_scroll);
         
         tvTitle = (TextView) findViewById(R.id.tvTitle);
         tvChords = (TextView) findViewById(R.id.tvChords);
         tvChords1 = (TextView) findViewById(R.id.tvChords1);
         tvText = (TextView) findViewById(R.id.tvText);
         
         if(iEnuDisplayChords != ENU_DISPLAY_CHORDS_ALL)
            tvChords.setVisibility(View.GONE);
         else
            tvChords.setVisibility(View.VISIBLE);
         
         ArrayList<? extends CVerse> alVerses = (iEnuDisplayChords == ENU_DISPLAY_CHORDS_ABOVE ? oSong.getChordsTextVerses() : oSong.oText.getTextVersesSet());
         
         setTitle();
         
         tvText.setText(alVerses.toString());
      }
      
   }
   
   private CSong getSongFromResources() 
   {
//      XmlResourceParser xmlSong = getResources().getXml(R.xml.antonina_2);
      try
      {
         InputStream oInputStream = getResources().openRawResource(R.raw.antonina_2);
   
         byte[] b = new byte[oInputStream.available()];
         oInputStream.read(b);
         oInputStream.close();
         String xmlSomg = new String(b, "UTF-8");
         
         oSong = new CSong(xmlSomg);
         
      } 
      catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      
      return oSong;
   }
   
   
   private void getSongFromFile(String sFile) 
   {
      try
      {
         InputStream oInputStream =  new BufferedInputStream(new FileInputStream(sFile));
         byte[] b = new byte[oInputStream.available()];
         oInputStream.read(b);
         oInputStream.close();
         String xmlSomg = new String(b, "UTF-8");
         
         oSong = new CSong(xmlSomg);
         
         setTitle();
         
         if(iEnuDisplayChords == ENU_DISPLAY_CHORDS_ALL)
            setChords();
         
      } catch(FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
//      catch(XmlPullParserException e)
//      {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      } 
      catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
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
            int iEnuDisplaySongOld = iEnuDisplaySong;
            updateFromPreferences();
            if(iEnuDisplaySongOld != iEnuDisplaySong)
            {
               refresh();
            }
            if(mPagerAdapter != null)
            {
               mPagerAdapter.deleteAllItems();
               mPagerAdapter.notifyDataSetChanged();
            }
            iLinesNbr = 0;
            
            if(iEnuDisplayChords == ENU_DISPLAY_CHORDS_ALL)
               setChords();            
            
            initialisePaging();
         }
      }
      else if(requestCode == SHOW_OPEN)
      {
         if(resultCode == Activity.RESULT_OK)
         {
            Bundle extras = intent.getExtras();
//            String sFile = extras.getString("file", "");
            String sFile = extras.getString("file");
            if(!sFile.isEmpty())
            {
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
            fPage.setArguments(bundle);
            this.mPagerAdapter.addItem(fPage);
         }
         
         this.mPagerAdapter.notifyDataSetChanged();
      }
   }

   private void preparePages()
   {
      int iTextSize = 0,
      iNdx = 0;

      CVerseSet oPage = new CVerseSet();
   
      alPages = new ArrayList<CVerseSet>();
      
//      ArrayList<? extends CVerse> alVerses = (iEnuDisplayChords == ENU_DISPLAY_CHORDS_ABOVE ? oSong.getChordsTextVerses() : oSong.oText.alTextVerses);
      ArrayList<? extends CVerse> alVerses = (iEnuDisplayChords == ENU_DISPLAY_CHORDS_ABOVE ? oSong.getChordsTextVerses() : oSong.oText.getTextVersesSet());
      CVerse oVerse;
      
      for(iNdx = 0; iNdx < alVerses.size(); iNdx++)
      {
         oVerse = alVerses.get(iNdx);
         if(iTextSize != 0)
            iTextSize++;
         iTextSize += oVerse.size();
         if(iTextSize > iLinesNbr)
         {
            alPages.add(oPage);
         
            oPage = new CVerseSet();
         
           iTextSize = 0;
           iNdx--;
         }
         else
         {
            oPage.add(oVerse);
         }
      }      
   
      if(oPage.size() != 0)         
      {
         alPages.add(oPage);
      }      
   }
   
   @Override
   public int getLinesNbr()
   {
      return iLinesNbr;
   }   
   
   private void updateFromPreferences()
   {
      Context context = getApplicationContext();
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
      
      iEnuDisplaySong = Integer.parseInt(prefs.getString(Preferences.PREF_DISPLAY_SONG, "1"));
      iEnuDisplayChords = Integer.parseInt(prefs.getString(Preferences.PREF_DISPLAY_CHORDS, "0"));
      int iTextSize = Integer.parseInt(prefs.getString(Preferences.PREF_TEXT_SIZE, "16"));
      
      if(this.iTextSize != iTextSize)
         this.iTextSize = iTextSize;
      
//      if(iEnuDisplayChords != ENU_DISPLAY_CHORDS_ALL)
//         tvChords.setVisibility(View.GONE);
//      else
//         tvChords.setVisibility(View.VISIBLE);
   
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
      for(CChordsVerse oChordsVerse: oSong.alChords)
      {
         tvChords.append(oChordsVerse.toString());
         tvChords.append("\n\n");
      }
   }

   @Override
   public int getEnuDisplayChords()
   {
      return iEnuDisplayChords;
   }
   
   private void refresh()
   {
      finish();
      Intent oIntent = new Intent(Main.this, Main.class);
      startActivity(oIntent);
   }

   @Override
   public CChordsVerse getChordsVerse(String sID)
   {
      return oSong.alChords.get(oSong.htChordsIdNdx.get(sID));
   }

   @Override
   public CVerseSet getPage(int iNdx)
   {
      return alPages.get(iNdx);
   }
}
