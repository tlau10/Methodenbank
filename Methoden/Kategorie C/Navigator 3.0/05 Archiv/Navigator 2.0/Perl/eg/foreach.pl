# Here is a sample that shows how to use ADO
# and foreach on an OLE object
#
# This assumes that the AdventureWorks sample
# that comes with IIS has been installed
# 

use OLE;

$Conn = CreateObject OLE "ADODB.Connection";
$Conn->Open("ADOSamples");
$RS = $Conn->Execute("SELECT * FROM Orders");

$Fields = $RS->Fields;
foreach $field (keys %$Fields) {
	print $field->name, "\n";
}
print "\n";

while(!$RS->EOF) {
	$Fields = $RS->Fields;
	foreach $field (keys %$Fields) {
		print $field->value, " ";
	}
	print "\n";
	$RS->MoveNext;
}
$RS->Close;
$Conn->Close;
