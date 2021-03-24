NAME          ALLOY BLENDING                                            
ROWS
 N  COST    
 E  YIELD   
 L  FE      
 L  CU      
 L  MN      
 L  MG      
 G  AL      
 L  SI      
COLUMNS
    BIN1      COST       0.030000000   YIELD      1.000000000
    BIN1      FE         0.150000000   CU         0.030000000
    BIN1      MN         0.020000000   MG         0.020000000
    BIN1      AL         0.700000000   SI         0.020000000
    BIN2      COST       0.080000000   YIELD      1.000000000
    BIN2      FE         0.040000000   CU         0.050000000
    BIN2      MN         0.040000000   MG         0.030000000
    BIN2      AL         0.750000000   SI         0.060000000
    BIN3      COST       0.170000000   YIELD      1.000000000
    BIN3      FE         0.020000000   CU         0.080000000
    BIN3      MN         0.010000000   AL         0.800000000
    BIN3      SI         0.080000000
    BIN4      COST       0.120000000   YIELD      1.000000000
    BIN4      FE         0.040000000   CU         0.020000000
    BIN4      MN         0.020000000   AL         0.750000000
    BIN4      SI         0.120000000
    BIN5      COST       0.150000000   YIELD      1.000000000
    BIN5      FE         0.020000000   CU         0.060000000
    BIN5      MN         0.020000000   MG         0.010000000
    BIN5      AL         0.800000000   SI         0.020000000
    ALUM      COST       0.210000000   YIELD      1.000000000
    ALUM      FE         0.010000000   CU         0.010000000
    ALUM      AL         0.970000000   SI         0.010000000
    SILCON    COST       0.380000000   YIELD      1.000000000
    SILCON    FE         0.030000000   SI         0.970000000
RHS
    MYRHS     YIELD     2000.0000000
    MYRHS     FE        60.000000000
    MYRHS     CU        100.00000000
    MYRHS     MN        40.000000000
    MYRHS     MG        30.000000000
    MYRHS     AL        1500.0000000
    MYRHS     SI        300.00000000
RANGES
    MYRANGE   SI        50.000000000
BOUNDS
 UP MYBOUND   BIN1      200.00000000
 UP MYBOUND   BIN2      750.00000000
 LO MYBOUND   BIN3      400.00000000
 UP MYBOUND   BIN3      800.00000000
 LO MYBOUND   BIN4      100.00000000
 UP MYBOUND   BIN4      700.00000000
 UP MYBOUND   BIN5      1500.0000000
ENDATA
HS     CU        100.00000000
    MYRHS     MN        40.000000000
    MYRHS     MG        30.000000000
    MYRHS     AL        1500.0000000
    MYRHS     SI        300.00000000
RANGES
    MYRANGE   SI        50.000000000
BOUND