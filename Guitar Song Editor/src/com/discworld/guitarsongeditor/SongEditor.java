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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;

import javax.swing.JComboBox;

import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.awt.Font;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.*;
import org.xml.sax.SAXException;

public class SongEditor extends JFrame implements ActionListener
{
   /**
    * 
    */
   private static final long serialVersionUID = 7937537729513348982L;
   
   private JTextArea txtSong;
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
   
   private final static String PLUGIN_FOLDER = "plugins",
                               PLUGIN_SUFFIX = ".jar";      
   
   private static ArrayList<CGuitarSongPlugin> alPlugins;

   private final static Pattern ptrText = Pattern.compile("[^ A-Hmoldurs#1-9/\\t]"),
                                ptrChord = Pattern.compile("[A-H]([moldurs#1-9]{0,6})(/[A-H]([moldurs#1-9]{0,6}))?"),
//                                ptrChord = Pattern.compile("[A-H]([moldurs#1-9]{0,6})"),
//                                ptrSylablesBG = Pattern.compile("[АЪОУЕИЮЯЬаъоуеиюя]"),
                                ptrSylablesBG = Pattern.compile("[\u0410\u042a\u041e\u0423\u0415\u0418\u042e\u042f\u042c\u0430\u044a\u043e\u0443\u0435\u0438\u044e\u044f]"),
//                                ptrSylablesRU = Pattern.compile("[АЪОУЕИЮЯЬЭЫаъоуеиюяыэё]"),
                                ptrSylablesRU = Pattern.compile("[\u0410\u042a\u041e\u0423\u0415\u0418\u042e\u042f\u042c\u042d\u042b\u0430\u044a\u043e\u0443\u0435\u0438\u044e\u044f\u044b\u044d\u0451]"),
//                                ptrLngRU = Pattern.compile("[ЭЫыэ]"),
                                ptrLngRU = Pattern.compile("[\u042d\u042b\u044b\u044d]"),
                                ptrLngEN = Pattern.compile("[AEIOUYaeiouy]"),
                                ptrEngWord = Pattern.compile("\\w+");
   
   

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
//      txtURL.setText("http://abcdisk.ru/akkordi/visockii_vladimir/1222/ballada_o_borbe/");
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
      cbxLanguage.setModel(new DefaultComboBoxModel(new String[] {"English", "\u0411\u044A\u043B\u0433\u0430\u0440\u0441\u043A\u0438", "\u0420\u0443\u0441\u043A\u0438\u0439"}));
//      cbxLanguage.setModel(new DefaultComboBoxModel(new String[] {"English", "Áúëãàðñêè", "Ðóñêèé"}));
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
            if(!txtSong.getText().isEmpty())
               btnConvert.setEnabled(true);
            else
               btnConvert.setEnabled(false);
            
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
            if(txtSong.getText().isEmpty())
               btnConvert.setEnabled(false);
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
      btnConvert.setToolTipText("Convert");
      btnConvert.setEnabled(false);
      verticalBox.add(btnConvert);
      btnConvert.setAlignmentX(Component.CENTER_ALIGNMENT);
      btnConvert.addActionListener(this);

      txtXml = new RSyntaxTextArea(5, 20);
      txtXml.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
      txtXml.setCodeFoldingEnabled(true);
      txtXml.setAntiAliasingEnabled(true);
      RTextScrollPane sp = new RTextScrollPane(txtXml);
      sp.setFoldIndicatorEnabled(true);      
      
      pnlXmlText = new JPanel();
      pnlXmlText.setLayout(new BorderLayout(0, 0));
      
      pnlXmlText.add(sp);
      
      container.add(pnlXmlText);
      
      pnlXmlButtons = new JPanel();
      pnlXmlText.add(pnlXmlButtons, BorderLayout.NORTH);
      
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
      // Loading plugins
      new File(PLUGIN_FOLDER).mkdirs();
      
//      Policy.setPolicy(new PluginPolicy());
//      System.setSecurityManager(new SecurityManager());
      
      alPlugins = new ArrayList<CGuitarSongPlugin>();
      
      final File fPluginFolder = new File(System.getProperty("user.dir") + "\\" + PLUGIN_FOLDER);
      
      try
      {
         loadPlugins(fPluginFolder);
      } catch(InstantiationException | IllegalAccessException
               | ClassNotFoundException | IOException e)
      {
         e.printStackTrace();
      }      
      
      //===============================================================
      // For test purposes only. Remove it before release
      
      getSongFromURL(txtURL.getText());
      txtSong.setCaretPosition(0);

