NAME 	Maschinenbelegungsplanung MINIMIZE
ROWS
 N  ZF
 E  R1
 E  R2
 E  R3
 L  R4
 L  R5
 L  R6
 L  R7
 L  R8
 L  R9
 L  R10
 L  R11
COLUMNS
    Y         R5        -2.
    Y         R4        -1.
    Y         R7        -1.
    Y         R6        -2.
    Y         ZF        1.
    A3P3      R3        1.
    A3P3      R6        1.
    A3P3      R10       1.
    A2P4      R11       1.
    A2P4      R7        1.
    A2P4      R2        1.
    A2P3      R10       1.
    A2P3      R6        1.
    A2P3      R2        1.
    A2P2      R5        1.
    A2P2      R9        1.
    A2P2      R2        1.
    A1P2      R1        1.
    A1P2      R5        1.
    A1P2      R9        1.
    A1P1      R4        1.
    A1P1      R8        1.
    A1P1      R1        1.
RHS
    MYRHS     R1        80.
    MYRHS     R2        100.
    MYRHS     R3        40.
    MYRHS     R4        0.
    MYRHS     R5        0.
    MYRHS     R6        0.
    MYRHS     R7        0.
    MYRHS     R8        60.
    MYRHS     R9        120.
    MYRHS     R10       120.
    MYRHS     R11       60.
ENDATA
