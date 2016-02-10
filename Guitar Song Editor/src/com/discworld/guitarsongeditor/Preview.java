package com.discworld.guitarsongeditor;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.discworld.guitarsonglib.CChordsTextPair;
import com.discworld.guitarsonglib.CChordsTextPairVerse;
import com.discworld.guitarsonglib.CSong;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class Preview extends JPanel implements ActionListener
{
   /**
    * 
    */
   private static final long serialVersionUID = -5389035716855314628L;
   private final static byte PAGE_LINE_NBR = 40;
   
   int inch = Toolkit.getDefaultToolkit().getScreenResolution();
   private JButton   btnXptRTF,
                     btnXptPDF,
                     btnPrint;
   float pixelToPoint = (float) 72 / (float) inch;
   private JTextPane tpSong;
   
   private CSong oSong;
   
   String sRtf;
   public Preview() 
   {
      setLayout(new BorderLayout(0, 0));
      
      JPanel pnlButtons = new JPanel();
      add(pnlButtons, BorderLayout.NORTH);
      pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
      
//      btnXptRTF = new JButton("RTF");
      btnXptRTF = new JButton(new ImageIcon(SongEditor.class.getResource("/icons/rtf-32.png")));
      btnXptRTF.setMargin(new Insets(0, 0, 0, 0));
      btnXptRTF.setToolTipText("Export to RTF");
      btnXptRTF.addActionListener(this);
      pnlButtons.add(btnXptRTF);
      
//      btnXptPDF = new JButton("PDF");
      btnXptPDF = new JButton(new ImageIcon(SongEditor.class.getResource("/icons/pdf-32.png")));
      btnXptPDF.setMargin(new Insets(0, 0, 0, 0));
      btnXptPDF.setToolTipText("Export to PDF");
      btnXptPDF.addActionListener(this);
      pnlButtons.add(btnXptPDF);
      
//      btnPrint = new JButton("Print");
      btnPrint = new JButton(new ImageIcon(SongEditor.class.getResource("/icons/prn-32.png")));
      btnPrint.setMargin(new Insets(0, 0, 0, 0));
      btnPrint.setToolTipText("Print");
      btnPrint.addActionListener(this);
      pnlButtons.add(btnPrint);
      
      JPanel pnlSong = new JPanel();
      add(pnlSong, BorderLayout.CENTER);
      pnlSong.setLayout(new BoxLayout(pnlSong, BoxLayout.X_AXIS));
      
      
//      epSong = new JTextPane("text/html", "");
      tpSong = new JTextPane();
      tpSong.setEditable(false);
      tpSong.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 14));

      JScrollPane scrollPane = new JScrollPane(tpSong);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(640, 480));
      scrollPane.setMinimumSize(new Dimension(250, 145));
      pnlSong.add(scrollPane);
      
//      epSong.setText("test <b>TEST</b> <h1>HHHH</H1>");
      tpSong.setContentType("text/rtf");
