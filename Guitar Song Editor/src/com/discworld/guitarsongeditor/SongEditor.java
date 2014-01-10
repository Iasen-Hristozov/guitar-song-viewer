package com.discworld.guitarsongeditor;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.JLabel;

import com.discworld.guitarsongeditor.dto.CChord;
import com.discworld.guitarsongeditor.dto.CChordsLine;
import com.discworld.guitarsongeditor.dto.CChordsVerse;
import com.discworld.guitarsongeditor.dto.CSong;
import com.discworld.guitarsongeditor.dto.CTextLine;
import com.discworld.guitarsongeditor.dto.CTextVerse;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;

public class SongEditor extends JFrame implements ActionListener
{
   private JTextArea txtSong,
                      txtXml;
   private JPanel pnlRawText, pnlXmlText, pnlBtn;
   private JButton btnConvert, btnGenerate;
   private Component rigidArea;
   private Box verticalBox;
   private JTextField txtTitle;
   private JLabel lblTitle;
   private JPanel panel;
   private JPanel panel_1;
   private JTextField txtAuthor;
   private JLabel lblAuthor;
   private String sSong;
   private ArrayList<String> alVerses;
   private CSong oSong;
   
   private Pattern ptrText = Pattern.compile("[^ A-Hmoldurs#1-9]"),
                   ptrChord = Pattern.compile("[A-H]");

   private class CChordsTextPair
   {
      public String sChordsLine;
      public String sTextLine;
      
      public CChordsTextPair()
      {
         sChordsLine = "";
         sTextLine = "";
      }
   }
   
   private ArrayList<CChordsTextPair> alChordsTextPairs;
   private JComboBox comboBox;
   private JPanel panel_2;
   private JLabel lblNewLabel;
   
   /**
    * Launch the application.
    */
   public static void main(String[] args)
   {
      SwingUtilities.invokeLater(new Runnable()
      {
         @Override
         public void run()
         {
            try
            {
               new SongEditor();
//               SongEditor window = new SongEditor();
//               window.frame.setVisible(true);
            } catch(Exception e)
            {
               e.printStackTrace();
            }
         }
      });
   }

   /**
    * Create the application.
    */
   public SongEditor()
   {
      super("Song Editor");
      initialize();
   }

   /**
    * Initialize the contents of the frame.
    */
   private void initialize()
   {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container container = this.getContentPane();
      getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
      
      
      pnlRawText = new JPanel();
      pnlRawText.setLayout(new BorderLayout(0, 0));
      
      panel = new JPanel();
      pnlRawText.add(panel, BorderLayout.NORTH);
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      
      lblTitle = new JLabel("Title");
      lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
      panel.add(lblTitle);
      
      txtTitle = new JTextField();
      panel.add(txtTitle);
      txtTitle.setColumns(20);
      
      lblAuthor = new JLabel("Author");
      panel.add(lblAuthor);
      
      txtAuthor = new JTextField();
      panel.add(txtAuthor);
      txtAuthor.setColumns(10);
      
      panel_2 = new JPanel();
      panel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
      panel.add(panel_2);
      panel_2.setLayout(new BorderLayout(0, 0));
      
      lblNewLabel = new JLabel("Language  ");
      lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
      panel_2.add(lblNewLabel, BorderLayout.WEST);
      
      comboBox = new JComboBox();
      comboBox.setMaximumRowCount(3);
      comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "\u0411\u044A\u043B\u0433\u0430\u0440\u0441\u043A\u0438", "\u0420\u0443\u0441\u043A\u0438\u0439"}));
      lblNewLabel.setLabelFor(comboBox);
      panel_2.add(comboBox, BorderLayout.CENTER);
      
      container.add(pnlRawText);
            
      panel_1 = new JPanel();
      pnlRawText.add(panel_1, BorderLayout.CENTER);
      panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
            
