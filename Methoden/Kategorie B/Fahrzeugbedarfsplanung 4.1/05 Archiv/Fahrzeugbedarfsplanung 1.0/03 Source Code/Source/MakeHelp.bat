@echo off
REM -- Zuerst Zuordnungsdatei der mit Microsoft Visual C++ erzeugten resource.h erstellen
echo // Von MAKEHELP.BAT erzeugte Hilfe-Zuordnungsdatei. Wird verwendet von FABEPLAN.HPJ. >"hlp\FaBePlan.hm"
echo. >>"hlp\FaBePlan.hm"
echo // Befehle (ID_* und IDM_*) >>"hlp\FaBePlan.hm"
makehm ID_,HID_,0x10000 IDM_,HIDM_,0x10000 resource.h >>"hlp\FaBePlan.hm"
echo. >>"hlp\FaBePlan.hm"
echo // Eingabeaufforderungen (IDP_*) >>"hlp\FaBePlan.hm"
makehm IDP_,HIDP_,0x30000 resource.h >>"hlp\FaBePlan.hm"
echo. >>"hlp\FaBePlan.hm"
echo // Ressourcen (IDR_*) >>"hlp\FaBePlan.hm"
makehm IDR_,HIDR_,0x20000 resource.h >>"hlp\FaBePlan.hm"
echo. >>"hlp\FaBePlan.hm"
echo // Dialogfelder (IDD_*) >>"hlp\FaBePlan.hm"
makehm IDD_,HIDD_,0x20000 resource.h >>"hlp\FaBePlan.hm"
echo. >>"hlp\FaBePlan.hm"
echo // Rahmen-Steuerelemente (IDW_*) >>"hlp\FaBePlan.hm"
makehm IDW_,HIDW_,0x50000 resource.h >>"hlp\FaBePlan.hm"
REM -- Hilfe erstellen für Projekt FABEPLAN


echo Erstelle Win32-Hilfedateien
start /wait hcw /C /E /M "hlp\FaBePlan.hpj"
if errorlevel 1 goto :Error
if not exist "hlp\FaBePlan.hlp" goto :Error
if not exist "hlp\FaBePlan.cnt" goto :Error
echo.
if exist Debug\nul copy "hlp\FaBePlan.hlp" Debug
if exist Debug\nul copy "hlp\FaBePlan.cnt" Debug
if exist Release\nul copy "hlp\FaBePlan.hlp" Release
if exist Release\nul copy "hlp\FaBePlan.cnt" Release
echo.
goto :done

:Error
echo hlp\FaBePlan.hpj(1) : Fehler: Problem beim Erstellen der Hilfedatei festgestellt

:done
echo.
