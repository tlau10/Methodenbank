package Win32::Semaphore;
use Win32::IPC;
require Exporter;
require DynaLoader;
require AutoLoader;

@ISA = qw(Exporter DynaLoader Win32::IPC);

=head1	NAME

Win32::Semaphore - allow synchronization with Win32 Semaphore objects.

=head1 SYNOPSIS
	use Win32::Semaphore;

	Win32::Semaphore::Create($SemObj,0,1,"MySem");
	$SemObj->Wait(INFINITE);


=head1 DESCRIPTION

This module allows the user to access Win32 semaphores.

=head1 METHODS

 Win32::Semaphore::Create( $SemObject,	#container for the sem objects
 						$Initial,	#initial count of the semaphore
						$Max, 		#max count of the semaphore
						$Name )		#name (string)
	Creates a semaphore object.

 Win32::Semaphore::Open( $SemObject,	#container for the sem object
 					  $Name )		#name of the semaphore to open.

	Open an already created named semaphore.

$SemObj->Release($Count,		#amount to increase semaphore count.
				 $lastval		#previous value of the semaphore count.

	Release ownership of a semphore object.

$SemObj->Wait($TimeOut)		

	Wait for ownership of a semaphore object.



=cut

bootstrap Win32::Semaphore

# Preloaded methods go here.

# Autoload methods go after __END__, and are processed by the autosplit program.

1;
__END__