      //===============================================================
      
   }

   private static void loadPlugins(final File fPluginFolder) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
   {
      for (final File fEntry : fPluginFolder.listFiles()) 
      {
         if (fEntry.isDirectory()) 
         {
             loadPlugins(fEntry);
         } 
         else 
         {
//             System.out.println(fEntry.getName());
             
             if(!fEntry.getName().endsWith(PLUGIN_SUFFIX))
                continue;
             
             ClassLoader oClassLoader = URLClassLoader.newInstance(new URL[] { fEntry.toURI().toURL() });
             CGuitarSongPlugin oPlugin = (CGuitarSongPlugin) oClassLoader.loadClass(getClassName(fEntry.getAbsolutePath())).newInstance();
             alPlugins.add(oPlugin);
         }
      }      
   }
   
   private static String getClassName(String sFile) throws IOException
   {
      String sClassName = "";
      ZipInputStream zip = new ZipInputStream(new FileInputStream(sFile));
      for(ZipEntry entry = zip.getNextEntry(); entry!=null && sClassName.isEmpty(); entry=zip.getNextEntry())
      {
         if(entry.getName().endsWith(".class") && !entry.isDirectory()) 
         {
            // This ZipEntry represents a class. Now, what class does it represent?
            StringBuilder className=new StringBuilder();
            for(String part : entry.getName().split("/")) 
            {
               if(className.length() != 0)
                  className.append(".");
                  className.append(part);
               if(part.endsWith(".class"))
                  className.setLength(className.length()-".class".length());
            }
            sClassName = className.toString();
         }
      }
      zip.close();
      return sClassName;
   }   
   
   @Override
   public void actionPerformed(ActionEvent e)
   {
      Object oSource = e.getSource();
      
      if(oSource == btnConvert)
      {
         sSong = txtSong.getText();
         if(sSong.isEmpty())
            return;

         // Convert string to CSong object
         convertSongStringToObject();

//         xmlSong = songToXml(oSong);
//         txtXml.setText(xmlSong);
         txtXml.setText(oSong.toXml2());
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
//         saveSongXml(txtXml.getText());
         
         xmlSong = txtXml.getText();
         try
         {
            CSong.validate(xmlSong);
            saveSongXml(xmlSong);
         } 
         catch(SAXException ex)
         {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Song Validation Error", JOptionPane.PLAIN_MESSAGE);
         }
         
      }
      else if(oSource == btnPreview)
      {
         xmlSong = txtXml.getText();
         try
         {
            CSong.validate(xmlSong);
            
            Preview oPreview = new Preview();
            JFrame frame = new JFrame ("Preview");
//            JDialog frame = new JDialog(this, "Preview", true);
//            frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            frame.setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);
            oPreview.setEnabled(false);
            frame.getContentPane().add (oPreview);
            frame.pack();
            frame.setVisible (true);
            
//            oSong = new CSong(txtXml.getText());
//            oSong = xmlToSong(xmlSong);
            oSong = new CSong(xmlSong, 1);
            
            oPreview.setSong(oSong);
         } 
         catch(SAXException ex)
         {
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Song Validation Error", JOptionPane.PLAIN_MESSAGE);
         }
         
         
      }
   }
   
   private String songToXml(CSong oSong)
   {
      String xmlSong = "";
      try
      {
         JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class);
//       JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class, CChordsVerse.class, CChordsLine.class, CChord.class);

         Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

         jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

         StringWriter sw = new StringWriter();
       
         jaxbMarshaller.marshal(oSong, sw);
         xmlSong = sw.toString();     
      } 
      catch(JAXBException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return xmlSong;
   }
   
   private CSong xmlToSong(String xmlSong)
   {
      CSong oSong = null;
      StringReader sr = new StringReader(xmlSong);
      JAXBContext jaxbContext1;
      try
      {
         jaxbContext1 = JAXBContext.newInstance(CSong.class);
         Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
         oSong = (CSong)jaxbUnmarshaller.unmarshal(sr);
         
      } catch(JAXBException e)
      {
         e.printStackTrace();
      }
      
      return oSong;
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
            exportToXml(xmlSong);
         
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
      for(CGuitarSongPlugin oPlugin: alPlugins)
      {
         if(sURL.contains(oPlugin.getDomainName()))
         {
            oPlugin.getSongFromURL(sURL);
            
            txtTitle.setText(oPlugin.getTitle());
            txtAuthor.setText(oPlugin.getAuthor());
            txtSong.setText(oPlugin.getSong());
            txtTitle.setCaretPosition(0);
            txtSong.setCaretPosition(0);
         }
      }      
   }

   private void convertSongStringToObject()
   {
      int iTextVerseNdx = 0;
      
      sSong = sSong.replaceAll("( *\n){3,}", "\n\n").replaceAll("\n \n", "\n\n");
      
      // Split string to verses 
      getVerses();

      if(alVerses.isEmpty())
         return;
      
      oSong = new CSong();

      oSong.sTitle = txtTitle.getText();
      oSong.sAuthor = txtAuthor.getText();
//      oSong.iEnuLanguage = cbxLanguage.getSelectedIndex() + 1;
//      oSong.setLanguage(cbxLanguage.getSelectedIndex() + 1);
      oSong.sLanguage = CSong.LANG[cbxLanguage.getSelectedIndex()];
      
      boolean bHasChords;
      
      // Add chords and text verses to song
      for(String sVerse: alVerses)
      {
         bHasChords = false;
         
         String tsVerseLines[] = getLinesFromVerse(sVerse);

         // Remove leading spaces from the verse
         int iOffset = getVerseLeadingSpace(tsVerseLines);

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
            if(!mtcText.find())
            {
               // Chords. Create a new pair and add it to the pair
               if(oChordsTextPair != null && !oChordsTextPair.sChordsLine.isEmpty() && oChordsTextPair.sTextLine.isEmpty())
                  alChordsTextPairs.add(oChordsTextPair);
               oChordsTextPair = new CChordsTextPair();
               oChordsTextPair.sChordsLine = tsVerseLines[i];
               bHasChords = true;
            }
            else
            {
               // Text. Add the text line to the pair
               if(oChordsTextPair == null)
                  oChordsTextPair = new CChordsTextPair();
               oChordsTextPair.sTextLine = tsVerseLines[i];
               alChordsTextPairs.add(oChordsTextPair);
               oChordsTextPair = null;
            }
         }
         if(oChordsTextPair != null)
            alChordsTextPairs.add(oChordsTextPair);
         
//         CTextVerse oTextVerse = null;
         CTextVerse oTextVerse = new CTextVerse();
         CTextLine oTextLine = null;
         CChordsLine oChordsLine = null;
         CChordsVerse oChordsVerse = null;
         
         
         // Extract text and chords (if exist) from chords-text pairs and add them to the song
         for(CChordsTextPair oChordsTextPair2 : alChordsTextPairs)
         {
            // There are chords connected to the text line in the chords-text pair, determine their names and postion
            if(!oChordsTextPair2.sChordsLine.isEmpty())
            {
               if(!oChordsTextPair2.sTextLine.isEmpty())
               {
                  // There is a text line in the pair
                  
                  // Different syllablization for different languages, Bulgarian and Russian use almost the same.
                  // The result is a chords line with the name and position (zero based, relative to syllables 
                  // in the text line) of the chords.  
//                  if(oSong.iEnuLanguage == CSong.ENU_LNG_EN)
                  if(oSong.sLanguage.equals(CSong.LANG_EN))
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
               }
               else
               {
                  oChordsLine = getChordsLine(oChordsTextPair2);
               }
               
               
               // Create a new chords verse id it doesn't exists and add the chords line to it. 
               if(oChordsVerse == null)
                  oChordsVerse = new CChordsVerse();
               
               oChordsVerse.addChordLine(oChordsLine);
            }
            else if(oChordsTextPair2.sChordsLine.isEmpty() && bHasChords)
            {
               // There are chords in the verse but this line chords line is empty 
//               oChordsVerse.alChordsLines.add(new CChordsLine());
               oChordsVerse.add(new CChordsLine());
            }
            
            // If there is a text line in chords-text pair add it to the text verse (create it if it doesn't exist).
            if(!oChordsTextPair2.sTextLine.isEmpty())
            {
               oTextLine = new CTextLine();
               oTextLine.sTextLine = oChordsTextPair2.sTextLine;
//               if(oTextVerse == null)
//                  oTextVerse = new CTextVerse();
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
            else
               oTextVerse.sChordsVerseID = getChordVerseID(iTextVerseNdx, oTextVerse.size());
//            oSong.oText.alTextVerses.add(oTextVerse);
            if(oTextVerse.alTextLines.size() != 0)
               iTextVerseNdx++;
            oSong.oText.add(oTextVerse);
         }
      }
   }
   
   private String getChordVerseID(int iTextVerseNdx, int iTextVerseSize)
   {
      
      ArrayList<CChordsVerse> alChordsVerses = getTextRelatedChordsVerses();
      
      int iChordsVersNdx = iTextVerseNdx - ((int)iTextVerseNdx / alChordsVerses.size())*alChordsVerses.size();
//      if(alChordsVerses.get(iChordsVersNdx).alChordsLines.size() == iTextVerseSize)
      if(alChordsVerses.get(iChordsVersNdx).size() == iTextVerseSize)
         return alChordsVerses.get(iChordsVersNdx).sID;
      else
         return "";


//      for(CChordsVerse oChordsVerse: alChordsVerses)
//      {
//         iChordsVersNbr++;
//         if((iTextVerseNbr % iChordsVersNbr == 0) && oChordsVerse.alChordsLines.size() == iTextVerseSize)
//            return oChordsVerse.sID;
//      }
      
   }

   private ArrayList<CChordsVerse> getTextRelatedChordsVerses()
   {
      ArrayList<CChordsVerse> alTextRelatedChordsVerses = new ArrayList<CChordsVerse>();
      for(CChordsVerse oChordsVerse : oSong.alChords)
      {
         if(isTextRelatedChordsverse(oChordsVerse))
            alTextRelatedChordsVerses.add(oChordsVerse);
      }
      return alTextRelatedChordsVerses;
   }

   private boolean isTextRelatedChordsverse(CChordsVerse oChordsVerse)
   {
//      for(CChordsLine oChordsLine: oChordsVerse.alChordsLines)
      for(CChordsLine oChordsLine: oChordsVerse)
      {
//         for(CChord oChord: oChordsLine.alChords)
         for(CChord oChord: oChordsLine)
         {
            if(oChord.iPosition > 0)
               return true;
         }
      }
      
      return false;
   }

   private int getVerseLeadingSpace(String[] tsVerseLines)
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
      
