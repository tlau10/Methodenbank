UNIT zeitrech;
{$O+,F+}
INTERFACE
USES {$IFDEF msDos } OVERLAY, {$ENDIF} util;

TYPE zeit_type=RECORD
                 meridian,jahr:INTEGER;
                 sommer,zonenzeit,monat,tag,stunde,minute,dayofweek:INTEGER;
                 szeit,uhrzeit:SINGLE;
                 jd:DOUBLE;
                 valid:BOOLEAN;
               END;

CONST reference:EXTENDED=2415018.5; { for date calculation }
      time_offset=35800;

PROCEDURE zeit_init(VAR zeit:zeit_type);
PROCEDURE buergerlich(VAR zeit:zeit_type);
PROCEDURE juliadatum(VAR zeit:zeit_type);
PROCEDURE zzeit(VAR z:zeit_type; geo_laenge:SINGLE);
FUNCTION  datumsanalyse(s_:STRING; VAR zeit:zeit_type):BOOLEAN;
FUNCTION  welcher_tag_(jd:DOUBLE):STRING;
FUNCTION  zeitanalyse(s_:STRING; VAR zeit:zeit_type):BOOLEAN;
FUNCTION  hmsto(zeit:DOUBLE):DOUBLE;
FUNCTION  tohms_(zeit:DOUBLE):STRING;
FUNCTION  schaltjahrtest(j:LONGINT):BYTE;


IMPLEMENTATION
CONST jul_konst=1720995.0;
      trop_jahr=365.25;
      monat_konst=30.6001;

PROCEDURE zeit_init(VAR zeit:zeit_type);
BEGIN
  fillchar(zeit,SIZEOF(zeit),0);
END;
{---------------------------------------------------------------------------}
PROCEDURE zzeit(VAR z:zeit_type; geo_laenge:SINGLE);
BEGIN
  WITH z DO
  BEGIN
    IF geo_laenge>=-7.5 THEN z.meridian:=ROUND( INT( (geo_laenge+7.5)/15)*15)
    ELSE                     z.meridian:=ROUND(-INT(-(geo_laenge-7.5)/15)*15);
    zonenzeit:=z.meridian DIV 15;
  END;
END;
{----------------------------------------------------------------------------}
FUNCTION hmsto(zeit:DOUBLE):DOUBLE;
VAR h,m,s:LONGINT;
BEGIN
  h:=TRUNC(zeit); m:=TRUNC(FRAC(zeit)*100); s:=TRUNC(100.00001*FRAC(100*zeit));
  hmsto:=h+m/60+s/3600;
END;
{----------------------------------------------------------------------------}
FUNCTION tohms_(zeit:DOUBLE):STRING;
VAR h,m,s:INTEGER;
    rest:DOUBLE;
    s2_:STRING[3];
    s_:STRING[20];
    vorz:BOOLEAN;
BEGIN
  vorz:=zeit<0;
  IF vorz THEN zeit:=-zeit;
  h:=TRUNC(zeit); rest:=60*(zeit-h); m:=TRUNC(rest); rest:=60*(rest-m);
  s:=TRUNC(rest); STR(h,s2_); s_:=s2_; STR(m:2,s2_); s_:=s_+'.'+s2_+#39;
  STR(s:2,s2_);
  IF vorz THEN s_:='-'+s_;
  tohms_:=s_+s2_+#34;
END;
{----------------------------------------------------------------------------}
FUNCTION schaltjahrtest(j:LONGINT):BYTE;
VAR limit:BYTE;
BEGIN
  limit:=2;
  IF j<0 THEN
  BEGIN
    IF (j+1) MOD 4=0 THEN limit:=13;
  END
  ELSE IF j MOD 4=0 THEN
  BEGIN
    limit:=13;
    IF j>=1582 THEN
    BEGIN
      IF j MOD 100=0 THEN limit:=2;
      IF j MOD 400=0 THEN limit:=13;
    END;
  END;
  schaltjahrtest:=limit;
