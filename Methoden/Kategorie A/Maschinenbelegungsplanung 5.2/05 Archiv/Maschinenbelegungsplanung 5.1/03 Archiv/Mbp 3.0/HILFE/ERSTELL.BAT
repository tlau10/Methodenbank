@echo off

rem ..Online-Hilfe erstellen mit HC31

if exist hilfetxt.err del hilfetxt.err

hc31 hilfetxt.hpj

rem if exist hilfetxt.err list hilfetxt.err

