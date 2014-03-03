package com.discworld.guitarsongeditor;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;

import javax.swing.UIManager;
import javax.swing.JLabel;

import com.discworld.englishhyphenator.CEnglishHyphenator;
import com.discworld.guitarsonglib.CChord;
import com.discworld.guitarsonglib.CChordsLine;
import com.discworld.guitarsonglib.CChordsTextPair;
import com.discworld.guitarsonglib.CChordsVerse;
import com.discworld.guitarsonglib.CSong;
import com.discworld.guitarsonglib.CTextLine;
import com.discworld.guitarsonglib.CTextVerse;

import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;

import java.awt.Font;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.*;

public class SongEditor extends JFrame implements ActionListener
{
   /**
    * 
    */
   private static final long serialVersionUID = 7937537729513348982L;
   
   private JTextArea txtSong;
//                      txtXml;
   private JPanel pnlRawText, pnlXmlText, pnlBtn;
   private JButton btnConvert;
   private Box verticalBox;
   private JTextField txtTitle;
   private JLabel lblTitle;
   private JPanel panel;
   private JPanel pnlSong;
   private JTextField txtAuthor;
   private JLabel lblAuthor;
   private String sSong,
                  xmlSong;
   private ArrayList<String> alVerses;
   private CSong oSong;
   
   private final static Pattern ptrText = Pattern.compile("[^ A-Hmoldurs#1-9]"),
                                ptrChord = Pattern.compile("[A-H]([moldurs#1-9]{0,6})"),
                                ptrSylablesBG = Pattern.compile("[�����������������]"),
                                ptrSylablesRU = Pattern.compile("[����������������������]"),
                                ptrLngRU = Pattern.compile("[����]"),
                                ptrLngEN = Pattern.compile("[AEIOUYaeiouy]"),
                                ptrEngWord = Pattern.compile("\\w+");
   
   

   private final static String URL_FLS_VMS = "falshivim-vmeste.ru";        
   private Matcher mtcText;
   
   private ArrayList<CChordsTextPair> alChordsTextPairs;
   @SuppressWarnings("rawtypes")
   private JComboBox cbxLanguage;
   private JPanel pnlLang;
   private JLabel lblLang;
   private JPanel panel_3;
   private JTextField txtURL;
   private JButton btnGet;
   private JLabel lblUrl;
   private JPanel pnlXmlButtons;
   private JButton btnSave;
   private JButton btnPreview;
   private JPanel pnlURL;
   private JPanel pnlTitle;
   private JPanel pnlAuthor;
   private RSyntaxTextArea txtXml;
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
            } 
            catch(Exception e)
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
   @SuppressWarnings({ "unchecked", "rawtypes" })
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
      
      pnlURL = new JPanel();
      panel.add(pnlURL);
      pnlURL.setLayout(new BorderLayout(0, 0));
      
      lblUrl = new JLabel("URL");
      pnlURL.add(lblUrl, BorderLayout.NORTH);
      lblUrl.setAlignmentX(Component.CENTER_ALIGNMENT);
      lblUrl.setLabelFor(txtURL);
      
      panel_3 = new JPanel();
      pnlURL.add(panel_3, BorderLayout.SOUTH);
      panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
      
      txtURL = new JTextField();
      txtURL.setText("http://www.falshivim-vmeste.ru/songs/827193600.html");
