@rem = '--*-Perl-*--';
@rem = '
@echo off
if exist perl.exe goto perlhere
cd bin
if not exist perl.exe goto perlnotthere
:perlhere
if not exist Perl300.dll goto perlnotthere
if not exist Perlglob.exe goto perlnotthere
perl.exe PerlW32-install.bat %1 %2 %3 %4 %5 %6 %7 %8 %9
goto endofperl
@rem ';

BEGIN{
	@INC = qw( ..\Lib ..\Ext );

};

require "NT.ph";
# xxx get rid of this dpreciated syntax !!!!

#(c) 1996 Microsoft Corporation. All rights reserved. 
#	Developed by ActiveWare Internet Corp., http://www.ActiveWare.com


# TODO xxxx check if a copy of Perl is already installed
# --- important on systems that have Res Kit installed
# get the version of our binary, then cd up a level
# and exec perl.exe -v to see if it exists -- note
# that our new binary could alread be in path, so check
# build info to see if it is what we get back if we
# exec ourselves


print <<'--end--';
This install script assumes that you have unpacked 
Perl for Win32 into the final intended destination 
directory.
Do you wish to proceed? [Y/n]
--end--
$in = <STDIN>;
until ( $in eq "\n" || $in =~ /^y/i ) {
	exit if ( $in =~ /^no?\n$/i );
	print "Do you wish to proceed? [Y/n]";
	$in = <STDIN>;
}


chop( $perldir = `cd` );
$bindir = $perldir;
$perldir =~ s/\\bin$//mi;

print "perldir = $perldir\n";

$libdir = $perldir . '\lib';
$docdir = $perldir . '\docs';

require 'ctime.pl' || warn "$!\nDid you preserve the directory structure when you unzipped?\n";

$date = ctime( time );

if( Win32::IsWinNT() ){
	( $myversion ) = grep( /perl[^\d]*(\d\.\d\.?\d\.?\d)/, `perl -v` );
	( $myversion ) = $myversion =~ /perl[^\d]*(\d\.(\d\.?)+)/;
}
else{
	$myversion=95;
	}
	

open ( LOG, '>>install.log' ) || warn "Couldn't open log file!\n";

&log( $date );

sub log {
    ( $message ) = @_;
    print LOG $message;
    print $message;
}

sub gripe {
    ( $message ) = @_;
    print LOG $message;
    warn $message;
}

&log( "Installing Perl for Win32 into $perldir\n" );

Win32::RegCreateKeyEx( &HKEY_LOCAL_MACHINE, 'SOFTWARE\ActiveWare\Perl5',
    &NULL, 'NT Perl 5', &REG_OPTION_NON_VOLATILE, &KEY_ALL_ACCESS, &NULL,
    $hkey, $disposition ) ?
    &log( "Added key to HKEY_LOCAL_MACHINE\\SOFTWARE\\ActiveWare\\Perl5 ..\n" ):
    &gripe( "Couldn't add key to HKEY_LOCAL_MACHINE\\SOFTWARE\\ActiveWare\\Perl5!!\n" );

if ( $disposition  == &REG_OPENED_EXISTING_KEY ) {
    &gripe( "Key exists already, modifying existing key...\n" );
}

Win32::RegSetValueEx( $hkey, 'BIN', &NULL, &REG_SZ, "$bindir" ) ?
    &log( "Adding $bindir to script path information\n" ):
    &gripe( "Couldn't add script path to registry!!\n" );

Win32::RegSetValueEx( $hkey, 'PRIVLIB', &NULL, &REG_SZ, "$libdir" ) ?
    &log( "Adding $libdir to library include path information\n" ):
    &gripe( "Couldn't add library path to registry!!\n" );

Win32::RegCloseKey( $hkey );

$fred = 'foo bar baz';

until ( $fred =~ /^(y|n)/i || $fred eq '' ) {
    print "Modify search path? [Y/n]";
    chop( $fred = <STDIN> );
}