//      switch(oSong.iEnuLanguage)
//      {
//         case CSong.ENU_LNG_BG:
//            mtcText = ptrSylablesBG.matcher(oChordsTextPair.sTextLine);      
//         break;
//         
//         default:
//         case CSong.ENU_LNG_RU:
//            mtcText = ptrSylablesRU.matcher(oChordsTextPair.sTextLine);      
//         break;
//      }

      switch(oSong.sLanguage)
      {
         case CSong.LANG_BG:
            mtcText = ptrSylablesBG.matcher(oChordsTextPair.sTextLine);      
         break;
         
         default:
         case CSong.LANG_RU:
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
//            if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >=0)
            if(oChordsLine.isEmpty() || oChordsLine.get(oChordsLine.size()-1).iPosition >=0)
               oChord.iPosition = -1;
            else 
//               oChord.iPosition = oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition - 1;
               oChord.iPosition = oChordsLine.get(oChordsLine.size()-1).iPosition - 1;
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
//            if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >=0)
            if(oChordsLine.isEmpty() || oChordsLine.get(oChordsLine.size()-1).iPosition >=0)
               oChord.iPosition = -1;
            else 
               oChord.iPosition = oChordsLine.get(oChordsLine.size()-1).iPosition - 1;
//               oChord.iPosition = oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition - 1;
            oChordsLine.addChord(oChord);
         }
      }      
      
