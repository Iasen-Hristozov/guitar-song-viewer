package com.esd.songeditor;

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

import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.JLabel;

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
      
      container.add(pnlRawText);
            
      panel_1 = new JPanel();
      pnlRawText.add(panel_1, BorderLayout.CENTER);
      panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
            
//      container.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
            
      txtSong = new JTextArea(0, 20);
            
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
//      sSong.
      getVerses();
      
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
      
      int a = 1;
   }

}