unless ( $fred =~ /^n/i ) {
        if( Win32::IsWinNT() ){
            Win32::RegOpenKeyEx ( &HKEY_LOCAL_MACHINE,
                'SYSTEM\CurrentcontrolSet\control\Session Manager\Environment',
                &NULL, &KEY_ALL_ACCESS, $hkey ) ?
                &log( "Retrieving Path information from session manager\n" ):
                &gripe( "Couldn't retrieve path information from session manager!!\n" );
        
            Win32::RegQueryValueEx( $hkey, 'Path', &NULL, $type, $pathstring );
        
            $pathstring =
                "$bindir;" . join (';', grep(!/perl/i, split(/;/, $pathstring))) . ";";
        
            Win32::RegSetValueEx( $hkey, 'Path', &NULL, $type, $pathstring ) ?
                &log( "Updated path information in session manager\n" ):
                &gripe( "Couldn't update path information in session manager!!\n" );
            Win32::RegCloseKey( $hkey );
        }
       else{ # must be win95, so update path in autoexec.bat
        
        &log("Attempting to change path in autoexec.bat\n");
        ( $bootdrive = $ENV{'windir'} ) =~ s|\\.*||g;
        &log( "bootdrive is $bootdrive\n");

        open( ABAT,">>$bootdrive\\autoexec.bat") ||
                gripe( "Couldn't open $bootdrive\\autoexec.bat");

        print ABAT "path \"%path%;$bindir;\"\n";
        close ABAT;

       }

}

if( Win32::IsWinNT() ){
	$fred = 'foo bar baz';
	until ( $fred =~ /^(y|n)/i || $fred eq '' ) {
		print "Associate .pl with Perl.exe? [Y/n]";
		chop( $fred = <STDIN> );
	}
	unless ( $fred =~ /^n/i ) {
		($sCSDVersion, $MajorVersion, $MinorVersion, $BuildNumber, $PlatformId) = Win32::GetOSVersion;
		if($MajorVersion >= 4)
		{
			&log( "Associating .pl extension with Perl.exe.\n" );
			system("assoc .pl=Perl");
			system("ftype Perl=$bindir\\perl.exe \%1 \%*");
			#
			# unable to locate where PATHEXT is in the registry
			# `set PATHEXT=%PATHEXT%;.pl`;
			# print "\nIf foo.pl is in the PATH, typing foo at any command prompt will run it.\n\n";
			#
			print "\nIf foo.pl is in the PATH, typing foo.pl at any command prompt will run it.\n\n";
		}
	}
}

if( Win32::IsWinNT() ){
	if(Win32::RegOpenKeyEx (&HKEY_LOCAL_MACHINE,
				'SYSTEM\CurrentControlSet\Services\W3SVC\Parameters',
				&NULL,&KEY_ALL_ACCESS,$Servkey)) {
		$fred = 'foo bar baz';
		until ( $fred =~ /^(y|n)/i || $fred eq '' ) {
			print "Add registry key to support standard I/O redirection in IIS? [Y/n]";
			chop( $fred = <STDIN> );
		}
		unless ( $fred =~ /^n/i ) {
			Win32::RegSetValueEx( $Servkey, "CreateProcessWithNewConsole", &NULL, &REG_DWORD, 1 ) ?
				&log( "Added CreateProcessWithNewConsole value for IIS\n" ) :
				gripe( "Adding CreateProcessWithNewConsole value for IIS failed!!\n" );
		}
		Win32::RegCloseKey( $Servkey );
	}
}

Win32::RegisterServer('PerlMsg.dll');

finishUp:
&log( "Perl $myversion installation finished.\n" );
print "NOTE: The updated path information will not take effect\n";
if( Win32::IsWinNT() ){
        print "      until you log off and back on again. enjoy ;-)\n";
}
else{
        print "      until you reboot your machine. enjoy ;-)\n";
}


__END__
:perlnotthere
echo Could not find Perl interpreter!!
echo *gasp* *wheez* *choke*
echo Did you build it? I looked in
@echo off
cd
:endofperl
notepad ..\docs\Perl-Win32\release.txt
pause
