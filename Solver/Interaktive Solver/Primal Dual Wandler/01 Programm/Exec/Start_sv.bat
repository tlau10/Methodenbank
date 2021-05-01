@echo off

::  Die Datendateien können vom Netzlaufwerk nicht geöffnet werden.
::  Deshalb werden sie auf das Temp-Verz. kopiert.
::


copy  \\Neptun\Lehre\Besf\Solver\PrimalDual\Daten\*.pdm  c:\temp\


\\Neptun\Lehre\Besf\Solver\PrimalDual\Exec\PrimalDual.exe


rem del c:\temp\*.pdm
