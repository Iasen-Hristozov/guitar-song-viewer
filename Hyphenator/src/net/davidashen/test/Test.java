package net.davidashen.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.davidashen.util.ErrorHandler;

public class Test
{

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      net.davidashen.text.Hyphenator h=new net.davidashen.text.Hyphenator();
      h.setErrorHandler(new ErrorHandler() {
         public void debug(String guard,String s) {}
         public void info(String s) {System.err.println(s);}
         public void warning(String s) {System.err.println("WARNING: "+s);}
         public void error(String s) {System.err.println("ERROR: "+s);}
         public void exception(String s,Exception e) {System.err.println("ERROR: "+s); e.printStackTrace(); }
         public boolean isDebugged(String guard) {return false;}
       });
      try
      {
//         h.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyphen.tex")));
//         h.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyph-en-us.tex")));
         h.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("etc/hyphen/hyph-bg.tex")));
         
      } catch(FileNotFoundException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } catch(IOException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
      
//      try {
//         h.loadTable(table,codelist);
//         table.close();
//       } catch(java.io.IOException e) {
//         System.err.println("error loading hyphenation table: "+e.toString());
//         System.exit(1);
//       }
      
      String hyphenated_word=h.hyphenate("родина");
//      String hyphenated_word=h.hyphenate("hyphenation");
      System.out.println(hyphenated_word); 

   }

}
