#Sub Command1_Click ()
#
#    Dim ExcelApp As Object
#    Dim ExcelChart As Object
#    Dim CharTypeVal As Integer
#    Dim i as Integer
#
#    '-4100 is the value for the Excel constant xl3DColumn. Visual
#    'Basic does not understand Excel constants, so the value must be
#    'used instead.
#    ChartTypeVal = -4100
#
#    'Creates OLE object to Excel
#    Set ExcelApp = CreateObject("excel.application")
#
#    'Sending VB Applications Edition commands to Excel via the new OLE
#    'object to create a new workbook fill in numbers, create the chart, and
#    'rotate the chart.
#
#    ExcelApp.Visible = True
#    ExcelApp.Workbooks.Add
#    ExcelApp.Range("a1").Value = 3
#    ExcelApp.Range("a2").Value = 2
#    ExcelApp.Range("a3").Value = 1
#    ExcelApp.Range("a1:a3").Select
#    Set ExcelChart = ExcelApp.Charts.Add()
#    ExcelChart.Type = ChartTypeVal
#    For i = 30 To 180 Step 10
#        ExcelChart.Rotation = i
#    Next
#
#    ExcelApp.ActiveWorkbook.Close (False)
#    ExcelApp.Quit
#    Set ExcelChart = Nothing
#    Set ExcelApp = Nothing
#    End
#End Sub
#To use this subroutine, create a form in Visual Basic and add a single
#button to that form. Double-click the button and type the code shown above
#in the procedure window. Close the procedure window, and choose Start from
#the Run menu. To see the rotating chart, click on the button in your form.
#

#set the include path.
BEGIN {

@INC = qw( ..\lib ..\ext );

}

use OLE;


#   -4100 is the value for the Excel constant xl3DColumn.

$ChartTypeVal = -4100;

#   Creates OLE object to Excel
$ExcelApp = CreateObject OLE "excel.application" || die "Unable to create Excel Object: $!\n";

# Create and rotate the chart

$ExcelApp->{'Visible'} = 1;
$ExcelApp->Workbooks->Add();
$ExcelApp->Range("a1")->{'Value'} = 3;
$ExcelApp->Range("a2")->{'Value'} = 2;
$ExcelApp->Range("a3")->{'Value'} = 1;
$ExcelApp->Range("a1:a3")->Select();
$ExcelChart = $ExcelApp->Charts->Add();
$ExcelChart->{'Type'} = $ChartTypeVal;

for($i=30;$i<180;$i+=10)
{
    $ExcelChart->{'Rotation'} = $i;
}

# Done, bye

$ExcelApp->ActiveWorkbook->Close(0);
$ExcelApp->Quit();