END;
{-----------------------------------------------------------------------------}
FUNCTION welcher_tag_(jd:DOUBLE):STRING;
CONST wochentage:ARRAY [1..7] OF STRING[11]=('Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag','Sonntag');
BEGIN
  welcher_tag_:=wochentage[TRUNC(jd+0.50000001) MOD 7+1];
END;
{-----------------------------------------------------------------------------}
PROCEDURE juliadatum(VAR zeit:zeit_type);
VAR m1,hjahr:INTEGER;
BEGIN
  WITH zeit DO
  BEGIN
    hjahr:=jahr; uhrzeit:=stunde+minute/60;
    IF hjahr<0 THEN INC(hjahr);
    IF monat>3 THEN m1:=monat
    ELSE
    BEGIN
      DEC(hjahr); m1:=monat+12;
    END;
    jd:=hjahr DIV 100; jd:=2.0-jd+TRUNC(jd/4);
    IF jahr+monat/100.0+tag/10000.0<1582.10051 THEN jd:=0;
    IF hjahr<0 THEN jd:=-INT(-trop_jahr*hjahr+0.75)+INT(monat_konst*(m1+1))
    ELSE jd:=jd+INT(trop_jahr*hjahr)+INT(monat_konst*(m1+1));
    jd:=jd+(uhrzeit-zonenzeit)/24.0-0.5+tag+jul_konst;
    IF sommer=1 THEN jd:=jd-1.0/24.0;
  END;
END;
{-----------------------------------------------------------------------------}
FUNCTION datumsanalyse(s_:STRING; VAR zeit:zeit_type):BOOLEAN;
CONST tage :ARRAY [1..13] OF BYTE=(31,28,31,30,31,30,31,31,30,31,30,31,29);
VAR a:BYTE;
    code:INTEGER;
    korrekt:BOOLEAN;
    td:DOUBLE;
    limit:BYTE;
BEGIN
  WITH zeit DO
  BEGIN
    korrekt:=TRUE; valid:=TRUE; a:=POS('.',s_);
    IF a=0 THEN korrekt:=FALSE
    ELSE
    BEGIN
      VAL(rtrim(ltrim(COPY(s_,1,a-1))),tag,code);
      IF code<>0 THEN korrekt:=FALSE
      ELSE IF tag<1 THEN korrekt:=FALSE
      ELSE
      BEGIN
        s_:=ltrim(COPY(s_,a+1,255));
        a:=POS('.',s_);
        IF a=0 THEN korrekt:=FALSE
        ELSE
        BEGIN
          VAL(rtrim(ltrim(COPY(s_,1,a-1))),monat,code);
          IF code<>0 THEN korrekt:=FALSE
          ELSE
          BEGIN
            VAL(rtrim(ltrim(COPY(s_,a+1,255))),jahr,code);
            IF code<>0 THEN korrekt:=FALSE
            ELSE
            BEGIN
              IF jahr=0 THEN
              BEGIN
                WRITELN('Das Jahr 0 gibt es nicht'); korrekt:=FALSE;
              END ELSE IF jahr>16000 THEN
              BEGIN
                WRITELN('Das Jahr liegt nicht im Berechnungsbereich'); korrekt:=FALSE;
              END ELSE IF jahr<-4713 THEN
              BEGIN
                WRITELN('Das Jahr liegt nicht im Berechnungsbereich'); korrekt:=FALSE;
              END ELSE
              BEGIN
                IF monat IN [1..12] THEN
                BEGIN
                  limit:=monat;
                  IF monat=2 THEN limit:=schaltjahrtest(jahr);
                  IF tag>tage[limit] THEN korrekt:=FALSE;
                END
                ELSE korrekt:=FALSE;
                td:=jahr+monat/100+tag/10000;
                IF (td>1582.10041) AND (td<1582.10141) THEN
                BEGIN
                  WRITELN('Dieses Datum gibt es nicht'); korrekt:=FALSE;
                END;
              END;
            END;
          END;
        END;
      END;
    END;
  END;
  datumsanalyse:=korrekt; zeit.valid:=korrekt;
