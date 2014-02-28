package com.discworld.guitarsongviewer;

import java.util.ArrayList;

import com.discworld.guitarsonglib.CChordsVerse;
import com.discworld.guitarsonglib.CTextVerse;
import com.discworld.guitarsongviewer.dto.IDataExchange;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author mwho
 *
 */
public class CTextFragment extends Fragment 
{
   private IDataExchange oDataExchange;
   
   private int iLinesNbr,   
               iNdx,
               iEnuDisplayChords;
   
   private ArrayList<CTextVerse> alText;
   
   private TextView tvText,
                    tvChords; 
   
   /** (non-Javadoc)
    * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
    */
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
   {
      if (container == null) 
      {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
      }
      
      iNdx = getArguments().getInt("index", 0);
      int iTextSize = oDataExchange.getTextSize();
      iLinesNbr = oDataExchange.getLinesNbr();
      View vPage = inflater.inflate(R.layout.page, container, false);
      tvText = (TextView) vPage.findViewById(R.id.tvText);
      tvText.setTextSize(iTextSize);
      tvChords = (TextView) vPage.findViewById(R.id.tvChords);

      if((iEnuDisplayChords = oDataExchange.getEnuDisplayChords()) != Main.ENU_DISPLAY_CHORDS_RELATED)
         tvChords.setVisibility(View.GONE);
      else
      {
         tvChords.setVisibility(View.VISIBLE);
         tvChords.setTextSize(iTextSize);
      }

      if(iLinesNbr == 0)
      {
       //set the adapter that will create the individual pages
       ViewTreeObserver vto = tvText.getViewTreeObserver();
       vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() 
       {
           public void onGlobalLayout() 
           {
              ViewTreeObserver obs = tvText.getViewTreeObserver();
              obs.removeGlobalOnLayoutListener(this);              
              int iHeight = tvText.getHeight();
              int iLinesHeght = tvText.getLineHeight();
              iLinesNbr = iHeight / iLinesHeght;
              oDataExchange.setLinesNbr(iLinesNbr);
              
              alText = oDataExchange.getPage(iNdx);
              
              setLyrics();
           }
       });         
      }
      else
      {
         alText = oDataExchange.getPage(iNdx);
         
         tvText.setTextSize(iTextSize);

         setLyrics();
      }
      return vPage;
   }
   
   private void setLyrics()
   {
      CChordsVerse oChordsVerse; 
      
      tvText.setText(null);
      tvChords.setText(null);
      for(CTextVerse oTextVerse : alText)
      {
         tvText.append(oTextVerse.toString() + "\n\n");
         
         if(iEnuDisplayChords == Main.ENU_DISPLAY_CHORDS_RELATED)
         {
            oChordsVerse = oDataExchange.getChordsVerse(oTextVerse.sChordsVerseID);
            tvChords.append(oChordsVerse.toString() + "\n\n");
         }
      }      
   }
   
   @Override
   public void onAttach(Activity activity)
   {
      super.onAttach(activity);
      oDataExchange = (IDataExchange) activity;
   }
}