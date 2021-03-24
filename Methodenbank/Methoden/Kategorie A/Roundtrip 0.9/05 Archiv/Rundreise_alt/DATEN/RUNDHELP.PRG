******************************************************************************
* Projekt...........: RLS-Reporting                                          *
*                     *************                                          *
*                                                                            *
* Programm / Module.: Programm Rundhelp.PRG                                  *
* Version / Datum...: 1.00 / 22.04.1992                                      *
* Datum erstversion.: 22.04.1992                                             *
*                                                                            *
* Programmierer.....: R.Sauter                                               *
*                                                                            *
* Aenderungen.......:                                                        *
*                                                                            *
*----------------------------------------------------------------------------*
* Funktion: Textdateien am Bildschirm anzeigen                               *
*                                                                            *
*                                                                            *
*                                                                            *
*----------------------------------------------------------------------------*
* Prozeduren und Funktionen:                                                 *
*                                                                            *
******************************************************************************

 PARAMETERS p_par1, p_par2

 #INCLUDE "INKEY.CH"
 #INCLUDE "ERROR.CH"
 #INCLUDE "NT250.CH"
 
 LOCAL   l_pos
 PRIVATE c_startcolor, l_coltxt

*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

 SET CURSOR OFF
 c_startcolor := SETCOLOR()
 
 
 
 Bild()
  

*-----------------------------------------------------------------------------

PROCEDURE Ende               && Beendigung des Programms

*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  SETCOLOR (c_startcolor)
  CLS
  QUIT
  SET CURSOR ON

RETURN

*-----------------------------------------------------------------------------

PROCEDURE Bild   && Ausgabe am Bildschirm

LOCAL l_texte
LOCAL l_xo1 := 0
LOCAL l_yo1 := 0
LOCAL l_xu1 := 1
LOCAL l_yu1 := 79
LOCAL l_xo2 := 23
LOCAL l_yo2 := 0
LOCAL l_xu2 := 24
LOCAL l_yu2 := 79
LOCAL l_xo3 := 1
LOCAL l_yo3 := 25
LOCAL l_xu3 := 1
LOCAL l_yu3 := 79
LOCAL l_xo4 := 23
LOCAL l_yo4 := 0
LOCAL l_xu4 := 23
LOCAL l_yu4 := 79
LOCAL l_xo5 := 24
LOCAL l_yo5 := 0
LOCAL l_xu5 := 24
LOCAL l_yu5 := 79
LOCAL l_xo6 := 3
LOCAL l_yo6 := 6
LOCAL l_xu6 := 22
LOCAL l_yu6 := 76