//      hyphenated_word = oHyphenator.hyphenate("they`re");
//      System.out.println(hyphenated_word); 
      
      
      return oChordsLine;
   }
   
   private static CChordsLine getChordsLine(CChordsTextPair oChordsTextPair)
   {
      int      iCrdBgn = 0,
               iCrdEnd = 0;
           
      Matcher   mtcChords;
     
     CChord oChord;
     
     CChordsLine oChordsLine = new CChordsLine();
     
     mtcChords = ptrChord.matcher(oChordsTextPair.sChordsLine);

     while(mtcChords.find(iCrdEnd))
     {
        iCrdBgn = mtcChords.start();
        iCrdEnd = mtcChords.end();
        
        oChord = new CChord(oChordsTextPair.sChordsLine.substring(iCrdBgn, iCrdEnd));
        
//        if(oChordsLine.alChords.isEmpty() || oChordsLine.alChords.get(oChordsLine.alChords.size()-1).iPosition >= 0)
        if(oChordsLine.isEmpty() || oChordsLine.get(oChordsLine.size()-1).iPosition >= 0)
           oChord.iPosition = -1;
        else 
           oChord.iPosition = oChordsLine.get(oChordsLine.size()-1).iPosition - 1;
        oChordsLine.addChord(oChord);
     }
     
     return oChordsLine;
   }


   private void exportToXml(String xmlSong)
   {
      try 
      {
         File file = new File(xmlSong);
         JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class);
//         JAXBContext jaxbContext = JAXBContext.newInstance(CSong.class, CChordsVerse.class, CChordsLine.class, CChord.class);
         Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
   
//         JABXList<CFile> Files = new JABXList<CFile>(vFilesDwn);
         
         // output pretty printed
         jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
   
//         String s;
//         jaxbMarshaller.marshal(oSong, file);
         StringWriter sw = new StringWriter();
         
         jaxbMarshaller.marshal(oSong, sw);
         System.out.print(sw.toString());
         
         System.out.println("unmarshall");
         StringReader sr = new StringReader(sw.toString());
         JAXBContext jaxbContext1 = JAXBContext.newInstance(CSong.class);
         Unmarshaller jaxbUnmarshaller = jaxbContext1.createUnmarshaller();
         CSong oSongnew = (CSong)jaxbUnmarshaller.unmarshal(sr);
         int a = 1;
         a++;
         
   
      } 
      catch (JAXBException e) 
      {
         e.printStackTrace();
      }      
   }
}
