package com.discworld.dto;

import java.util.ArrayList;

public interface IDataExchange
{
   public void setLinesNbr(int iLinesNbr);
   public int getLinesNbr();
   public ArrayList<CTextVerse> getPage(int iNdx);
   public int getTextSize();
}