*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

  
  
  // Backslash am ende entfernen

  IF EMPTY(p_par1)
    CLS
    @ 10,10 SAY "1. Parameter fehlt , Programm wird abgebrochen"
    INKEY(5)
    Ende()    
  ENDIF
  
  l_len := LEN(p_par1)
  IF SUBSTR(p_par1, l_len, 1) = "\"
    p_par1 := LEFT(p_par1, l_len -1 )
  ENDIF

  IF EMPTY(p_par2)
    CLS  
    @ 10,10 SAY "2. Parameter fehlt , Programm wird abgebrochen"
    INKEY(5)
    Ende()
  ENDIF

  IF FILEVALID(p_par2)
    IF FILE(p_par1 + "\" + p_par2)
  
      l_texte := MEMOREAD(p_par1 + "\" + p_par2)
      l_coltxt := Steuerung()      
      
      l_hauptcol := Farben()                            // Hauptfarbe

      l_pos1     := Farben()                           // oberer Farbbereich
      Position(l_pos1, @l_xo1, @l_yo1, @l_xu1, @l_yu1)
      l_col1     := Farben()
       
      l_pos2     := Farben()                           // unterer Farbbereich
      Position(l_pos2, @l_xo2, @l_yo2, @l_xu2, @l_yu2)
      l_col2     := Farben()

      l_texto    := Farben()                          // öberschrift
      l_pos3     := Farben()
      Position(l_pos3, @l_xo3, @l_yo3, @l_xu3, @l_yu3)

      l_textu1   := Farben()                          // Unterzeile1
      l_pos4     := Farben()
      Position(l_pos4, @l_xo4, @l_yo4, @l_xu4, @l_yu4)

      l_textu2   := Farben()                          // Unterzeile2
      l_pos5     := Farben()
      Position(l_pos5, @l_xo5, @l_yo5, @l_xu5, @l_yu5)

      l_pos6     := Farben()
      Position(l_pos6, @l_xo6, @l_yo6, @l_xu6, @l_yu6)
      
      SETCOLOR(l_hauptcol)
      CLS


      *-------------------------------------------------
      *  oberer Bildschirmbereich und Farben festlegen
      
      @ l_xo1, l_yo1, l_xu1, l_yu1  BOX SPACE(0) COLOR l_col1  
      @ l_xo3, l_yo3  SAY l_texto  COLOR(l_col1)
      

      *-------------------------------------------------
      *  unterer Bildschirmbereich und Farben festlegen
      
      @ l_xo2, l_yo2, l_xu2, l_yu2 BOX SPACE(0) COLOR  l_col2 
      @ l_xo4, l_yo4 SAY l_textu1  COLOR(l_col2)
      @ l_xo5, l_yo5 SAY l_textu2  COLOR(l_col2)
      
      
//      @ 23, 0 SAY " <ESC> = Start      BlÑttern mit PgUp , PgDn,         " ;
//      COLOR(l_col2)
//      COLOR("N/W,W/R,,,N/BG+")
//      @ 24, 0 SAY " <Ctrl_PgUp> = Anfang     <Ctrl_PgDn> = Ende      " ;
//      COLOR("N/W,W/R,,,N/BG+")

       
//      @ 3, 6 TO 22, 76 DOUBLE

      *--------------------------------------------
      * Helpfenster festlegen
      
      @ l_xo6, l_yo6 TO l_xu6, l_yu6 DOUBLE
      MEMOEDIT(l_texte, 4, 7, 21, 75, .F., "MemoTasten", 69)

      Ende()
    ELSE
      CLS  
      @ 10,10 SAY ""
      ?? "TextFile ", (p_par1 + "\" + p_par2) , " existiert nicht"
      @ 12,10 SAY " Programm wird abgebrochen"
      INKEY(5)
      Ende()
    ENDIF
    
  ELSE
    CLS  
    @ 10,1 SAY ""
    ?? "    ", p_par2, "  ist kein gÅltiger Dateiname"
    ?  "Programm wird abgebrochen"
    INKEY(5)
    Ende()
  
  ENDIF

  RETURN

*-----------------------------------------------------------------------------

FUNCTION MemoTasten  && Funktion fÅr 'MEMOEDIT'

LOCAL l_taste, l_rueck

*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 
  l_rueck = 0
  l_taste := LASTKEY()

  DO CASE
    CASE (l_taste == K_F8)
      CLS
      l_rueck = 27
  ENDCASE

 RETURN(l_rueck)



*-----------------------------------------------------------------------------

FUNCTION Steuerung  && Funktion zum einlesen der Bildschirmfarben

LOCAL  l_farbe

*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

 l_datei := p_par1 + "\" + "steuer.txt"
 IF FILE(l_datei)
   l_farbe := MEMOREAD(l_datei)
 ELSE
   CLS  
   @ 10,10 SAY ""
   ?? "TextFile ", l_datei , " existiert nicht"
   @ 12,10 SAY " Programm wird abgebrochen"
   INKEY(5)
   Ende()
 ENDIF

 RETURN(l_farbe)


*-----------------------------------------------------------------------------

FUNCTION Farben()  && Funktion zum bestimmen der Bildschirmfarben

LOCAL  l_crlf, l_pos, l_col

*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

      l_crlf := CHR(13) + CHR(10)
      l_pos  := AT( l_crlf, l_coltxt)
      l_col  := LEFT(l_coltxt, l_pos -1)
      l_coltxt := STUFF(l_coltxt, 1, l_pos + 1, "")
      
      RETURN (l_col)

*-----------------------------------------------------------------------------

PROCEDURE Position(p_text, l_xo, l_yo, l_xu, l_yu) 
                                 && Funktion zum bestimmen der Koordinaten

LOCAL  l_crlf, l_pos, l_col, l_komma
*- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
      
      l_komma := ","

      l_pos  := AT( l_komma, p_text)
      l_xo   := VAL(LEFT(p_text, l_pos -1))
      p_text := STUFF(p_text, 1, l_pos , "")

      l_pos  := AT( l_komma, p_text)
      l_yo   := VAL(LEFT(p_text, l_pos -1))
      p_text := STUFF(p_text, 1, l_pos , "")
      
      l_pos  := AT( l_komma, p_text)
      l_xu   := VAL(LEFT(p_text, l_pos -1))
      p_text := STUFF(p_text, 1, l_pos , "")
      
      l_yu := VAL(p_text)
      
      RETURN
  