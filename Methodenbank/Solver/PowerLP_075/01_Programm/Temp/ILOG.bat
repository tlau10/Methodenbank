@echo off

cplex.exe -c "read ILOG.lp " "optimize" "display solution variables -" "write cplex.exe" "quit" 
