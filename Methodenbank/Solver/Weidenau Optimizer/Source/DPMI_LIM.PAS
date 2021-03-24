UNIT dpmi_limit;
INTERFACE
IMPLEMENTATION
BEGIN
  system.heaplimit:=8192;
  system.heapblock:=system.heaplimit*4;
END.