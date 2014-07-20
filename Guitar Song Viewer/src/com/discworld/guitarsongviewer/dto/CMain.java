package com.discworld.guitarsongviewer.dto;

import com.discworld.guitarsongviewer.About;
import com.discworld.guitarsongviewer.Open;
import com.discworld.guitarsongviewer.Preferences;
import com.discworld.guitarsongviewer.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CMain extends Activity
{
   protected static final int   SHOW_PREFERENCES = 1,
                                SHOW_ABOUT = 2,
                                SHOW_OPEN = 3;   
   
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
   
   private void updateFromPreferences()
   {
      Context context = getApplicationContext();
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
      
      iEnuDisplayChords = Integer.parseInt(prefs.getString(Preferences.PREF_DISPLAY_CHORDS, "0"));
      int iTextSize = Integer.parseInt(prefs.getString(Preferences.PREF_TEXT_SIZE, "16"));
      
      if(this.iTextSize != iTextSize)
         this.iTextSize = iTextSize;
      
      if(iEnuDisplayChords != ENU_DISPLAY_CHORDS_ALL)
         tvChords.setVisibility(View.GONE);
      else
         tvChords.setVisibility(View.VISIBLE);
   }   
}
