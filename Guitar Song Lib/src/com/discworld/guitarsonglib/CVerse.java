package com.discworld.guitarsonglib;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({CTextVerse.class})
@XmlRootElement(name = "text-verse")
public abstract class CVerse
{
   
   ArrayList<?> alLines;
   public abstract int size(); 
   @Override
   public abstract String toString();
}
