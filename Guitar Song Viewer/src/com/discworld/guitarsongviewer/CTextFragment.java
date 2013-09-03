package com.discworld.guitarsongviewer;

import java.util.ArrayList;

import com.discworld.dto.CTextVerse;
import com.discworld.dto.IDataExchange;

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
   
   private int iLinesNbr;
   
   private int iNdx;
   
   private ArrayList<CTextVerse> alText;
   
   private TextView tvText; 
   
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
      
//      String strtext = getArguments().getString("edttext", "");
      iNdx = getArguments().getInt("index", 0);
//      int iTextSize = getArguments().getInt("text_size", 14);
      int iTextSize = oDataExchange.getTextSize();
//      iLinesNbr = getArguments().getInt("LinesNbr", 0);
      iLinesNbr = oDataExchange.getLinesNbr();
//      View vPage = (RelativeLayout)inflater.inflate(R.layout.page, container, false);
      View vPage = inflater.inflate(R.layout.page, container, false);
      tvText = (TextView) vPage.findViewById(R.id.tvText);
      tvText.setTextSize(iTextSize);
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
              
              tvText.setText(null);
              for(CTextVerse oTextVerse : alText)
              {
                 tvText.append(oTextVerse.toString());
                 tvText.append("\n\n");
              }
              
              
//              CTextVerse oTextVerse;
//              int iTextSize = 0;
//              int iPageNdx = 0;
//              int iTextVerseNdx = 0;
//              iPageNdx = getId();
//              alText = getArguments().getParcelableArrayList("verse");
//              
//              tvText.setText(null);
//              do
//              {
//                 oTextVerse = alText.get(iTextVerseNdx);
//                 if(iTextSize + oTextVerse.alTextLines.size() <= iLinesNbr)
//                 {
//                    tvText.append(oTextVerse.toString());
//                    iTextSize += oTextVerse.alTextLines.size();
//
//                    if(iTextSize + 1 < iLinesNbr)
//                    {
//                       tvText.append("\n\n");
//                       iTextSize++;
//                    }
//                 }
//                 iTextVerseNdx++;
//              }while(iTextSize + alText.get(iTextVerseNdx).alTextLines.size() <= iLinesNbr);
           }
       });         
      }
      else
      {
         alText = oDataExchange.getPage(iNdx);
//         alText = getArguments().getParcelableArrayList("verse");         
         
         tvText.setTextSize(iTextSize);
         tvText.setText(null);
         for(CTextVerse oTextVerse : alText)
         {
            tvText.append(oTextVerse.toString());
            tvText.append("\n\n");
         }
      }
//      else
//      {
//         int iPageNdx = getId();
//         
//         alText = getArguments().getParcelableArrayList("verse");
//         
//         tvText.setText(null);
//         for(CTextVerse oTextVerse : alText)
//         {
//            tvText.append(oTextVerse.toString());
//            tvText.append("\n\n");
//         }
   //      }
//      alText = oDataExchange.getPage(iNdx);
//       
//      tvText.setText(null);
//      for(CTextVerse oTextVerse : alText)
//      {
//         tvText.append(oTextVerse.toString());
//         tvText.append("\n\n");
//      }
      
      
//      tvText.setText(strtext);
      
      return vPage;
   }
   
   @Override
   public void onAttach(Activity activity)
   {
      super.onAttach(activity);
      oDataExchange = (IDataExchange) activity;
   }
   
   public void setTextSize(int iTextSize)
   {
      tvText.setText("HIHI");
      tvText.setTextSize(iTextSize);
      int iHeight = tvText.getHeight();
      int iLinesHeght = tvText.getLineHeight();
      iLinesNbr = iHeight / iLinesHeght;      
      
//      ViewTreeObserver vto = tvText.getViewTreeObserver();
//      vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() 
//      {
//          public void onGlobalLayout() 
//          {
//             ViewTreeObserver obs = tvText.getViewTreeObserver();
//             obs.removeGlobalOnLayoutListener(this);              
//             int iHeight = tvText.getHeight();
//             int iLinesHeght = tvText.getLineHeight();
//             iLinesNbr = iHeight / iLinesHeght;
//             oDataExchange.setLinesNbr(iLinesNbr);
//             
//             alText = oDataExchange.getPage(iNdx);
//             
//             tvText.setText(null);
//             for(CTextVerse oTextVerse : alText)
//             {
//                tvText.append(oTextVerse.toString());
//                tvText.append("\n\n");
//             }
//             
//             
//          }
//      });               
      
   }
   
   
}