//      tpSong.setText("{\\rtf1 \\fs36\\qc HelloWorld! \\par\\fs24\\qr {\\i This} is formatted {\\b\\i Text}\\par �������!.} ");
   }

   public void setSong(CSong oSong)
   {
      ArrayList<CChordsTextPairVerse> alChordsTextVerses; 
      this.oSong = oSong;
      
      alChordsTextVerses = oSong.getChordsTextVerses();
      
      String sSong = "{\\rtf1 \\fs32\\qc\\b " + oSong.sAuthor + " - " + oSong.sTitle + "\\par\\fs28\\ql\\b0\\par "; 
      
      int iLnsNbr = 2;
      
      for(CChordsTextPairVerse oChordsTextPairVerse: alChordsTextVerses)
      {
         iLnsNbr += oChordsTextPairVerse.size() + 1;
         if(iLnsNbr > PAGE_LINE_NBR)
         {
            sSong += (char)12;
//            sSong += "\\page";
            
            iLnsNbr = oChordsTextPairVerse.size() + 1;
         }
         for(CChordsTextPair oChordsTextPair: oChordsTextPairVerse.alChordsTextPairs)
         {
            if(!oChordsTextPair.sChordsLine.isEmpty())
               sSong += oChordsTextPair.sChordsLine + "\\par ";
//               sSong += "\\par " + oChordsTextPair.sChordsLine;
            if(!oChordsTextPair.sTextLine.isEmpty())
               sSong += oChordsTextPair.sTextLine + "\\par ";
//               sSong += "\\par " + oChordsTextPair.sTextLine;
         }
         sSong += "\\par ";
      }
      
      sSong += "} ";
      
      tpSong.setText(sSong);
      sRtf = sSong;
      
      tpSong.setCaretPosition(0);
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
         try
         {
            exportToPdf();
         } catch(FileNotFoundException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } catch(DocumentException e1)
         {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } catch(IOException e2)
         {
            // TODO Auto-generated catch block
            e2.printStackTrace();
         }
         
      }
      else if(oSource == btnPrint)
      {
         printSong();
      }
   }

   private void printSong()
   {
      PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();
      attr_set.add(MediaSizeName.ISO_A4);
      attr_set.add(Sides.DUPLEX);      
      
      try
      {
         tpSong.print(null, null, true, null, attr_set, true);
      } 
      catch(PrinterException e)
      {
         e.printStackTrace();
      }
   }

   private void exportToPdf() throws DocumentException, IOException
   {
      if (tpSong.getDocument().getLength() > 0)
      {
         File oFile = getFile("PDF", "pdf");
         
         if(oFile == null)
            return;
         
         tpSong.setBounds(0, 0, (int) convertToPixels((int)PageSize.A4.getWidth() - 58), (int) convertToPixels((int)PageSize.A4.getHeight() - 60));

         com.itextpdf.text.Document oDocument = new com.itextpdf.text.Document();
         FileOutputStream oFileOutputStream = new FileOutputStream(oFile);

         PdfWriter.getInstance(oDocument, oFileOutputStream);

         oDocument.setPageSize(PageSize.A4);
         oDocument.open();
         
         BaseFont bfTitle = BaseFont.createFont( "c:/windows/fonts/courbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
         BaseFont bfText = BaseFont.createFont( "c:/windows/fonts/cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        
         Font fntTitle = new Font(bfTitle, 16);
         Font fntText = new Font(bfText, 14);
         
         final int iLnsNbr = 35;
         int iLns = 1;
         Paragraph p = new Paragraph(oSong.sAuthor + " - " + oSong.sTitle, fntTitle);
         p.setAlignment(Paragraph.ALIGN_CENTER);
         oDocument.add(p);
         String s = "";
         
         ArrayList<CChordsTextPairVerse> alChordsTextPairVersesSet = oSong.getChordsTextVerses();
         
         for(CChordsTextPairVerse oChordsTextPairVerse : alChordsTextPairVersesSet)
         {
            iLns += oChordsTextPairVerse.size() + 1;
            if(iLns > iLnsNbr)
            {
               p = new Paragraph(s, fntText);
               oDocument.add(p);
               oDocument.newPage();
               s = oChordsTextPairVerse.toString();
               iLns = oChordsTextPairVerse.size();
            }
            else
            {
               s += "\n\n" + oChordsTextPairVerse.toString(); 
            }
         }

         p = new Paragraph(s, fntText);
         oDocument.add(p);

         oDocument.close();
         oFileOutputStream.flush();
         oFileOutputStream.close();         
      }
   }
   
   public float convertToPixels(int points) 
   {
      return (float) (points / pixelToPoint);
   }
   
   private void exportToRtf() throws IOException, BadLocationException
   {
      if (tpSong.getDocument().getLength() > 0)
      {
         JFileChooser oFileChooser = new JFileChooser();
         ExtensionFileFilter ffRTF = new ExtensionFileFilter("RichText Format", new String[] { "rtf" });
         oFileChooser.setFileFilter(ffRTF);
         oFileChooser.setMultiSelectionEnabled(false);

         File oFile = getFile("RichText Format", "rtf");
         
         if(oFile == null)
            return;

         Document doc = tpSong.getDocument();
         
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         tpSong.getEditorKit().write(baos, doc, 0, doc.getLength());
         
         final FileOutputStream fos = new FileOutputStream(oFile);

         String s = baos.toString();
         s = s.replaceAll("\\\\b\\\\", "\\\\b\\\\qc\\\\").replaceAll("\\x0C", "\\\\page");
         fos.write(s.getBytes());
       
         baos.close();
         fos.close();
      }      
   }
   
   private File getFile(String sFileType, String sFileExtension)
   {
      File oFile = null;
      JFileChooser oFileChooser = new JFileChooser();
      oFileChooser.setSelectedFile(new File(oSong.sAuthor + " - " + oSong.sTitle + "." + sFileExtension));
      ExtensionFileFilter oExtensionFileFilter = new ExtensionFileFilter(sFileType, new String[] { sFileExtension });
      oFileChooser.setFileFilter(oExtensionFileFilter);
      oFileChooser.setMultiSelectionEnabled(false);

      if (oFileChooser.showSaveDialog(Preview.this) == JFileChooser.APPROVE_OPTION) 
      {
         oFile = oFileChooser.getSelectedFile();
         String sPar = oFile.getParent();
         String sFile = oFile.getName();
         if(!sFile.toLowerCase().endsWith("." + sFileExtension))
            sFile += "." + sFileExtension;
         oFile = new File(sPar + "\\" + sFile);

         // 
         if(oFile.exists() && !oFile.isDirectory()) 
         {
            int dialogResult = JOptionPane.showConfirmDialog (null, "File Exists. Would You Like to Overwrite it", "Warning", JOptionPane.YES_NO_OPTION);
            if(dialogResult == JOptionPane.NO_OPTION)
               return null;
         }                
      }  
         
      return oFile;
   }
}