//      container.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
            
      txtSong = new JTextArea(0, 20);
      txtSong.setFont(new Font("Courier New", Font.PLAIN, 12));
            
      JScrollPane scrSong = new JScrollPane(txtSong,
               JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
               JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      panel_1.add(scrSong);
      
      pnlBtn = new JPanel();
      pnlBtn.setBounds(10, 10, 10, 10);
      container.add(pnlBtn);
      pnlBtn.setLayout(new BoxLayout(pnlBtn, BoxLayout.Y_AXIS));
      
      verticalBox = Box.createVerticalBox();
      verticalBox.setBorder(UIManager.getBorder("ToolBar.border"));
      pnlBtn.add(verticalBox);
      
      btnConvert = new JButton(">>");
      verticalBox.add(btnConvert);
      btnConvert.setAlignmentX(Component.CENTER_ALIGNMENT);
      btnConvert.addActionListener(this);
      
      rigidArea = Box.createRigidArea(new Dimension(0, 5));
      verticalBox.add(rigidArea);
      btnGenerate = new JButton("<<");
      verticalBox.add(btnGenerate);
      btnGenerate.setAlignmentX(Component.CENTER_ALIGNMENT);

      btnGenerate.addActionListener(this);
            
      txtXml = new JTextArea(5, 20);

      JScrollPane scrXml = new JScrollPane(txtXml,
               JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
               JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      
      
      pnlXmlText = new JPanel();
      pnlXmlText.setLayout(new BorderLayout(0, 0));
      
      pnlXmlText.add(scrXml);
      
      container.add(pnlXmlText);
      
      this.setSize(427, 370);
      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      Object oSource = e.getSource();
      
      if(oSource == btnConvert)
      {
         txtXml.setText(txtSong.getText());
         sSong = txtSong.getText();
         convertSongToXml();
      }
      else if(oSource == btnGenerate)
      {
         
      }
   }
   
   private void convertSongToXml()
   {
      Matcher mtcText;
//      sSong.
      getVerses();

      if(alVerses.isEmpty())
         return;
      
      oSong = new CSong();
      for(String sVerse: alVerses)
      {
//         ArrayList<String> alVerseLines = getLinesFromVerse(sVerse);
         String tsVerseLines[] = getLinesFromVerse(sVerse);

         int iOffset = getVerseOffset(tsVerseLines);

         if(iOffset != 0)
         {
            for(int i = 0; i < tsVerseLines.length; i++)
               tsVerseLines[i] = tsVerseLines[i].substring(iOffset);
         }
         CChordsTextPair oChordsTextPair = null;
         alChordsTextPairs = new ArrayList<CChordsTextPair>();
         for(int i = 0; i < tsVerseLines.length; i++)
         {
            mtcText = ptrText.matcher(tsVerseLines[i]);
            if(!mtcText.find())
            {
               oChordsTextPair = new CChordsTextPair();
               oChordsTextPair.sChordsLine = tsVerseLines[i];
            }
            else
            {
               if(oChordsTextPair == null)
                  oChordsTextPair = new CChordsTextPair();
               oChordsTextPair.sTextLine = tsVerseLines[i];
               alChordsTextPairs.add(oChordsTextPair);
               oChordsTextPair = null;
            }
         }
         
         CTextVerse oTextVerse = null;
         CTextLine oTextLine = null;
         CChordsLine oChordsLine = null;
         CChordsVerse oChordsVerse = null;
         CChord oChord = null;
         
         for(CChordsTextPair oChordsTextPair2 : alChordsTextPairs)
         {
            if(!oChordsTextPair2.sChordsLine.isEmpty())
            {
               oChordsLine = new CChordsLine();
               
               String tsChords[] = oChordsTextPair2.sChordsLine.split(" ");
               for(int i = 0; i < tsChords.length; i++)
               {
                  if(!tsChords[i].isEmpty())
                  {
                     oChord = new CChord(tsChords[i]);
                     oChordsLine.addChord(oChord);      
                  }
               }
               
               if(oChordsVerse == null)
                  oChordsVerse = new CChordsVerse();
               
               oChordsVerse.alChordsLines.add(oChordsLine);
            }
            
            if(!oChordsTextPair2.sTextLine.isEmpty())
            {
               oTextLine = new CTextLine();
               oTextLine.sTextLine = oChordsTextPair2.sTextLine;
               if(oTextVerse == null)
                  oTextVerse = new CTextVerse();
               oTextVerse.alTextLines.add(oTextLine);
            }
         }
         if(oChordsVerse != null)
         {
            oChordsVerse.sID = String.valueOf(oSong.alChords.size());
            oSong.alChords.add(oChordsVerse);
         }
         if(oTextVerse != null)
         {
            if(oChordsVerse != null)
               oTextVerse.sChordsVerseID = oChordsVerse.sID;
            oSong.oText.alTextVerses.add(oTextVerse);
         }
      }
      
      int a = 1;
   }
   
   private int getVerseOffset(String[] tsVerseLines)
   {
      int iOffset = 0;

      Matcher matcher;            
      
      for(int i = 0; i < tsVerseLines.length; i++)
      {
//         if(tsVerseLines[i].matches("[^A-Hmoldurs0-9]"))
//         if(tsVerseLines[i].matches("[^A-H]"))
         matcher = ptrText.matcher(tsVerseLines[i]);
         if(matcher.find())
         {
            iOffset = getOffset(tsVerseLines[i]);
            break;
         }
      }      
      return iOffset; 
   }
   
   private int getOffset(String string)
   {
      int iOffset = 0;
      for(int i = 0; i < string.length(); i++)
      {
         if(string.charAt(i) == ' ')
            iOffset++;
         else if(string.charAt(i) == '\n')
            continue;
         else
            break;
      }
      return iOffset;
   }

   //   private ArrayList<String> getLinesFromVerse(String sVerse)
   private String[] getLinesFromVerse(String sVerse)
   {
      return sVerse.split("\n");
   }

   private void getVerses()
   {
      alVerses = new ArrayList<String>(); 
      String tsVerses[] = sSong.split("\n\n");
      
      for(int i = 0; i < tsVerses.length; i++)
      {
         if(!tsVerses[i].trim().isEmpty())
            alVerses.add(tsVerses[i]);
      }
      
//      int a = 1;
   }

}
