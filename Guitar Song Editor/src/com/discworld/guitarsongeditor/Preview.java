package com.discworld.guitarsongeditor;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.BoxLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

public class Preview extends JPanel implements ActionListener
{
   private JButton   btnXptRTF,
                     btnXptPDF,
                     btnPrint;
   
   private JTextPane epSong;
   
   public Preview() 
   {
      setLayout(new BorderLayout(0, 0));
      
      JPanel pnlButtons = new JPanel();
      add(pnlButtons, BorderLayout.NORTH);
      pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
      
      btnXptRTF = new JButton("RTF");
      btnXptRTF.addActionListener(this);
      pnlButtons.add(btnXptRTF);
      
      btnXptPDF = new JButton("PDF");
      btnXptPDF.addActionListener(this);
      pnlButtons.add(btnXptPDF);
      
      btnPrint = new JButton("Print");
      btnPrint.addActionListener(this);
      pnlButtons.add(btnPrint);
      
      JPanel pnlSong = new JPanel();
      add(pnlSong, BorderLayout.CENTER);
      pnlSong.setLayout(new BoxLayout(pnlSong, BoxLayout.X_AXIS));
      
      
//      epSong = new JTextPane("text/html", "");
      epSong = new JTextPane();
//      epSong = new JEditorPane();
      JScrollPane scrollPane = new JScrollPane(epSong);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(250, 145));
      scrollPane.setMinimumSize(new Dimension(10, 10));
      pnlSong.add(scrollPane);
      
//      epSong.setText("test <b>TEST</b> <h1>HHHH</H1>");
      epSong.setContentType("text/rtf");
      epSong.setText("{\\rtf1 \\qc\\fs36HelloWorld! \\par\\fs24\\ql {\\i This} is formatted {\\b\\i Text}.}");

   }

   @Override
   public void actionPerformed(ActionEvent e)
   {
      Object oSource = e.getSource();
      
      if(oSource == btnXptRTF)
      {
         try
         {
            exportToRtf();
         } catch(IOException | BadLocationException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
      }
      else if(oSource == btnXptPDF)
      {
         
      }
      else if(oSource == btnPrint)
      {
         
         
      }
   }

   private void exportToRtf() throws IOException, BadLocationException
   {
//      final StringWriter out = new StringWriter();
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Document doc = epSong.getDocument();
      RTFEditorKit kit = new RTFEditorKit();
      kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());
      out.close();

      String rtfContent = out.toString();
      {
         // replace "Monospaced" by a well-known monospace font
         rtfContent = rtfContent.replaceAll("Monospaced", "Courier New");
         final StringBuffer rtfContentBuffer = new StringBuffer(rtfContent);
         final int endProlog = rtfContentBuffer.indexOf("\n\n");
         // set a good Line Space and no Space Before or Space After each paragraph
         rtfContentBuffer.insert(endProlog, "\n\\sl240");
         rtfContentBuffer.insert(endProlog, "\n\\sb0\\sa0");
         rtfContent = rtfContentBuffer.toString();
      }

      final File file = new File("C:\\Temp\\test.rtf");
      final FileOutputStream fos = new FileOutputStream(file);
      fos.write(rtfContent.toString().getBytes());
      fos.close();
      
//      String s = epSong.getText();
//      if (epSong.getText().length() > 0)
//      {
//
//      JFileChooser chooser = new JFileChooser();
//      chooser.setMultiSelectionEnabled(false);
//
//      int option = chooser.showSaveDialog(Preview.this);
//
//      if (option == JFileChooser.APPROVE_OPTION) {
//
//          StyledDocument doc = (StyledDocument)epSong.getDocument();
//
//          HTMLEditorKit kit = new HTMLEditorKit();
//
//          BufferedOutputStream out;
//
//          try {
//              out = new BufferedOutputStream(new FileOutputStream(chooser.getSelectedFile().getAbsoluteFile()));
//
//              kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());
//              out.close();
//
//          } catch (FileNotFoundException e) {
//
//          } catch (IOException e){
//
//          } catch (BadLocationException e){
//
//          }
//      }
//  }      
   }
}
