package Win32::Mutex;
use Win32::IPC;
require Exporter;
require DynaLoader;
require AutoLoader;

@ISA = qw(Exporter DynaLoader Win32::IPC);

=head1 NAME

Win32::Mutex - create mutex objects in perl.

=head1 SYNOPSIS

	use Win32::Mutex;

	Win32::Mutex::Create($MutObj,$Initial,$Name);
	$MutObj->Wait(INFINITE);

=head1 DESCRIPTION
This module creates access to the Win32 mutex objects.  The Wait and
WaitForMultipleObjects calls are inherited from the Win32::IPC module.

=head1 METHODS AND CONSTRUCTORS

	Create($MutObj,$Initial,$Name)
		Creates a mutex object in $MutObj.

		$Initial	Flags to decide initial ownership.(TRUE = I own it).
		$Name		String to name the mutex.

	Open($MutObj,$Name)
		Create a mutex object from an already created mutex

		$MutObj		Container for mutex object.
		$Name		name of already created mutex.

	$MutObj->Release();
		Release ownership of a mutex.

	$MutObj->Wait($Timeout)
		wait for ownership of a mutex.

		$Timeout	amount of time you will wait.forever = INFINITE;



=cut

bootstrap Win32::Mutex;

# Preloaded methods go here.

# Autoload methods go after __END__, and are processed by the autosplit program.

1;
__END__
