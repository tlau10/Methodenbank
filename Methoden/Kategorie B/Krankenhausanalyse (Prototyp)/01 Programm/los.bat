@echo off


if exist ergebnis.txt del ergebnis.txt

lp_solve.exe <formeln.txt >ergebnis.txt


if exist ergebnis.txt notepad ergebnis.txt

