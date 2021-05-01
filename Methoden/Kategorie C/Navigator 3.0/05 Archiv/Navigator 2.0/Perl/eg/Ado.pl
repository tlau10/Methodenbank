# Here is a sample that shows how to use ADO
#
# This assumes that the AdventureWorks sample
# that comes with IIS has been installed
# 


use OLE;

$Conn = CreateObject OLE "ADODB.Connection";
$Conn->Open("ADOSamples");
$RS = $Conn->Execute("SELECT * FROM Orders");


$Count = $RS->Fields->count;
for($i = 0; $i < $Count; ++$i) {
	print $RS->Fields($i)->name , "\n";
}

while(! $RS->EOF) {
	for ( $i = 0; $i < $Count; $i++ ) {
		print $RS->Fields($i)->value, "  ";
	}
	print "\n";
	$RS->MoveNext;
}
$RS->Close;
$Conn->Close;