//      txtURL.setText("http://www.falshivim-vmeste.ru/songs/104457600.html");
      txtURL.setCaretPosition(0);
      panel_3.add(txtURL);
      txtURL.setColumns(10);
      
      
      btnGet = new JButton("Get");
      btnGet.addActionListener(this);
      panel_3.add(btnGet);
      
      pnlTitle = new JPanel();
      panel.add(pnlTitle);
      pnlTitle.setLayout(new BorderLayout(0, 0));
      
      lblTitle = new JLabel("Title");
      pnlTitle.add(lblTitle, BorderLayout.WEST);
      lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
      lblTitle.setLabelFor(txtTitle);
      
      txtTitle = new JTextField();
      pnlTitle.add(txtTitle, BorderLayout.SOUTH);
      txtTitle.setColumns(20);
      
      pnlAuthor = new JPanel();
      panel.add(pnlAuthor);
      pnlAuthor.setLayout(new BorderLayout(0, 0));
      
      lblAuthor = new JLabel("Author");
      pnlAuthor.add(lblAuthor, BorderLayout.WEST);
      lblAuthor.setHorizontalAlignment(SwingConstants.LEFT);
      
      txtAuthor = new JTextField();
      pnlAuthor.add(txtAuthor, BorderLayout.SOUTH);
      txtAuthor.setColumns(10);
      
      pnlLang = new JPanel();
      pnlLang.setAlignmentX(Component.LEFT_ALIGNMENT);
      panel.add(pnlLang);
      pnlLang.setLayout(new BorderLayout(0, 0));
      
      lblLang = new JLabel("Language  ");
      lblLang.setHorizontalAlignment(SwingConstants.LEFT);
      pnlLang.add(lblLang, BorderLayout.WEST);
      
      cbxLanguage = new JComboBox();
      cbxLanguage.setMaximumRowCount(3);
//      cbxLanguage.setModel(new DefaultComboBoxModel(new String[] {"English", "\u0411\u044A\u043B\u0433\u0430\u0440\u0441\u043A\u0438", "\u0420\u0443\u0441\u043A\u0438\u0439"}));
      cbxLanguage.setModel(new DefaultComboBoxModel(new String[] {"English", "���������", "������"}));
      lblLang.setLabelFor(cbxLanguage);
      pnlLang.add(cbxLanguage, BorderLayout.CENTER);
      
      container.add(pnlRawText);
            
      pnlSong = new JPanel();
      pnlRawText.add(pnlSong, BorderLayout.CENTER);
      pnlSong.setLayout(new BoxLayout(pnlSong, BoxLayout.Y_AXIS));
            
