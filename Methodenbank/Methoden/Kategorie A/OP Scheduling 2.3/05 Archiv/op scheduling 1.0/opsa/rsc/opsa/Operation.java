package opsa;

/**
 * Title:        Operationssäleplanung
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      FHKN
 * @author Arne Bittermann, Liwei Lu
 * @version 1.0
 */

public class Operation {
  private int ID,anfang,ende,dauer;
  private String merkung;
  public Operation(int in_ID,int in_anfang,int in_ende,
                  int in_dauer,String in_merkung) {
    ID=in_ID;
    anfang=in_anfang;
    ende=in_ende;
    dauer=in_dauer;
    merkung=in_merkung;
  }
  public int getID(){
    return ID;
  }
  public int getAnfang(){
    return anfang;
  }
  public int getEnde(){
    return ende;
  }
  public int getDauer(){
    return dauer;
  }
  public String getMerk(){
    return merkung;
  }
}