UNIT p_settin;
{$O+,F+}
INTERFACE
USES {$ifdef msdos }OVERLAY,{$ENDIF}
     {$IFDEF win32} CONTROLS,DIALOGS, {$ENDIF}
     util;
TYPE printer_type=RECORD
                    pitch,lpi,lpp,rand:BYTE;
                    portrait_,drucker_,destination_:STRING[20];
                    init_:STRING;
                    output,nlq,duplex,upper:BOOLEAN;
                    ae_k,ae_g,oe_k,oe_g,ue_k,ue_g,ss_:STRING[8];
                  END;

PROCEDURE make_init(VAR p:printer_type);

IMPLEMENTATION

CONST ame=#27'R'#0;
      deu=#27'R'#2;

PROCEDURE make_init(VAR p:printer_type);
BEGIN
  WITH p DO
  BEGIN
    IF drucker_='NEC' THEN
    BEGIN
      ss_ :=deu+#126+ame;
      ae_k:=deu+#123+ame; oe_k:=deu+#124+ame; ue_k:=deu+#125+ame;
      ae_g:=deu+#91+ame;  oe_g:=deu+#92+ame;  ue_g:=deu+#93+ame;
      init_:=#28#64;            { Reset Printer }
      init_:=init_+#27'N'#6;    { WÑhlt Perforationssprung mit 6 Zeilen }
      init_:=init_+#27#120#0;   { Wahl von Schnelldruck }
      IF nlq THEN init_:=init_+#27'x1' ELSE init_:=init_+#27'x0';
      CASE pitch OF
        10:
        BEGIN
          init_:=init_+#27#80;       { 10 CPI }
        END;
        12:
        BEGIN
          init_:=init_+#27#77;      { 12 CPI }
        END;
        16:
        BEGIN
          init_:=init_+#27#103;     { Schmalschrift }
        END;
        20:
        BEGIN
          init_:=init_+#27'!'#5;    { 12 CPI CONDENSED }
        END;
      END;
      init_:=init_+#27#108+CHR(rand);    { Linker Rand }
    END ELSE IF drucker_='LASER' THEN
    BEGIN
      ss_ :='·'; ae_k:='Ñ'; oe_k:='î'; ue_k:='Å';
      ae_g:='é'; oe_g:='ô'; ue_g:='ö';
      init_:=#27'E';         { RESET }
      IF portrait_[1]='P' THEN init_:=init_+#27#38#108#48#79  { Hochformat }
      ELSE                     init_:=init_+#27#38#108#49#79;  { Querformat }
      init_:=init_+#27'&l26A';                      { Papierformat DIN A4 }
      init_:=init_+#27#40#49#48#85;              { Zeichsatz IBM-PC }
      init_:=init_+#27#38#108+ltoa(3)+#69;       { oberer Rand 3 }
      init_:=init_+#27#38#97+ltoa(rand)+#76;    { Linker Rand }
      init_:=init_+#27#38#108#49#76;       { Perforation Åberspringen }
      init_:=init_+#27#40#115+ltoa(pitch)+#72;             { pitch }
      init_:=init_+#27#40#115#48#83;       { Schrift normal aufrecht }
      {    init_:=init_+#27#40#115#48#84; }      { Schrittyp Lineprinter }
      init_:=init_+#27'&l'+ltoa(lpi)+'D';              { Lines per Inch }
      {    init_:=init_+#27'&l'+ltoa(page)+'P';}  { Page length }
      init_:=init_+#27'&l'+ltoa(lpp-3)+'F';
    END ELSE IF drucker_='DUPLEX' THEN
    BEGIN
      ss_ :='·'; ae_k:='Ñ'; oe_k:='î'; ue_k:='Å';
      ae_g:='é'; oe_g:='ô'; ue_g:='ö';
      init_:=#27'E';         { RESET }
      IF portrait_[1]='P' THEN init_:=init_+#27#38#108#48#79  { Hochformat }
      ELSE                     init_:=init_+#27#38#108#49#79;  { Querformat }
      init_:=init_+#27'&l26A';                      { Papierformat DIN A4 }
      init_:=init_+#27#40#49#48#85;              { Zeichsatz IBM-PC }
      init_:=init_+#27#38#108+ltoa(3)+#69;       { oberer Rand 3 }
      init_:=init_+#27#38#97+ltoa(rand)+#76;    { Linker Rand }
      init_:=init_+#27#38#108#49#76;       { Perforation Åberspringen }
      init_:=init_+#27#40#115+ltoa(pitch)+#72;             { pitch }
      init_:=init_+#27#40#115#48#83;       { Schrift normal aufrecht }
      {    init_:=init_+#27#40#115#48#84; }      { Schrittyp Lineprinter }
      init_:=init_+#27'&l'+ltoa(lpi)+'D';              { Lines per Inch }
      {    init_:=init_+#27'&l'+ltoa(page)+'P';}  { Page length }
      init_:=init_+#27'&l'+ltoa(lpp-3)+'F';
      IF duplex THEN init_:=init_+#27'&l1S' ELSE init_:=init_+#27'&l0S';
      IF upper  THEN init_:=init_+#27'&l1H' ELSE init_:=init_+#27'&l4H';
      IF output THEN init_:=init_+#27'&l2G' ELSE init_:=init_+#27'&l1G';
    END
    ELSE my_message('NO PRINTER SELECTED');
  END;
END;
END.
