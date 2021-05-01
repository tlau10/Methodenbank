#Const xlRows = 1
#
#Set application = CreateObject("Excel.Application")
#application.Visible = True
#Set workbook = application.Workbooks.Add(template:=xlWorkSheet)
#Set worksheet = workbook.Worksheets(1)
#worksheet.Range("A1:D1").Value = Array("North", "South", "East", "West")
#worksheet.Range("A2").Value = 5.2
#worksheet.Range("B2").Value = 10
#worksheet.Range("C2").Value = 8
#worksheet.Range("D2").Value = 20
#Set range = worksheet.Range("A1:D2")
#Set chart = workbook.Charts.Add
#chart.ChartWizard Source:=range, gallery:=xl3DPie,
#    Format:=7, plotBy:=xlRows, categoryLabels:=1,
#        seriesLabels:=0, hasLegend:=2, Title:="Sales Percentages"
#        workbook.Saved = True

use OLE;

$application = CreateObject OLE 'Excel.Application' || die $!;

$application->{'Visible'} = 1;
$workbook = $application->Workbooks->Add();
$worksheet = $workbook->Worksheets(1);
$worksheet->Range("A1:D1")->{'Value'} = ["North","South","East","West"];
$worksheet->Range("A2")->{'Value'} = 5.2;
$worksheet->Range("B2")->{'Value'} = 10;
$worksheet->Range("C2")->{'Value'} = 8;
$worksheet->Range("D2")->{'Value'} = 20;

$range = $worksheet->Range("A1:D2");
$chart = $workbook->Charts->Add;

#$chart->ChartWizard(range,"x13DPie",7,"x1Rows",1,0,2,"Sales Percentages" );

$workbook->{'Saved'} = 1;

$application->ActiveWorkbook->Close(0);
$application->Quit();

