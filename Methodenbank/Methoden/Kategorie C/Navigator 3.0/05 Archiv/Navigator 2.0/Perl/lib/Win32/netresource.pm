package Win32::NetResource;

require Exporter;
require DynaLoader;
require AutoLoader;

@ISA = qw(Exporter DynaLoader);
# Items to export into callers namespace by default. Note: do not export
# names by default without a very good reason. Use EXPORT_OK instead.
# Do not simply export all your public functions/methods/constants.
@EXPORT = qw(
	RESOURCEDISPLAYTYPE_DOMAIN
	RESOURCEDISPLAYTYPE_FILE
	RESOURCEDISPLAYTYPE_GENERIC
	RESOURCEDISPLAYTYPE_GROUP
	RESOURCEDISPLAYTYPE_SERVER
	RESOURCEDISPLAYTYPE_SHARE
	RESOURCEDISPLAYTYPE_TREE
	RESOURCETYPE_ANY
	RESOURCETYPE_DISK
	RESOURCETYPE_PRINT
	RESOURCETYPE_UNKNOWN
	RESOURCEUSAGE_CONNECTABLE
	RESOURCEUSAGE_CONTAINER
	RESOURCEUSAGE_RESERVED
	RESOURCE_CONNECTED
	RESOURCE_GLOBALNET
	RESOURCE_REMEMBERED
);

=head1 NAME

Win32::NetResource - manage network resources in perl

=head1 SYNOPSIS

	use Win32::NetResource;

	$ShareInfo = {
			        'path' => "C:\\MyShareDir",
					'netname' => "MyShare",
					'remark' => "It is good to share",
					'passwd' => "",
					'current-users' =>0,
					'permissions' => 0,
					'maxusers' => -1,
					'type'  => 0,
					};
	
   Win32::NetResource::NetShareAdd( $ShareInfo,$parm )|| die "unable to add share\n";


=head1 DESCRIPTION

This module offers control over the network resources of Win32.Disks,
printers etc can be shared over a network.

=head1 DATA TYPES
There are two main data types required to control network resources.
In Perl these are represented by hash types.

=over 10
=item %NETRESOURCE

		KEY				VALUE
		'Scope'			=>	Scope of an Enumeration
							RESOURCE_CONNECTED,
							RESOURCE_GLOBALNET,
							RESOURCE_REMEMBERED.

		'Type'			=>	The type of resource to Enum
							RESOURCETYPE_ANY	All resources
							RESOURCETYPE_DISK	Disk resources
							RESOURCETYPE_PRINT	Print resources

		'DisplayType'	=>	The way the resource should be displayed.
							RESOURCEDISPLAYTYPE_DOMAIN	
							The object should be displayed as a domain.
							RESOURCEDISPLAYTYPE_GENERIC	
							The method used to display the object does not matter.
							RESOURCEDISPLAYTYPE_SERVER	
							The object should be displayed as a server.
							RESOURCEDISPLAYTYPE_SHARE	
							The object should be displayed as a sharepoint.

		'Usage' 		=>		Specifies the Resources usage:
								RESOURCEUSAGE_CONNECTABLE
								RESOURCEUSAGE_CONTAINER.

		'LocalName'		=>		Name of the local device the resource is 
								connected to.

		'RemoteName'	=>		The network name of the resource.
		'Comment'		=>		A string comment.
		'Provider'		=>		Name of the provider of the resource.

=back	
=item %SHARE_INFO

This hash represents the SHARE_INFO_502 struct.
=over 10
		KEY					VALUE
		'netname'		=>	Name of the share.
		'type'			=>	type of share.
		'remark'		=>	A string comment.
		'permissions'	=>	Permissions value
		'maxusers' 		=>	the max # of users.
		'current-users'	=>	the current # of users.
		'path'			=>	The path of the share.
		'passwd'		=>	A password if one is req'd

=back
=head1 FUNCTIONS

=head2 NOTE:
all of the functions return FALSE (0) if they fail.

=over 10

=item GetSharedResources(\@Resources,dwType)
	Creates a list in @Resources of %NETRESOURCE hash references.

=item AddConnection(\%NETRESOURCE,$Password,$UserName,$Connection)
	Makes a connection to a network resource specified by %NETRESOURCE

=item CancelConnection($Name,$Connection,$Force)
	Cancels a connection to a network resource connected to local device 
	$name.$Connection is either 1 - persistent connection or 0, non-persistent.

=item WNetGetLastError($ErrorCode,$Description,$Name)
	Gets the Extended Network Error.

=item GetError( $ErrorCode )
	Gets the last Error for a Win32::NetResource call.

=item GetUNCName( $UNCName, $LocalPath );
	returns the UNC name of the disk share connected to $LocalPath in $UNCName.

=head2 servername is optional for all the calls below. ( if not given the local machine is assumed. )

=item NetShareAdd(\%SHARE,$parm_err,$servername = NULL )
	Add a share for sharing.

=item NetShareCheck($device,$type,$servername = NULL )
	Check if a share is available for connection.

=item NetShareDel( $netname, $servername = NULL )
	Remove a share from a machines list of shares.

=item NetShareGetInfo( $netname, \%SHARE,$servername=NULL )
	Get the %SHARE_INFO information about the share $netname on the server $servername.

=item NetShareSetInfo( $netname,\%SHARE,$parm_err,$servername=NULL)
	Set the information for share $netname.

=back

=cut

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
	    die "Your vendor has not defined Win32::NetResource macro $constname, used at $file line $line.
