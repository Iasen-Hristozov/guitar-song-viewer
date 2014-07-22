package com.discworld.guitarsongviewer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.discworld.guitarsonglib.CChordsVerse;
import com.discworld.guitarsonglib.CTextVerse;
import com.discworld.guitarsonglib.CVerse;
import com.discworld.guitarsongviewer.dto.CApplication;
import com.discworld.guitarsongviewer.dto.CMain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainScroll extends CMain
{
   private int verticalScrollMax,
               scrollPos = 0,
               heightToScroll = 1,
               scrollPeriod = 20;
   
   private final static int seekBarMax = 50;
   
   private ScrollView svMain;
   private RelativeLayout rlText;
   private TextView tvText,
                    tvChords1;
   private Timer scrollTimer     =  null;
   private TimerTask scrollerSchedule;
   private SeekBar sbSpeed;
   private Handler handler;
   
   private final Runnable Timer_Tick  =  new Runnable() 
   {
      public void run() 
      {
         moveScrollView();
      }
   };
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main_scroll);
      
      tvTitle = (TextView) findViewById(R.id.tvTitle);
      tvChords = (TextView) findViewById(R.id.tvChords);
      
      svMain  =   (ScrollView) findViewById(R.id.svMain);
      rlText = (RelativeLayout)findViewById(R.id.llText);
      tvText  = (TextView)findViewById(R.id.tvText);
      tvChords1 = (TextView) findViewById(R.id.tvChords1);
      
      tvText.setTextSize(oApplication.getTextSize());
      tvChords1.setTextSize(oApplication.getTextSize());
      
      setTitle();
      
      setSong();
      
      setDispalyChords();
      
      ViewTreeObserver vto =  rlText.getViewTreeObserver();
      vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() 
      {
          @SuppressWarnings("deprecation")
          @Override
          public void onGlobalLayout() 
          {
             rlText.getViewTreeObserver().removeGlobalOnLayoutListener(this);
             getScrollMaxAmount();
             startAutoScrolling(scrollPeriod);
          }
      });
      
      sbSpeed = (SeekBar) findViewById(R.id.sbSpeed);
      sbSpeed.setMax(seekBarMax);
      sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
      { 
         @Override 
         public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
         { 
            if(scrollTimer !=  null)
            {
               scrollTimer.cancel();
               scrollTimer = null;
            }

            if(progress != 0)
               startAutoScrolling(seekBar.getMax() - progress);
         } 

         @Override 
         public void onStartTrackingTouch(SeekBar seekBar) 
         { 
            // TODO Auto-generated method stub 
         } 

         @Override 
         public void onStopTrackingTouch(SeekBar seekBar) 
         { 
            // TODO Auto-generated method stub 
         } 
       }); 
   }

   private void setDispalyChords()
   {
      if(oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_NONE || oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_ABOVE)
      {
         tvChords1.setVisibility(View.GONE);
         tvChords.setVisibility(View.GONE);
      }
      else if(oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_ALL)
      {
         tvChords.setVisibility(View.VISIBLE);
         tvChords1.setVisibility(View.GONE);
         setChords();
      }
      else if(oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_RELATED)
      {
         tvChords.setVisibility(View.GONE);
         tvChords1.setVisibility(View.VISIBLE);
      }
   }
   
   private void setSong()
   {
      CChordsVerse oChordsVerse;
      
      tvText.setText(null);
      
      if(oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_RELATED)
         tvChords1.setText(null);
      
      ArrayList<? extends CVerse> alVerses = (oApplication.getEnuDisplayChords() == ENU_DISPLAY_CHORDS_ABOVE ? oApplication.getSong().getChordsTextVerses() : oApplication.getSong().oText.getTextVersesSet());
      CVerse oVerse;
      
      for(int iNdx = 0; iNdx < alVerses.size(); iNdx++)
      {
         oVerse = alVerses.get(iNdx);
         tvText.append(oVerse.toString() + "\n\n");
         
         if(oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_RELATED && !((CTextVerse) oVerse).sChordsVerseID.isEmpty())
         {
            oChordsVerse = oApplication.getSong().alChords.get(oApplication.getSong().htChordsIdNdx.get(((CTextVerse) oVerse).sChordsVerseID));
            tvChords1.append(oChordsVerse.toString() + "\n\n");
         }
         
      }
   }
   
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent intent) 
   {
      int iEnuDisplayChordsOld = oApplication.getEnuDisplayChords();
      int iTextSizeOld = oApplication.getTextSize();
      
      super.onActivityResult(requestCode, resultCode, intent);
      
      if(requestCode == SHOW_PREFERENCES)
      {
         if(resultCode == Activity.RESULT_CANCELED)
         {
            if(iEnuDisplayChordsOld != oApplication.getEnuDisplayChords())
            {
               setDispalyChords();
               setSong();
            }
            if(iTextSizeOld != oApplication.getTextSize())
            {
               tvText.setTextSize(oApplication.getTextSize());
               if(oApplication.getEnuDisplayChords() == CApplication.ENU_DISPLAY_CHORDS_RELATED)
                  tvChords1.setTextSize(oApplication.getTextSize());
            }
         }
      }
      else if(requestCode == SHOW_OPEN)
      {
         setDispalyChords();
         setSong();
      }
   }   
   
   public void startAutoScrolling1()
   {
      handler = new Handler();
      
      Runnable runnable = new Runnable() {
         @Override
         public void run() {
            /* do what you need to do */
            moveScrollView();
            /* and here comes the "trick" */
            int delay = sbSpeed.getProgress();
            handler.postDelayed(this, delay);
         }
      };      
      
      
      handler.postDelayed(runnable, 100);
   }

   public void getScrollMaxAmount()
   {
//      int actualWidth = (llText.getMeasuredHeight()-(256*3));
      int actualWidth = (rlText.getMeasuredHeight());
      verticalScrollMax   = actualWidth;
   }
   
   public void startAutoScrolling(int scrollPeriod)
   {
      if(scrollerSchedule != null)
      {
         scrollerSchedule.cancel();
         scrollerSchedule = null;
      }
      
      scrollerSchedule = new TimerTask()
      {
         @Override
         public void run()
         {
            runOnUiThread(Timer_Tick);
         }
      };
      
      scrollTimer = new Timer();      
//      scrollTimer.schedule(scrollerSchedule, 30, scrollPeriod);
      scrollTimer.schedule(scrollerSchedule, 0, scrollPeriod);
   }
   
   public void moveScrollView()
   {
      scrollPos = (int) (svMain.getScrollY() + heightToScroll);
      
      if(scrollPos >= verticalScrollMax)
      {
//         scrollPos = 0;
         return;
      }
      svMain.scrollTo(0, scrollPos);
//      Log.e("moveScrollView","moveScrollView");    
   }

//   public void onBackPressed()
//   {
//      super.onBackPressed();
//      finish();
//   }
   
   public void onPause() 
   {
//      try
//      {
//         scrollTimer.wait();
//         scrollerSchedule.wait();
//      } catch(InterruptedException e)
//      {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
      super.onPause();
//      finish();
   }
   
   public void onDestroy()
   {
      clearTimerTaks(scrollerSchedule);
      clearTimers(scrollTimer);
      
      scrollerSchedule      = null;
      scrollTimer           = null;
      
      super.onDestroy();
   }
   
   private void clearTimers(Timer timer)
   {
       if(timer != null) 
       {
          timer.cancel();
           timer = null;
       }
   }
   
   private void clearTimerTaks(TimerTask timerTask)
   {
      if(timerTask != null) 
      {
         timerTask.cancel();
         timerTask = null;
      }
   }
}
