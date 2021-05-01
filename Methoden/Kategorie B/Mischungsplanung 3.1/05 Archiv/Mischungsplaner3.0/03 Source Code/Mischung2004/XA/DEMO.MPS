
* Beispiel einer MPS-Datei fÅr
*
*    3 X1 + 2 X2 <  12
*    1 X1 + 3 X2 <= 9
*    1 X1 + 2 X2 --> Max!
*

NAME          DEMO

ROWS

*sdfsdaf
 N  ZF

 L  R1
*sdafsd
 L  R2

COLUMNS
*
    C1        ZF        1.0            R1        3.0

    C1        R2        1.0
*sdafsdafsf
    C2        ZF        0.2            R1        2.0
    C2        R2        0.3
*   Marker-Datens‰tze f¸r gemischt-ganzzahlige Modelle

*   INT000    'MARKER'                           'INTORG'

*   B1        ZF        2.0            R1        2.0
*   INT001    'MARKER'                           'INTEND'

RHS
*fdsafsdaf

    RHS       R1        12.0           R2        9.0

*sdfsda

RANGES
* obere/untere Schranken einzelner Restrektionen
BOUNDS
* obere/untere Schranken einzelner Varaiblen
ENDATA
