package com.discworld.guitarsongviewer;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainScrollOld extends Activity
{
   private int verticalScrollMax,
               scrollPos = 0,
               heightToScroll = 1,
               scrollPeriod = 20;

   private final int seekBarMax = 50;
   
   private ScrollView svMain;
   private LinearLayout llText;
   private TextView tvText;
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
      
      svMain  =   (ScrollView) findViewById(R.id.svMain);
      llText = (LinearLayout)findViewById(R.id.llText);
      tvText  = (TextView)findViewById(R.id.tvText);
      
      ViewTreeObserver vto =  llText.getViewTreeObserver();
      vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() 
      {
          @SuppressWarnings("deprecation")
          @Override
          public void onGlobalLayout() 
          {
             llText.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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
      int actualWidth = (llText.getMeasuredHeight());
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

   public void onBackPressed()
   {
      super.onBackPressed();
      finish();
   }
   
   public void onPause() 
   {
      super.onPause();
      finish();
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
