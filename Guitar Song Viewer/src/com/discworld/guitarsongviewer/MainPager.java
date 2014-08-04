package com.discworld.guitarsongviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.discworld.guitarsonglib.CChordsVerse;
import com.discworld.guitarsonglib.CVerse;
import com.discworld.guitarsonglib.CVerseSet;
import com.discworld.guitarsongviewer.dto.*;

public class MainPager extends CMain implements IDataExchange
{
   public final static String SONGS_SUFFIX = ".xml";
   
   private int iLinesNbr = 0;
   
   ArrayList<CVerseSet> alPages;
//   ArrayList<CChordsTextPairVerseSet> alPages1;

   LayoutInflater inflater;   //Used to create individual pages
   
   ViewPager vp;   //Reference to class to swipe views

   private CPagerAdapter mPagerAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      super.setContentView(R.layout.activity_main);

      tvTitle = (TextView) findViewById(R.id.tvTitle);
//      tvChords = (TextView) findViewById(R.id.tvChordsRelative);
      tvChords = (TextView) findViewById(R.id.tvChords);

      setTitle();
      
      setDisplayChords();
      
      if(oApplication.getEnuDisplayChords() == ENU_DISPLAY_CHORDS_ALL)
         setChords();
      
      //initialsie the pager
      this.initialisePaging();      
      
//      startActivityForResult(new Intent(this, Open.class), SHOW_OPEN);
   }
   
   @Override
   protected void getSongFromFile(String sFile) 
   {
      super.getSongFromFile(sFile);
      
      setTitle();
      
      if(oApplication.getEnuDisplayChords() == ENU_DISPLAY_CHORDS_ALL)
         setChords();
   }
  
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
   {
      int iEnuDisplayChordsOld = oApplication.getEnuDisplayChords();
      
      super.onActivityResult(requestCode, resultCode, intent);
      
      if(requestCode == SHOW_PREFERENCES)
      {
         if(resultCode == Activity.RESULT_CANCELED)
         {
            if(iEnuDisplayChordsOld != oApplication.getEnuDisplayChords())
            {
               setDisplayChords();

               if(oApplication.getEnuDisplayChords() == ENU_DISPLAY_CHORDS_ALL)
                  setChords();            
            }
            
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

   private void setDisplayChords()
   {
      if(oApplication.getEnuDisplayChords() != ENU_DISPLAY_CHORDS_ALL)
         tvChords.setVisibility(View.GONE);
      else
         tvChords.setVisibility(View.VISIBLE);
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
      ArrayList<? extends CVerse> alVerses = (oApplication.getEnuDisplayChords() == ENU_DISPLAY_CHORDS_ABOVE ? oApplication.getSong().getChordsTextVerses() : oApplication.getSong().oText.getTextVersesSet());
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

   // TODO Optimize? Move in fragment?
   @Override
   public int getTextSize()
   {
      return oApplication.getTextSize();
   }

   // TODO Optimize? Move in fragment?
   @Override
   public int getEnuDisplayChords()
   {
      return oApplication.getEnuDisplayChords();
   }

   // TODO Optimize? Move in fragment?
   @Override
   public CChordsVerse getChordsVerse(String sID)
   {
      return oApplication.getSong().alChords.get(oApplication.getSong().htChordsIdNdx.get(sID));
   }

   @Override
   public CVerseSet getPage(int iNdx)
   {
      return alPages.get(iNdx);
   }
}
