*   maximization
NAME          BURMAA
ROWS
 N  OBJECTIV
 L  LOGISCH1
 L  LOGISCH3
 L  LOGISCH4
 G  LOGISCH5
 L  WASSERA
 L  WASSERB
 L  WASSERC
 L  WASSERD
 L  ANBAUA
 L  ANBAUB
 L  ANBAUC
 L  ANBAUD
 L  MXGETREI
COLUMNS
    REISA     OBJECTIV      1100.000   WASSERA           .250
    REISA     ANBAUA           1.000   MXGETREI         -.300
    REISB     OBJECTIV      1300.000   WASSERB           .250
    REISB     ANBAUB           1.000   MXGETREI         -.300
    REISC     OBJECTIV      1400.000   WASSERC           .250
    REISC     ANBAUC           1.000   MXGETREI         -.300
    REISD     OBJECTIV      1600.000   WASSERD           .250
    REISD     ANBAUD           1.000   MXGETREI         -.300
    GETREIDA  OBJECTIV      1000.000   WASSERA           .100
    GETREIDA  ANBAUA           1.000   MXGETREI          .700
    GETREIDB  OBJECTIV      1300.000   WASSERB           .100
    GETREIDB  ANBAUB           1.000   MXGETREI          .700
    GETREIDC  OBJECTIV      1600.000   WASSERC           .100
    GETREIDC  ANBAUC           1.000   MXGETREI          .700
    GETREIDD  OBJECTIV      1800.000   WASSERD           .100
    GETREIDD  ANBAUD           1.000   MXGETREI          .700
    IST00001  'MARKER'                 'INTORG'
    EIKANALA  OBJECTIV     -5000.000   WASSERA         -1.500
    EIKANALA  LOGISCH1        -1.000   LOGISCH5         1.000
    STAUDAMM  OBJECTIV    -10000.000   LOGISCH1        -1.000
    STAUDAMM  LOGISCH5         1.000   LOGISCH4        -1.000
    ANKANALB  OBJECTIV     -2000.000   WASSERA           .100
    ANKANALB  WASSERB         -1.000   LOGISCH1         2.000
    ANKANALB  LOGISCH3        -1.000   LOGISCH5         1.000
    ANKANALC  OBJECTIV     -2000.000   WASSERA           .400
    ANKANALC  WASSERB           .200   WASSERC          -.600
    ANKANALC  LOGISCH3         1.000   LOGISCH5         1.000
    KRAFTWER  OBJECTIV     -5000.000   WASSERD         -1.200
    KRAFTWER  LOGISCH4         1.000   LOGISCH5         1.000
    IEN00001  'MARKER'                 'INTEND'
RHS
    RHS       ANBAUA           5.000   ANBAUB           5.000
    RHS       ANBAUC           5.000   ANBAUD           5.000
    RHS       LOGISCH5         1.000
BOUNDS
 UP BOUNDS    EIKANALA         1.000
 UP BOUNDS    STAUDAMM         1.000
 UP BOUNDS    ANKANALB         1.000
 UP BOUNDS    ANKANALC         1.000
 UP BOUNDS    KRAFTWER         1.000
ENDATA
