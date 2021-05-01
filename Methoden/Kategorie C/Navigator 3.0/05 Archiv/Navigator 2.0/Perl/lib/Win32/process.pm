package Win32::Process;

use Win32::IPC;
require Exporter;
require DynaLoader;
require AutoLoader;
@ISA = qw(Exporter DynaLoader Win32::IPC);
=head1 NAME

Win32::Process - Create and manipulate processes.

=head1 SYNOPSIS
	use Win32::Process;
	use Win32;        
	
	sub ErrorReport{
		print Win32::FormatMessage( Win32::GetLastError() );
	}

		Win32::Process::Create($ProcessObj,
						"D:\\winnt35\\system32\\notepad.exe",
						"notepad temp.txt",
						0,
						NORMAL_PRIORITY_CLASS,
						".")|| die ErrorReport();
		
		$ProcessObj->Suspend();
		$ProcessObj->Resume();
		$ProcessObj->Wait(INFINITE);




=head1  DESCRIPTION

This module allows for control of processes in Perl.

=head1 METHODS

	Win32::Process::Create($ProcessObj,        #container for process object
						$AppName,               #full path name of executable module
						$CommLine,              #command line args
						$Inherit,               #flag: inherit calling processes handles or no.
						$CreateFlags,   #flags for creation, see exported var below.)
						$InitialDir )   #working dir of new process.

	$ProcessObj->Suspend();
		Suspend the process associated with the $ProcessObj;

	$ProcessObj->Resume()
		Resume a suspended process

	$ProcessObj->Kill( $ExitCode );
		Kill the associated process, have it die with exit code 
			$ExitCode

	$ProcessObj->GetPriorityClass($class)
		Get the priority class of the process

	$ProcessObj->SetPriorityClass( $class )
		Set the priority class of the process
		( See exported values below for options).

	$ProcessObj->GetExitCode( $ExitCode )
		retrieve the exitcode of the process.

	$ProcessObj->Wait($Timeout)
		wait for the process to die. forever = INFINITE




=cut

# Items to export into callers namespace by default. Note: do not export
# names by default without a very good reason. Use EXPORT_OK instead.
# Do not simply export all your public functions/methods/constants.
@EXPORT = qw(
	CREATE_DEFAULT_ERROR_MODE
	CREATE_NEW_CONSOLE
	CREATE_NEW_PROCESS_GROUP
	CREATE_NO_WINDOW
	CREATE_SEPARATE_WOW_VDM
	CREATE_SUSPENDED
	CREATE_UNICODE_ENVIRONMENT
	DEBUG_ONLY_THIS_PROCESS
	DEBUG_PROCESS
	DETACHED_PROCESS
	HIGH_PRIORITY_CLASS
	IDLE_PRIORITY_CLASS
	INFINITE
	NORMAL_PRIORITY_CLASS
	REALTIME_PRIORITY_CLASS
	THREAD_PRIORITY_ABOVE_NORMAL
	THREAD_PRIORITY_BELOW_NORMAL
	THREAD_PRIORITY_ERROR_RETURN
	THREAD_PRIORITY_HIGHEST
	THREAD_PRIORITY_IDLE
	THREAD_PRIORITY_LOWEST
	THREAD_PRIORITY_NORMAL
	THREAD_PRIORITY_TIME_CRITICAL
);

sub AUTOLOAD {
    # This AUTOLOAD is used to 'autoload' constants from the constant()
    # XS function.  If a constant is not found then control is passed
    # to the AUTOLOAD in AutoLoader.

    my($constname);
    ($constname = $AUTOLOAD) =~ s/.*:://;
    #reset $! to zero to reset any current errors.
    $!=0;
    my $val = constant($constname, @_ ? $_[0] : 0);
    if ($! != 0) {
	if ($! =~ /Invalid/) {
	    $AutoLoader::AUTOLOAD = $AUTOLOAD;
	    goto &AutoLoader::AUTOLOAD;
	}
	else {
	    ($pack,$file,$line) = caller;
	    die "Your vendor has not defined Win32::Process macro $constname, used at $file line $line.";
	}
    }
    eval "sub $AUTOLOAD { $val }";
    goto &$AUTOLOAD;
}

bootstrap Win32::Process;

# Preloaded methods go here.

# Autoload methods go after __END__, and are processed by the autosplit program.

1;
__END__
