package com.discworld.guitarsongeditor;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import com.discworld.guitarsonglib.CChordsTextPair;
import com.discworld.guitarsonglib.CChordsTextPairVerse;
import com.discworld.guitarsonglib.CSong;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.DefaultFontMapper.BaseFontParameters;
import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import rtf.AdvancedRTFEditorKit;
import java.awt.Font;

//public class Preview extends JPanel implements ActionListener, ISetSong
public class Preview extends JPanel implements ActionListener, ISetSong
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   int inch = Toolkit.getDefaultToolkit().getScreenResolution();
   private JButton   btnXptRTF,
                     btnXptPDF,
                     btnPrint;
   float pixelToPoint = (float) 72 / (float) inch;
   private JTextPane tpSong;
   
   private CSong oSong;
   
   AdvancedRTFEditorKit kit1;
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
      tpSong = new JTextPane();
      tpSong.setFont(new Font("Courier New", Font.PLAIN, 14));
//      kit1 = new AdvancedRTFEditorKit();      
//      epSong.setEditorKit(kit1);
      
//      epSong = new JEditorPane();
      JScrollPane scrollPane = new JScrollPane(tpSong);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(250, 145));
      scrollPane.setMinimumSize(new Dimension(10, 10));
      pnlSong.add(scrollPane);
      
//      epSong.setText("test <b>TEST</b> <h1>HHHH</H1>");
      tpSong.setContentType("text/rtf");
//      tpSong.setText("{\\rtf1 \\fs36\\qc HelloWorld! \\par\\fs24\\qr {\\i This} is formatted {\\b\\i Text}\\par Спасибо!.} ");
   }

