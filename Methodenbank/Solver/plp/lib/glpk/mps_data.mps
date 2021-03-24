NAME          Power-LP MAXIMIZE
ROWS
 N  ZF
 L  R1
 L  R2
 L  R3
COLUMNS
    X1        ZF        300
    X1        R1        1
    X1        R2        1
    X1        R3        0
    X2        ZF        500
    X2        R1        2
    X2        R2        1
    X2        R3        3
    X3        ZF        -140
    X3        R1        -1
    X3        R2        0
    X3        R3        0
    X4        ZF        -80
    X4        R1        0
    X4        R2        -1
    X4        R3        0
    X5        ZF        -125
    X5        R1        0
    X5        R2        0
    X5        R3        -1
    X6        ZF        -36000
    X6        R1        0
    X6        R2        0
    X6        R3        0
RHS
    MYRHS     R1        170
    MYRHS     R2        150
    MYRHS     R3        180
BOUNDS
 LO MYBOUND   X1        0
 UP MYBOUND   X1        0
 LO MYBOUND   X2        0
 UP MYBOUND   X2        0
 LO MYBOUND   X3        0
 UP MYBOUND   X3        30
 LO MYBOUND   X4        0
 UP MYBOUND   X4        30
 LO MYBOUND   X5        0
 UP MYBOUND   X5        30
 LO MYBOUND   X6        1
 UP MYBOUND   X6        1
ENDATA