//      container.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
            
      txtSong = new JTextArea(0, 20);
      txtSong.setFont(new Font("Courier New", Font.PLAIN, 12));
      
      txtSong.getDocument().addDocumentListener( new DocumentListener() 
      {
         @Override
         public void changedUpdate(DocumentEvent arg0)
         {
            // TODO Auto-generated method stub
            
         }

         @Override
         public void insertUpdate(DocumentEvent arg0)
         {
            mtcText = ptrLngRU.matcher(txtSong.getText());
            if(mtcText.find())
            {
               cbxLanguage.setSelectedIndex(2);
               return;
            }
            mtcText = ptrLngEN.matcher(txtSong.getText());
            if(mtcText.find())
            {
               cbxLanguage.setSelectedIndex(0);
               return;
            }
            cbxLanguage.setSelectedIndex(1);
         }

         @Override
         public void removeUpdate(DocumentEvent arg0)
         {
            // TODO Auto-generated method stub
         }
      });      
      
      
      
      JScrollPane scrSong = new JScrollPane(txtSong,
                                            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      pnlSong.add(scrSong);
      
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

      txtXml = new RSyntaxTextArea(5, 20);
      txtXml.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
      txtXml.setCodeFoldingEnabled(true);
      txtXml.setAntiAliasingEnabled(true);
      RTextScrollPane sp = new RTextScrollPane(txtXml);
      sp.setFoldIndicatorEnabled(true);      
      
//      txtXml = new JTextArea(5, 20);
//
//      JScrollPane scrXml = new JScrollPane(txtXml,
//               JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//               JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      
      
      pnlXmlText = new JPanel();
      pnlXmlText.setLayout(new BorderLayout(0, 0));
      
//      pnlXmlText.add(scrXml);
      pnlXmlText.add(sp);
      
      container.add(pnlXmlText);
      
      pnlXmlButtons = new JPanel();
      pnlXmlText.add(pnlXmlButtons, BorderLayout.NORTH);
      
//      btnSave = new JButton("Save");
      pnlXmlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
      btnSave = new JButton(new ImageIcon(SongEditor.class.getResource("/icons/save.png")));
      btnSave.setMargin(new Insets(0, 0, 0, 0));
      btnSave.setToolTipText("Save");
      btnSave.addActionListener(this);
      btnSave.setEnabled(false);
      pnlXmlButtons.add(btnSave);
      
      btnPreview = new JButton(new ImageIcon(SongEditor.class.getResource("/icons/preview.png")));
      btnPreview.setEnabled(false);
      btnPreview.setToolTipText("Preview");
      btnPreview.addActionListener(this);
      btnPreview.setMargin(new Insets(0, 0, 0, 0));
      pnlXmlButtons.add(btnPreview);
      
      this.setSize(640, 480);
      this.setVisible(true);
      //===============================================================
      // For test purposes only. Remove it before release
      
      getSongFromURL(txtURL.getText());
      txtSong.setCaretPosition(0);
      
//      com.itextpdf.text.pdf.hyphenation.Hyphenator h = new com.itextpdf.text.pdf.hyphenation.Hyphenator("en", "US", 2, 2);
//      Hyphenation s = h.hyphenate("hyphenation");
//      com.itextpdf.text.pdf.hyphenation.Hyphenator h = new com.itextpdf.text.pdf.hyphenation.Hyphenator("de", "DE", 2, 2);
//      Hyphenation s = h.hyphenate("Leistungsscheinziffer");
//
//      System.out.println(s);
      
      
      
//      sSong = txtSong.getText();
//
//      // Convert string to CSong object
//      convertSongStringToObject();
//      
//      xmlSong = oSong.generateXml();
//      
//      txtXml.setText(xmlSong);
//      btnSave.setEnabled(true);
//      btnPreview.setEnabled(true);
      
      
//      Preview oPreview = new Preview();
//      JFrame frame = new JFrame ("Preview");
//      frame.setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
//      frame.getContentPane().add (new Preview());
//      frame.pack();
//      frame.setVisible (true);
//      oPreview.setSong(oSong);      
      //===============================================================
      
   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      Object oSource = e.getSource();
      
      if(oSource == btnConvert)
      {
         sSong = txtSong.getText();

         // Convert string to CSong object
         convertSongStringToObject();
         
         xmlSong = oSong.generateXml();
         
//         txtXml.setText(xmlSong);
         txtXml.setText(xmlSong);
         txtXml.setCaretPosition(0);
         btnSave.setEnabled(true);
         btnPreview.setEnabled(true);
      }
      else if(oSource == btnGet)
      {
         getSongFromURL(txtURL.getText());
      }
      else if(oSource == btnSave)
      {
         xmlSong = txtXml.getText();
         saveSongXml(xmlSong);
      }
      else if(oSource == btnPreview)
      {
         Preview oPreview = new Preview();
         JFrame frame = new JFrame ("Preview");
//         JDialog frame = new JDialog(this, "Preview", true);
//         frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
         frame.setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
         oPreview.setEnabled(false);
         frame.getContentPane().add (oPreview);
         frame.pack();
         frame.setVisible (true);
         
         oSong = new CSong(txtXml.getText());
         oPreview.setSong(oSong);
      }
   }
   
   private void saveSongXml(String xmlSong)
   {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setSelectedFile(new File(oSong.sAuthor + " - " + oSong.sTitle + ".xml"));
      FileFilter ffXml = new ExtensionFileFilter("XML", new String[] { "xml" });
      fileChooser.setFileFilter(ffXml);
      if (fileChooser.showSaveDialog(SongEditor.this) == JFileChooser.APPROVE_OPTION) 
      {
         File oFile = fileChooser.getSelectedFile();
         String sPar = oFile.getParent();
         String sFile = oFile.getName();
         if(!sFile.toLowerCase().endsWith(".xml"))
            sFile += ".xml";

         oFile = new File(sPar + "\\" + sFile);
         if(oFile.exists() && !oFile.isDirectory()) 
         {
            int dialogResult = JOptionPane.showConfirmDialog (null, "File Exists. Would You Like to Overwrite it", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.NO_OPTION)
               return;
         }         
         try
         {
            PrintWriter writer = new PrintWriter(sPar + "\\" + sFile, "UTF-8");
            writer.print(xmlSong);
            writer.close();
         
         } 
         catch(FileNotFoundException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } 
         catch(UnsupportedEncodingException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }   
   }

   private void getSongFromURL(String sURL)
   {
      if(sURL.contains(URL_FLS_VMS))
      {
         getSongFromFalshivimVmeste(sURL);
      }
      
   }

   private void getSongFromFalshivimVmeste(String sURL)
   {
      final String USER_AGENT = "Mozilla/5.0",
                   sTitleNameBgn = "<h1>",
                   sTitleNameEnd = "</h1>",
                   sTitleBgn = "������� ����� ",
                   sAuthorBgn = " (",
                   sAuthorEnd = ")",
                   sTextBgn = "<pre class=textsong>",
                   sTextEnd = "</pre>";
  
      String       sResponse;
  
      URL          oURL;
  
      BufferedReader   in; 
  
      HttpURLConnection oHTTPConn;
  
      try
      {
         oURL = new URL(sURL);
         oHTTPConn = (HttpURLConnection) oURL.openConnection();

         // optional default is GET
         oHTTPConn.setRequestMethod("GET");
     
         // add reuqest header
         oHTTPConn.setRequestProperty("User-Agent", USER_AGENT);
     
         if(oHTTPConn.getResponseCode() == 200)
         {
            in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream(), "UTF-8"));
//            in = new BufferedReader(new InputStreamReader(oHTTPConn.getInputStream()));
        
            String inputLine;
            StringBuffer sbResponse = new StringBuffer();
    
            while ((inputLine = in.readLine()) != null) 
               sbResponse.append(inputLine + "\n");
//            int inputChar;
//            while ((inputChar = in.read()) != -1)
//               sbResponse.append((char)inputChar);
//            sResponse = sbResponse.toString();
//            txtSong.setText(sResponse);            
            in.close();
        
            sResponse = sbResponse.toString();

            // Get song title and author
            int iTtlNmBgn = sResponse.indexOf(sTitleNameBgn);
            int iTtlNmEnd = sResponse.indexOf(sTitleNameEnd);
            String sTtlNm = sResponse.substring(iTtlNmBgn + sTitleNameBgn.length(), iTtlNmEnd);
            
            // Get and set song title
            int iTtlBgn = sTtlNm.indexOf(sTitleBgn);
            int iTtlEnd = sTtlNm.indexOf(sAuthorBgn);
            String sTitle = sTtlNm.substring(iTtlBgn + sTitleBgn.length(), iTtlEnd);
            txtTitle.setText(sTitle);
            txtTitle.setCaretPosition(0);
            
            // Get and set song author
            int iAthEnd = sTtlNm.indexOf(sAuthorEnd);
            String sAuthor = sTtlNm.substring(iTtlEnd + sAuthorBgn.length(), iAthEnd);
            txtAuthor.setText(sAuthor);
            txtTitle.setCaretPosition(0);
            
            // Get and set song text
            int iTxtBgn =  sResponse.indexOf(sTextBgn);
            int iTxtEnd =  sResponse.indexOf(sTextEnd);
            String sText = sResponse.substring(iTxtBgn + sTextBgn.length(), iTxtEnd);
            txtSong.setText(sText);
            txtSong.setCaretPosition(0);
         }
      } 
      catch(MalformedURLException e)
      {
         e.printStackTrace();
      } 
      catch(ProtocolException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } 
      catch(IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
  
   }

   private void convertSongStringToObject()
   {
//      sSong.
      // Split string to verses 
      getVerses();

      if(alVerses.isEmpty())
         return;
      
      oSong = new CSong();

      oSong.sTitle = txtTitle.getText();
      oSong.sAuthor = txtAuthor.getText();
      oSong.iEnuLanguage = cbxLanguage.getSelectedIndex() + 1;
      
      // Add chords and text verses to song
      for(String sVerse: alVerses)
      {
         String tsVerseLines[] = getLinesFromVerse(sVerse);

         // Remove leading spaces from the verse
         int iOffset = getVerseOffset(tsVerseLines);

         if(iOffset != 0)
         {
            for(int i = 0; i < tsVerseLines.length; i++)
               tsVerseLines[i] = tsVerseLines[i].substring(iOffset);
         }
         
         // Create chords-text pairs for the verse
         CChordsTextPair oChordsTextPair = null;
         alChordsTextPairs = new ArrayList<CChordsTextPair>();
         for(int i = 0; i < tsVerseLines.length; i++)
         {
            mtcText = ptrText.matcher(tsVerseLines[i]);
            // If the line is a chords line add it to the pair
            if(!mtcText.find())
            {
               oChordsTextPair = new CChordsTextPair();
               oChordsTextPair.sChordsLine = tsVerseLines[i];
            }
            // add the text line to the pair
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
         
         // Extract text and chords (if exist) from chords-text pairs and add them to the song
         for(CChordsTextPair oChordsTextPair2 : alChordsTextPairs)
         {
            // There are chords connected to the text line in the chords-text pair, determine their names and postion
            if(!oChordsTextPair2.sChordsLine.isEmpty())
            {
               // Different syllablization for different languages, Bulgarian and Russian use almost the same.
               // The result is a chords line with the name and position (zero based, relative to syllables 
               // in the text line) of the chords.  
               if(oSong.iEnuLanguage == CSong.ENU_LNG_EN)
               {
                  try
                  {
                     oChordsLine = getChordsLineEn(oChordsTextPair2);
                  } 
                  catch(Exception e)
                  {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                  }
               }
               else
                  oChordsLine = getChordsLineCyr(oChordsTextPair2);               
               
               // Create a new chords verse id it doesn't exists and add the chords line to it. 
               if(oChordsVerse == null)
                  oChordsVerse = new CChordsVerse();
               
               oChordsVerse.alChordsLines.add(oChordsLine);
            }
            
            // If there is a text line in chords-text pair add it to the text verse (create it if it doesn't exist).
            if(!oChordsTextPair2.sTextLine.isEmpty())
            {
               oTextLine = new CTextLine();
               oTextLine.sTextLine = oChordsTextPair2.sTextLine;
               if(oTextVerse == null)
                  oTextVerse = new CTextVerse();
               oTextVerse.alTextLines.add(oTextLine);
            }
         }
         
         // If exists chords verse assign ID and add to the song. 
         if(oChordsVerse != null)
         {
            oChordsVerse.sID = String.valueOf(oSong.alChords.size() + 1);
            oSong.alChords.add(oChordsVerse);
            
            oSong.htChordsIdNdx.put(oChordsVerse.sID, oSong.alChords.size()-1);
         }
         
         // If exists text verse assign ID and add to the song.
         if(oTextVerse != null)
         {
            if(oChordsVerse != null)
               oTextVerse.sChordsVerseID = oChordsVerse.sID;
            oSong.oText.alTextVerses.add(oTextVerse);
         }
      }
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

   CChordsLine getChordsLineCyr(CChordsTextPair oChordsTextPair)
   {
      int iSlbNdx = 1,
          iCrdBgn = 0,
          iCrdEnd = 0;
      
      Matcher   mtcText,
                mtcChords;
      
      CChord oChord;
      
      CChordsLine oChordsLine = new CChordsLine();
      
      switch(oSong.iEnuLanguage)
      {
         case CSong.ENU_LNG_BG:
            mtcText = ptrSylablesBG.matcher(oChordsTextPair.sTextLine);      
         break;
         
         default:
         case CSong.ENU_LNG_RU:
            mtcText = ptrSylablesRU.matcher(oChordsTextPair.sTextLine);      
         break;
      }
      
      mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);
      while(mtcChords.find(iCrdEnd))
      {
         iCrdBgn = mtcChords.start();
         iCrdEnd = mtcChords.end();
         
         oChord = new CChord(oChordsTextPair.sChordsLine.substring(iCrdBgn, iCrdEnd));
         
         if(iCrdBgn < oChordsTextPair.sTextLine.length())
         
         {
            while(mtcText.find())
            {            
               if(mtcText.start() >= iCrdBgn)
               {
                  oChord.iPosition = iSlbNdx++;
                  oChordsLine.addChord(oChord);
                  break;
               }
               iSlbNdx++;
            }
         }
         else
         {
            if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >=0)
               oChord.iPosition = -1;
            else 
               oChord.iPosition = oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition - 1;
            oChordsLine.addChord(oChord);
         }
      }
      
      return oChordsLine;
   }   

   static CChordsLine getChordsLineEn(CChordsTextPair oChordsTextPair) throws Exception 
   {
      int      iSlbNdx = 1,
               iBgn = 0,
               iEnd = 0,
               i = 0,
               iPos = 0,
               iNxtSlb = 0;
      
      String   sSyllable,
               tsSylables[],
               word,
               hyphenated_word;
      
      ArrayList<String> alSyllables = new ArrayList<>();
      
      Matcher   mtcText,
                mtcChords;
      
      CChord    oChord;
      
      CChordsLine oChordsLine = new CChordsLine();
      
//      Hyphenator oHyphenator = initHyphenator();
      CEnglishHyphenator oHyphenator = new CEnglishHyphenator(CEnglishHyphenator.ENU_HPN_INT_LRC_HPN);
      
      mtcText = ptrEngWord.matcher(oChordsTextPair.sTextLine);
      
      while(mtcText.find(iEnd))
      {
         iBgn = mtcText.start();
         if(iBgn != iEnd)
            alSyllables.add(oChordsTextPair.sTextLine.substring(iEnd, iBgn));
         iEnd = mtcText.end();
         
         word = oChordsTextPair.sTextLine.substring(iBgn, iEnd);

         hyphenated_word = oHyphenator.hyphenate(word);
         tsSylables = hyphenated_word.split("-");
         
         for(int j = 0; j < tsSylables.length; j++)
            alSyllables.add(tsSylables[j]);

         System.out.println(hyphenated_word); 
      }
      
      iEnd = 0;
      
      mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);
      while(mtcChords.find(iEnd))
      {
         iBgn = mtcChords.start();
         iEnd = mtcChords.end();
         
         oChord = new CChord(oChordsTextPair.sChordsLine.substring(iBgn, iEnd));
         
         if(iBgn < oChordsTextPair.sTextLine.length())
         {
            for(i = iNxtSlb; i < alSyllables.size(); i++)
            {
               sSyllable = alSyllables.get(i);
               if(sSyllable.matches("[\\W]"))
               {
                  iPos += sSyllable.length();
                  continue;
               }
               else
               {
                  if((iPos >= iBgn) || (iPos < iBgn  && iBgn < iPos + sSyllable.length()))
                  {
                     oChord.iPosition = iSlbNdx++;
                     oChordsLine.addChord(oChord);
                     iNxtSlb = i + 1;
                     iPos += sSyllable.length();
                     break;
                  }
                  iPos += sSyllable.length();
                  iSlbNdx++;
               }
            }
         }
         else
         {
            if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >=0)
               oChord.iPosition = -1;
            else 
               oChord.iPosition = oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition - 1;
            oChordsLine.addChord(oChord);
         }
      }      
      
//      hyphenated_word = oHyphenator.hyphenate("they`re");
//      System.out.println(hyphenated_word); 
      
      
      return oChordsLine;
   }   
}