";
	}
    }
    eval "sub $AUTOLOAD { $val }";
    goto &$AUTOLOAD;
}

sub AddConnection
{
	local $NetResource = _hash2NET( $_[0] );

	_AddConnection($NetResource,$_[1],$_[2],$_[3]);
}



sub GetSharedResources
{
	( ref $_[0] == ARRAY )||
		die "GetSharedResources: ARRAY reference required\n";

	local $Aref = [];
	local $Href;
	local $Ret;

	# Get the shared resources.

	$Ret = _GetSharedResources( $Aref ,$_[1] );
	
	#build the array of hashes in $_[0]
	
	if ($Ret != 0) {
		foreach ( @$Aref ){
			$Href = _NET2hash( $_ );
			push( @{$_[0]}, $Href );
		}
	}

	$Ret;
}

sub NetShareAdd
{
	local $ShareInfo;
	$ShareInfo = _hash2SHARE( $_[0] );
	_NetShareAdd($ShareInfo,$_[1],$_[2]);

}

sub NetShareGetInfo
{
	local $NetInfo,$Val;
	$Val = _NetShareGetInfo( $_[0],$NetInfo,$_[2]);

	$_[1] = _SHARE2hash( $NetInfo );	

	$Val;
}

sub NetShareSetInfo
{
	local $ShareInfo;
	$ShareInfo = _hash2SHARE( $_[3] );

	_NetShareSetInfo( $_[0],$_[1],$_[2],$ShareInfo,$_[4]);

}


#These are private functions to work with the ShareInfo structure.
#please note that the implementation of these calls requires the SHARE_INFO_502 level
#of information.

sub _SHARE2hash
{
	my $ShareRef = $_[0];

	local $netname,$type,$remark,$permissions,$maxusers;
	local $current_users,$path,$passwd;
	
	($type,$permissions,$maxuses,$current_users,$remark,$netname,$path,$passwd)=
		unpack('i4 A257 A81 A257 A257',$ShareRef);

	$Hash={ 	'netname'		=>	$netname,
				'type'			=>	$type,
				'remark'		=>	$remark,
				'permissions'	=>	$permissions,
				'maxusers' 		=>	$maxuses,
				'current-users'	=>	$current_users,
				'path'			=>	$path,
				'passwd'		=>	$passwd,
			};

	$Hash;


}

sub _hash2SHARE
{
	local $Hash = $_[0];
   	local $ShareRef;

	( ref $Hash == HASH )||
		die "reference passed is not a hash reference\n";


	local $netname,$type,$remark,$permissions,$maxusers;
	local $current_users,$path,$passwd;

	$netname = $Hash->{'netname'} . "\0";
	$type		=	$Hash->{'type'} . "\0";
	$remark		=	$Hash->{'remark'} . "\0";
	$permissions=	$Hash->{'permissions'} . "\0";
	$maxusers	=	$Hash->{'maxusers'} . "\0";
	$current_users= $Hash->{'current_users'} . "\0";
	$path		=	$Hash->{'path'} . "\0";
	$passwd		=	$Hash->{'passwd'} . "\0";

	$ShareRef = pack( 'i4 A257 A81 A257 A257',$type,$permissions,$maxusers,$current_users,$remark,$netname,$path,$passwd);
	
	$ShareRef; 
}

#These are private functions to translate the NETRESOURCE structure to a hash
# for manipulation in perl.
#typedef struct _NETRESOURCE {  // nr  
#    DWORD  dwScope; 
#    DWORD  dwType; 
#    DWORD  dwDisplayType; 
#    DWORD  dwUsage; 
#    LPTSTR lpLocalName; 
#    LPTSTR lpRemoteName; 
#    LPTSTR lpComment; 
#    LPTSTR lpProvider; 
#} NETRESOURCE; 
 
 sub _NET2hash
{
	my $netref = $_[0];
 	local $Scope,$Type,$DisplayType,$Usage,$LocalName,$RemoteName,$Comment,$Provider;

	($Scope,$Type,$DisplayType,$Usage,$LocalName,$RemoteName,$Comment,$Provider) =
		unpack( "i4 p4", $netref );


$Hash = {
		'Scope'		=>		$Scope,
		'Type'		=>		$Type,
		'DisplayType'	=>	$DisplayType,
		'Usage' 	=>		$Usage,
		'LocalName'	=>		$LocalName,
		'RemoteName'	=>	$RemoteName,
		'Comment'	=>		$Comment,
		'Provider'	=>		$Provider,
		};

	$Hash;
}

sub _hash2NET
{
	my $hashref = $_[0];
	( ref $hashref == HASH )|| die "Hash reference required\n";
	 local $Scope,$Type,$DisplayType,$Usage,$LocalName,$RemoteName,$Comment,$Provider;

	$Scope 		=		$hashref->{'Scope'};
	$Type 		= 		$hashref->{'Type'};
	$DisplayType =		$hashref->{'DisplayType'};
	$Usage 		= 		$hashref->{'Usage'};
	$LocalName 	=		$hashref->{'LocalName'};
	$RemoteName =		$hashref->{'RemoteName'};
	$Comment 	=		$hashref->{'Comment'};
	$Provider	=		$hashref->{'Provider'};

	$hashref = pack( 'i4 p4',$Scope,$Type,$DisplayType,$Usage,$LocalName,$RemoteName,$Comment,$Provider);

	$hashref;
 
}


bootstrap Win32::NetResource;

# Preloaded methods go here.

# Autoload methods go after __END__, and are processed by the autosplit program.

1;
__END__
