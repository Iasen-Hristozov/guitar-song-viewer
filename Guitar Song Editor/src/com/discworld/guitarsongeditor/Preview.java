package com.discworld.guitarsongeditor;

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
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.BoxLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.DefaultFontMapper.BaseFontParameters;
import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import rtf.AdvancedRTFEditorKit;
import java.awt.Font;

public class Preview extends JPanel implements ActionListener
{
   int inch = Toolkit.getDefaultToolkit().getScreenResolution();
   private JButton   btnXptRTF,
                     btnXptPDF,
                     btnPrint;
   float pixelToPoint = (float) 72 / (float) inch;
   private JTextPane epSong;
   
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
      epSong = new JTextPane();
      epSong.setFont(new Font("Courier New", Font.PLAIN, 11));
//      kit1 = new AdvancedRTFEditorKit();      
//      epSong.setEditorKit(kit1);
      
//      epSong = new JEditorPane();
      JScrollPane scrollPane = new JScrollPane(epSong);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setPreferredSize(new Dimension(250, 145));
      scrollPane.setMinimumSize(new Dimension(10, 10));
      pnlSong.add(scrollPane);
      
//      epSong.setText("test <b>TEST</b> <h1>HHHH</H1>");
      epSong.setContentType("text/rtf");
      epSong.setText("{\\rtf1 \\fs36\\qc HelloWorld! \\par\\fs24\\qr {\\i This} is formatted {\\b\\i Text}\\par Спасибо!.} ");
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
         
         
      }
   }

   private void exportToPdf() throws DocumentException, IOException
   {
//      final int iPageWidth = 612,
//                iPageHeight = 792;

      final int iPageWidth = 300,
               iPageHeight = 400;

      
      epSong.setBounds(0, 0, (int) convertToPixels(iPageWidth - 58), (int) convertToPixels(iPageHeight - 60));

      com.itextpdf.text.Document document = new com.itextpdf.text.Document();
      FileOutputStream fos = new FileOutputStream("C:\\Temp\\3.pdf");
      PdfWriter writer = PdfWriter.getInstance(document, fos);

      document.setPageSize(new com.itextpdf.text.Rectangle(iPageWidth, iPageHeight));
      document.open();
      PdfContentByte cb = writer.getDirectContent();

      cb.saveState();
      cb.concatCTM(1, 0, 0, 1, 0, 0);

      DefaultFontMapper mapper = new DefaultFontMapper();
      mapper.insertDirectory("c:\\windows\\fonts");

      BaseFontParameters parameters = new BaseFontParameters("c:\\windows\\fonts\\cour.ttf");
      parameters.encoding = BaseFont.IDENTITY_H;
      mapper.putName("Courier New", parameters );      
      

      Graphics2D g2 = cb.createGraphics(iPageWidth, iPageHeight, mapper, true, .95f);

      java.awt.geom.AffineTransform at = new java.awt.geom.AffineTransform();
      at.translate(convertToPixels(20), convertToPixels(20));
      at.scale(pixelToPoint, pixelToPoint);

      g2.transform(at);

      g2.setColor(Color.WHITE);
      g2.fill(epSong.getBounds());

      java.awt.Rectangle alloc = getVisibleEditorRect(epSong);
      epSong.getUI().getRootView(epSong).paint(g2, alloc);

//      g2.setColor(Color.BLACK);
//      g2.setColor(Color.WHITE);
//      g2.draw(epSong.getBounds());

      g2.dispose();
      cb.restoreState();
      document.close();
      fos.flush();
      fos.close();
   }
   
   protected java.awt.Rectangle getVisibleEditorRect(JTextPane ta) {
      java.awt.Rectangle alloc = epSong.getBounds();
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
      if (epSong.getDocument().getLength() > 0)
      {
         JFileChooser oFileChooser = new JFileChooser();
         ExtensionFileFilter ffRTF = new ExtensionFileFilter("RichText Format", new String[] { "rtf" });
         oFileChooser.setFileFilter(ffRTF);
         oFileChooser.setMultiSelectionEnabled(false);

         if (oFileChooser.showSaveDialog(Preview.this) == JFileChooser.APPROVE_OPTION) 
         {
            File oFile = oFileChooser.getSelectedFile();
            String sPar = oFile.getParent();
            String sFile = oFile.getName();
            if(!sFile.toLowerCase().endsWith(".rtf"))
               sFile += ".rtf";
            oFile = new File(sPar + "\\" + sFile);

            // 
            if(oFile.exists() && !oFile.isDirectory()) 
            {
               int dialogResult = JOptionPane.showConfirmDialog (null, "File Exists. Would You Like to Overwrite it", "Warning", JOptionPane.YES_NO_OPTION);
               if(dialogResult == JOptionPane.NO_OPTION)
                  return;
            }                  
            
            final StringWriter out = new StringWriter();
            Document doc = epSong.getDocument();
            AdvancedRTFEditorKit kit = new AdvancedRTFEditorKit();
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
   }
}
