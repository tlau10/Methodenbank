@rem = '--*-Perl-*--';
@rem = '
@echo off
if not exist perl.exe goto perlnotthere
if not exist Perl300.dll goto perlnotthere
perl.exe PerlW32-uninstall.bat %1 %2 %3 %4 %5 %6 %7 %8 %9
pause
goto endofperl
@rem ';

@INC = qw( ..\Lib ..\Ext );
require 'NT.ph';

#(c) 1995 Microsoft Corporation. All rights reserved. 
#	Developed by ActiveWare Internet Corp., http://www.ActiveWare.com

print <<'--end--';
PerlW32-uninstall.bat:
  This script will remove the registry entries created by the 
  PerlW32-install.bat script.  The script does not delete files.
  You can re-install by running PerlW32-install.bat again. 
Continue?(Y/N):
--end--

$in = <STDIN>;
until ( $in eq "\n" || $in =~ /^y/i ) {
	exit if ( $in =~ /^no?\n$/i );
	print "Do you wish to proceed? [Y/n]";
	$in = <STDIN>;
}

#check the registry entries
Win32::RegOpenKeyEx(&HKEY_LOCAL_MACHINE, 'SOFTWARE\ActiveWare\Perl5',
		&NULL, &KEY_ALL_ACCESS, $PerlKey) ?
	print "Retrieving Perl dir information from Registry\n":
	warn "Couldn't retrieve Perl dir information from Registry!!\n";

Win32::RegQueryValueEx($PerlKey, 'BIN', &NULL, $type, $bindir);
  
Win32::RegDeleteValue($PerlKey, 'BIN') ?
	print "Deleting BIN value from registry\n" :
	warn "Cannot delete BIN value from registry!!!\n";

Win32::RegDeleteValue($PerlKey, 'PRIVLIB') ?
	print "Deleting PRIVLIB value from registry\n" :
	warn "Cannot delete PRIVLIB value from registry!!!\n";

Win32::RegDeleteKey(&HKEY_LOCAL_MACHINE, 'SOFTWARE\ActiveWare\Perl5') ?
	print "Removing Perl from Registry\n" :
	warn "Cannot remove Perl from Registry!!!\n";

Win32::RegCloseKey($PerlKey);

#remove an associations
if( Win32::IsWinNT() ){
	($sCSDVersion, $MajorVersion, $MinorVersion, $BuildNumber, $PlatformId) = Win32::GetOSVersion;
	if($MajorVersion >= 4)
	{
		`assoc .pl=`;
		`ftype Perl=`;
	}
}

#remove the bin directory from the path.
if( Win32::IsWinNT()){
    Win32::RegOpenKeyEx( &HKEY_LOCAL_MACHINE,
	'SYSTEM\CurrentcontrolSet\control\Session Manager\Environment',
	&NULL, &KEY_ALL_ACCESS, $hkey ) ?
	print "Retrieving Path information from session manager\n":
	warn "Couldn't retrieve path information from session manager!!\n";

    Win32::RegQueryValueEx($hkey, 'Path', &NULL, $type, $pathstring);

    $pathstring =~ s/$bindir;//g;

    Win32::RegSetValueEx($hkey, 'Path', &NULL, $type, $pathstring) ?
	print "Updated path information in session manager\n" :
	warn "Couldn't update path information in session manager!!\n";

    Win32::RegCloseKey($hkey);
}


print "NOTE: The changes to the path will not take effect\n";
print "      until you log off and on again.\n";


__END__
:perlnotthere
echo Could not find Perl interpreter!!
echo *gasp* *wheez* *choke*
:endofperl