//   @Override
   public void setSong(CSong oSong)
   {
      ArrayList<CChordsTextPairVerse> alChordsTextVerses; 
      this.oSong = oSong;
      
      alChordsTextVerses = oSong.getChordsTextVerses();
      
//      tpSong.setText("{\\rtf1 \\fs36\\qc " + oSong.sAuthor + " - " + oSong.sTitle + "} ");
      String sSong = "{\\rtf1 \\fs32\\qc\\b " + oSong.sAuthor + " - " + oSong.sTitle + "\\par\\fs28\\ql\\b0 "; 
      
      for(CChordsTextPairVerse oChordsTextPairVerse: alChordsTextVerses)
      {
         for(CChordsTextPair oChordsTextPair: oChordsTextPairVerse.alChordsTextPairs)
         {
            if(!oChordsTextPair.sChordsLine.isEmpty())
               sSong += "\\par " + oChordsTextPair.sChordsLine;
            sSong += "\\par " + oChordsTextPair.sTextLine;
         }
         sSong += "\\par ";
      }
      
      sSong += "} ";
      
      tpSong.setText(sSong);
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
//      tpSong.setBounds(0, 0, (int) convertToPixels((int)PageSize.A4.getWidth() - 58), (int) convertToPixels((int)PageSize.A4.getHeight() - 60));

//      MessageFormat headerFormat = new MessageFormat("Your header here - {0}");  //sets the page number
//      MessageFormat footerFormat = new MessageFormat("Your footer here");

      PrintRequestAttributeSet attr_set = new HashPrintRequestAttributeSet();
      attr_set.add(MediaSizeName.ISO_A4);
      attr_set.add(Sides.DUPLEX);      
      
      try
      {
//         tpSong.print(headerFormat, footerFormat, true, null, attr_set, true);
         tpSong.print(null, null, true, null, attr_set, true);
      } catch(PrinterException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
//      PrintSupport.printComponent(tpSong);//txtPrintArea is the name of your JTextArea.
   }

   private void exportToPdf() throws DocumentException, IOException
   {
//      final int iPageWidth = 612,
//                iPageHeight = 792;

//      A4 = 8.27x11.69" x72points/inch = 595x842 points
//      final int iPageWidth = 595,
//               iPageHeight = 842;

      if (tpSong.getDocument().getLength() > 0)
      {
         File oFile = getFile("PDF", "pdf");
         
         if(oFile == null)
            return;
         
//         tpSong.setBounds(0, 0, (int) convertToPixels(iPageWidth - 58), (int) convertToPixels(iPageHeight - 60));
         tpSong.setBounds(0, 0, (int) convertToPixels((int)PageSize.A4.getWidth() - 58), (int) convertToPixels((int)PageSize.A4.getHeight() - 60));

         com.itextpdf.text.Document oDocument = new com.itextpdf.text.Document();
         FileOutputStream oFileOutputStream = new FileOutputStream(oFile);
         PdfWriter oPdfWriter = PdfWriter.getInstance(oDocument, oFileOutputStream);

//         oDocument.setPageSize(new com.itextpdf.text.Rectangle(iPageWidth, iPageHeight));
         oDocument.setPageSize(PageSize.A4);
         oDocument.open();
         PdfContentByte oPdfContentByte = oPdfWriter.getDirectContent();

         oPdfContentByte.saveState();
         oPdfContentByte.concatCTM(1, 0, 0, 1, 0, 0);

         DefaultFontMapper oDefaultFontMapper = new DefaultFontMapper();
         oDefaultFontMapper.insertDirectory("c:\\windows\\fonts");

         BaseFontParameters oBaseFontParameters = new BaseFontParameters("c:\\windows\\fonts\\cour.ttf");
         oBaseFontParameters.encoding = BaseFont.IDENTITY_H;
         oDefaultFontMapper.putName("Courier New", oBaseFontParameters );      
         

         Graphics2D oGraphics2D = oPdfContentByte.createGraphics(PageSize.A4.getWidth(), PageSize.A4.getHeight(), oDefaultFontMapper, true, .95f);

         java.awt.geom.AffineTransform oAffineTransform = new java.awt.geom.AffineTransform();
         oAffineTransform.translate(convertToPixels(20), convertToPixels(20));
         oAffineTransform.scale(pixelToPoint, pixelToPoint);

         oGraphics2D.transform(oAffineTransform);

         oGraphics2D.setColor(Color.WHITE);
         oGraphics2D.fill(tpSong.getBounds());

         java.awt.Rectangle oRectangle = getVisibleEditorRect(tpSong);
         tpSong.getUI().getRootView(tpSong).paint(oGraphics2D, oRectangle);

//         g2.setColor(Color.BLACK);
//         g2.setColor(Color.WHITE);
//         g2.draw(epSong.getBounds());

         oGraphics2D.dispose();
         oPdfContentByte.restoreState();
         oDocument.close();
         oFileOutputStream.flush();
         oFileOutputStream.close();         
      }
      

   }
   
   protected java.awt.Rectangle getVisibleEditorRect(JTextPane ta) {
      java.awt.Rectangle alloc = tpSong.getBounds();
      if ((alloc.width > 0) && (alloc.height > 0)) {
        alloc.x = alloc.y = 0;
        Insets insets = ta.getInsets();
        alloc.x += insets.left;
        alloc.y += insets.top;
        alloc.width -= insets.left + insets.right;
        alloc.height -= insets.top + insets.bottom;
        return alloc;
      }
      return null;
    }   
   
   public float convertToPixels(int points) {
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
         
//         final StringWriter out = new StringWriter();
         final ByteArrayOutputStream out = new ByteArrayOutputStream();
         Document doc = tpSong.getDocument();
//         AdvancedRTFEditorKit kit = new AdvancedRTFEditorKit();
         RTFEditorKit kit = new RTFEditorKit();
         kit.write(out, doc, doc.getStartPosition().getOffset(), doc.getLength());
         out.close();

         String rtfContent = out.toString();
         {
           // replace "Monospaced" by a well-known monospace font
           rtfContent = rtfContent.replaceAll("Monospaced", "Courier New");
           final StringBuffer rtfContentBuffer = new StringBuffer(rtfContent);
           final int endProlog = rtfContentBuffer.indexOf("\n\n");
           if(endProlog > -1)
           {
              // set a good Line Space and no Space Before or Space After each paragraph
              rtfContentBuffer.insert(endProlog, "\n\\sl240");
              rtfContentBuffer.insert(endProlog, "\n\\sb0\\sa0");
              rtfContent = rtfContentBuffer.toString();
           }
         }

         final FileOutputStream fos = new FileOutputStream(oFile);
         fos.write(rtfContent.toString().getBytes());
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
