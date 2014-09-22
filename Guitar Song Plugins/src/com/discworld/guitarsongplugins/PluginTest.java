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
      final String sURL = "http://www.falshivim-vmeste.ru/songs/827193600.html";
    
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
             ClassLoader authorizedLoader = URLClassLoader.newInstance(new URL[] { fEntry.toURL() });
//             System.out.println(getClassName(authorizedJarFile.getPath()));
//             CGuitarSongPlugin authorizedPlugin = (CGuitarSongPlugin) authorizedLoader.loadClass("com.discworld.guitarsongplugins.FalshivimVmeste").newInstance();
             CGuitarSongPlugin authorizedPlugin = (CGuitarSongPlugin) authorizedLoader.loadClass(getClassName(fEntry.getPath())).newInstance();
             alPlugins.add(authorizedPlugin);
             
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