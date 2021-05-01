package Win32::File;

#
#File.pm
#Written by Douglas_Lankshear@ActiveWare.com
#

require Exporter;
require DynaLoader;

@ISA= qw( Exporter DynaLoader );
# Items to export into callers namespace by default. Note: do not export
# names by default without a very good reason. Use EXPORT_OK instead.
# Do not simply export all your public functions/methods/constants.
@EXPORT = qw(
		ARCHIVE
		COMPRESSED
		DIRECTORY
		HIDDEN
		NORMAL
		READONLY
		SYSTEM
	);

=head1 NAME

Win32::File - manage file attributes in perl

=head1 SYNOPSIS

	use Win32::File;

=head1 DESCRIPTION

This module offers the retrieval and setting of file attributes.

=head1 FUNCTIONS

=head2 NOTE:
all of the functions return FALSE (0) if they fail, unless otherwise noted.

=over 10

=item GetAttributes(filename, returnedAttirbutes)
	Gets the attributes of a file or directory

=item SetAttributes(filename, newAttirbutes)
	Sets the attributes of a file or directory

=back

=cut

sub AUTOLOAD 
{
    # This AUTOLOAD is used to 'autoload' constants from the constant()
    # XS function.  If a constant is not found then control is passed
    # to the AUTOLOAD in AutoLoader.

    my($constname);
    ($constname = $AUTOLOAD) =~ s/.*:://;
    #reset $! to zero to reset any current errors.
    $!=0;
    my $val = constant($constname, @_ ? $_[0] : 0);
    if($! != 0)
	{
		if($! =~ /Invalid/)
		{
			$AutoLoader::AUTOLOAD = $AUTOLOAD;
			goto &AutoLoader::AUTOLOAD;
		}
		else 
		{
			($pack,$file,$line) = caller;
			die "Your vendor has not defined Win32::File macro $constname, used in $file at line $line.";
		}
    }
    eval "sub $AUTOLOAD { $val }";
    goto &$AUTOLOAD;
}

bootstrap Win32::File;

# Preloaded methods go here.

# Autoload methods go after __END__, and are processed by the autosplit program.

1;
__END__
	









