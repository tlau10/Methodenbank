UNIT lp_oinit; 
{$O+,F+} 
{$D-,E-,G+,L-,N+,R-,S-,V-,X-,Y-} 
INTERFACE 
USES OVERLAY; 
IMPLEMENTATION 
BEGIN 
  OVRINIT('LP.OVR');
  OVRINITEMS; 
END. 
