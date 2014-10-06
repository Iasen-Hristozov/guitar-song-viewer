package com.discworld.guitarsongplugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Policy;
import java.util.ArrayList;



import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.discworld.guitarsongplugins.dto.CGuitarSongPlugin;


public class PluginTest
{
   public final static String PLUGIN_FOLDER = "plugins",
                              PLUGIN_SUFFIX = ".jar";
   
   private static ArrayList<CGuitarSongPlugin> alPlugins;

   public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException
   {
//      final String sURL = "http://www.falshivim-vmeste.ru/songs/827193600.html";
//      final String sURL = "http://tabs.ultimate-guitar.com/j/johnny_mandel/suicide_is_painless_tab.htm";
//      final String sURL = "http://tabs.ultimate-guitar.com/b/blues_brothers/rawhide_crd.htm";
//      final String sURL = "http://muzland.ru/songs.html?auth=108&song=3&tone=Am";
//      final String sURL = "http://muzland.ru/songs.html?auth=108&song=35";
//      final String sURL = "http://hm6.ru/ddt/18072-ddt-napishi-mne-napishi.html";
//      final String sURL = "http://hm6.ru/ddt/1233-ddt-antonina.html";
//      final String sURL = "http://www.amdm.ru/akkordi/mashina_vremeni/12899/ah_kakoi_bil_iziskannii_bal/";
//      final String sURL = "http://www.amdm.ru/akkordi/mashina_vremeni/3046/sinaa_ptica/";
      
//      final String sURL = "http://accords.cu.cc/txt.php?s=070&d=017#v10";
//      final String sURL = "http://accords.cu.cc/txt.php?s=070&d=024#v08";
//      final String sURL = "http://www.dix.ru/3039";
//      final String sURL = "http://www.dix.ru/?id=3168";
      final String sURL = "http://abcdisk.ru/akkordi/visockii_vladimir/1222/ballada_o_borbe/";
//      final String sURL = "http://abcdisk.ru/akkordi/visockii_vladimir/14268/ah_vrema_kak_mahorochka/";
//      final String sURL = "http://pesni.ru/song/97/";
//      final String sURL = "http://pesni.ru/song/100/";
//      final String sURL = "http://music-palace.ru/?id=114&song=12013";
//      final String sURL = "http://music-palace.ru/?id=114&song=12049";
//      final String sURL = "http://spoemte.ru/songs/1066608000.html";
//      final String sURL = "http://akordite.com/index.php?option=com_chordbase&Itemid=26&task=viewSong&song_id=53052";
//      final String sURL = "http://www.akordite.com/index.php?option=com_chordbase&Itemid=26&task=viewSong&song_id=381";
      
      new File(PLUGIN_FOLDER).mkdirs();
      
      Policy.setPolicy(new PluginPolicy());
      System.setSecurityManager(new SecurityManager());
      
      alPlugins = new ArrayList<CGuitarSongPlugin>();
      
      final File fPluginFolder = new File(System.getProperty("user.dir") + "\\" + PLUGIN_FOLDER);
      
      loadPlugins(fPluginFolder);
      
      
      
      for(CGuitarSongPlugin oPlugin: alPlugins)
      {
         if(sURL.contains(oPlugin.getDomainName()))
         {
            oPlugin.getSongFromURL(sURL);
            System.out.println(oPlugin.getDomainName());
            System.out.println(oPlugin.getTitle());
            System.out.println(oPlugin.getAuthor());
            System.out.println(oPlugin.getSong());
         }
      }
      

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
             System.out.println(fEntry.getName());
             
             if(!fEntry.getName().endsWith(PLUGIN_SUFFIX))
                continue;
             
//             File authorizedJarFile = new File("FalshivimVmeste.jar");
//             File authorizedJarFile = new File(fEntry.getName());
             ClassLoader oClassLoader = URLClassLoader.newInstance(new URL[] { fEntry.toURL() });
//             System.out.println(getClassName(authorizedJarFile.getPath()));
//             CGuitarSongPlugin authorizedPlugin = (CGuitarSongPlugin) authorizedLoader.loadClass("com.discworld.guitarsongplugins.FalshivimVmeste").newInstance();
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

}