END;
{----------------------------------------------------------------------------}
FUNCTION zeitanalyse(s_:STRING; VAR zeit:zeit_type):BOOLEAN;
VAR a:INTEGER;
    zeit_neu:DOUBLE;
BEGIN
  WITH zeit DO
  BEGIN
    zeitanalyse:=TRUE; VAL(s_,zeit_neu,a);
    IF a<>0 THEN
    BEGIN
      zeitanalyse:=FALSE; valid:=FALSE; EXIT;
    END;
    stunde:=TRUNC(zeit_neu); minute:=ROUND(FRAC(zeit_neu)*100.0);
    IF (stunde>23) OR (stunde<0) OR (minute>59) OR (minute<0) THEN
    BEGIN
      zeitanalyse:=FALSE; EXIT;
    END;
    uhrzeit:=stunde+minute/60.0; valid:=TRUE;
  END;
END;
{----------------------------------------------------------------------------}
PROCEDURE buergerlich(VAR zeit:zeit_type);
VAR a,a1,b:INTEGER;
    ok:BOOLEAN;
    jd1,dat:DOUBLE;
BEGIN
  WITH zeit DO
  BEGIN
    jd1:=jd+zonenzeit/24;
    IF sommer=1 THEN jd1:=jd1+1/24;
    dat:=TRUNC(jd1+0.50000001); uhrzeit:=12.0+(jd1-dat)*24.0;
    IF uhrzeit<0 THEN uhrzeit:=uhrzeit+24;
    stunde:=TRUNC(uhrzeit); minute:=ROUND(FRAC(uhrzeit)*60);
    IF minute=60 THEN
    BEGIN
      minute:=0; INC(stunde);
    END;
    IF dat<0 THEN
    BEGIN
      WRITELN('Das Julianische Datum gibt es nicht!'); EXIT;
    END;
    IF dat<=1721117.0 THEN
    BEGIN
      jahr:=TRUNC((dat-jul_konst)/trop_jahr);
      REPEAT
        tag:=TRUNC(dat-jul_konst+INT(-jahr*trop_jahr+0.75));
        monat:=TRUNC(tag/monat_konst)-1;
        IF monat<3 THEN DEC(jahr);
      UNTIL monat>=3;
      DEC(tag,TRUNC(monat_konst*(monat+1)));
      IF monat>12 THEN
      BEGIN
        DEC(monat,12); INC(jahr);
      END;
      IF jahr<=0 THEN DEC(jahr);
    END
    ELSE
    BEGIN
      a:=TRUNC((dat-jul_konst)/trop_jahr); b:=0;
      IF dat>=2299161.0 THEN
      BEGIN
        REPEAT
          b:=TRUNC(a/100.0);
          b:=2-b+TRUNC(b/4.0);
          a1:=TRUNC((dat-jul_konst-b)/trop_jahr);
          ok:=a1=a;
          IF a1<>a THEN a:=a1;
        UNTIL ok;
      END;
      REPEAT
        jahr:=a;
        tag:=TRUNC(dat-jul_konst-b-INT(a*trop_jahr));
        monat:=TRUNC(tag/monat_konst)-1;
        DEC(tag,TRUNC(monat_konst*(monat+1)));
        IF monat<3 THEN
        BEGIN
          DEC(a); b:=0;
          IF dat>=2299161.0 THEN
          BEGIN
            b:=TRUNC(a/100); b:=2-b+TRUNC(b/4);
          END;
        END;
      UNTIL monat>=3;
      IF monat>12 THEN
      BEGIN
        DEC(monat,12); INC(jahr);
      END;
      IF jahr<=0 THEN DEC(jahr);
    END;
  END;
END;
{----------------------------------------------------------------------------}
END.
