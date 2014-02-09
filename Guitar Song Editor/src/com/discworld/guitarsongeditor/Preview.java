package com.discworld.guitarsongeditor;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Preview extends JPanel
{
   private JTextField textField;
   public Preview() {
      
      textField = new JTextField();
      add(textField);
      textField.setColumns(10);
   }

}
