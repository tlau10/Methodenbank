package Config;
require Exporter;
@ISA = (Exporter);
@EXPORT = qw(%Config);
@EXPORT_OK = qw(myconfig config_sh config_vars);

$] == 5.00307 or die sprintf
    "Perl lib version (5.00307) doesn't match executable version (%.5f)\n", $];


#### Hard coded for Win32 - WYT 1995-10-23

=head1 NAME

Config - access Perl configuration option

=head1 SYNOPSIS

    use Config;
    if ($Config{'cc'} =~ /gcc/) {
	print "built by gcc\n";
    } 

=head1 DESCRIPTION

The Config module contains everything that was available to the
C<Configure> program at Perl build time.  Shell variables from
F<config.sh> are stored in the readonly-variable C<%Config>, indexed by
their names.

=head1 EXAMPLE

Here's a more sophisticated example of using %Config:

    use Config;

    defined $Config{sig_name} || die "No sigs?";
    foreach $name (split(' ', $Config{sig_name})) {
	$signo{$name} = $i;
	$signame[$i] = $name;
	$i++;
    }   

    print "signal #17 = $signame[17]\n";
    if ($signo{ALRM}) { 
	print "SIGALRM is $signo{ALRM}\n";
    }   

=head1 NOTE

This module contains a good example of how to make a variable
readonly to those outside of it.  

=cut

$config_sh=<<'!END!OF!CONFIG!';
archlib=''
ccflags=''
cppflags=''
dynamic_ext=''
extensions='Fcntl FileHandle SDBM_File IO Socket'
intsize='4'
libpth=''
osname='MSWin32'
osvers='3.51'
sharpbang=''
shsharp=''
sig_name=''
so='dll'
startsh=''
static_ext=' '
Author=''
CONFIG='true'
Date='$Date'
Header=''
Id='$Id'
Locker=''
Log='$Log'
Mcc='Mcc'
PATCHLEVEL='1'
RCSfile='$RCSfile'
Revision='$Revision'
Source=''
State=''
afs='false'
alignbytes='4'
aphostname=''
archlibexp=
archname='i386-win32'
awk='awk'
baserev='5.0'
bash=''
bin=''
binexp=''
bison=''
byacc='byacc'
byteorder='1234'
c=''
castflags='0'
cat='type'
cc='cl'
cccdlflags=''
ccdlflags=''
cf_by=''
cf_time=''
chgrp=''
chmod=''
chown=''
clocktype=''
comm='comm'
compress=''
contains=''
cp='copy'
cpio=''
cpp='cl /E'
cpp_stuff=''
cpplast=''
cppminus=''
cpprun='cl /E'
cppstdin='cl /E'
cryptlib=''
csh='cmd'
date='date'
db_hashtype='u_int32_t'
db_prefixtype='size_t'
defvoidused='15'
direntrytype='struct dirent'
dlext='pll'
dlsrc='dl_win32.xs'
echo='echo'
egrep=''
emacs=''
eunicefix=':'
expr=''
find=''
flex=''
fpostype='fpos_t'
freetype='void'
full_csh=''
full_sed=''
gcc=''
gccversion=''
gidtype='gid_t'
glibpth=''
grep=''
groupcat=''
groupstype='gid_t'
h_fcntl='false'
h_sysfile=''
hint='recommended'
hostcat=''
huge=''
incpath=''
inews=''
installarchlib=''
installbin=''
installman1dir=''
installman3dir=''
installprivlib=''
installscript=''
installsitelib=''
known_extensions=''
ksh=''
large=''
ld='link32'
lddlflags=''
ldflags=''
less=''
libc=''
libs=''
libswanted=''
line=''
lint=''
lkflags=''
ln=''
lns=''
lp=''
lpr=''
ls='dir'
lseektype='off_t'
mail=''
mailx=''
make='nmake'
mallocobj=''
mallocsrc=''
malloctype='void *'
man1dir=''
man1direxp=''
man1ext=''
man3dir=''
man3direxp=''
man3ext=''
medium=''
mips=''
mips_type=''
mkdir='mkdir'
models='none'
modetype='mode_t'
more=''
mv='rename'
myarchname='i386-win32'
mydomain=''
myhostname=''
myuname=''
n=''
nm_opt=''
nroff=''
optimize=''
orderlib='false'
package='perl5'
passcat=''
patchlevel='7'
perl='perl'
pg=''
phostname='hostname'
plibpth=''
pmake=''
pr=''
prefix=''
prefixexp=''
privlib=''
privlibexp=''
prototype='define'
randbits='15'
ranlib=':'
rm='del'
rmail=''
runnm='true'
scriptdir=''
scriptdirexp=''
sed=''
selecttype='fd_set *'
sendmail=''
sh=''
shar=''
shmattype='char *'
shrpdir='none'
signal_t='void'
sitelib=''
sitelibexp=''
sizetype='size_t'
sleep=''
smail=''
small=''
sockethdr=''
socketlib=''
sort='sort'
spackage='Perl5'
spitshell='type'
split=''
ssizetype='ssize_t'
stdchar='char'
stdio_base='((fp)->_IO_read_base)'
stdio_bufsiz='((fp)->_IO_read_end - (fp)->_IO_read_base)'
stdio_cnt='((fp)->_IO_read_end - (fp)->_IO_read_ptr)'
stdio_ptr='((fp)->_IO_read_ptr)'
strings=''
submit=''
sysman=''
tail=''
tar=''
tbl=''
test='test'
timeincl=''
timetype='time_t'
touch=''
tr=''
troff=''
uidtype='uid_t'
uname=''
uniq=''
usedl='define'
usemymalloc='n'
usenm='false'
useposix='false'
usevfork='false'
usrinc=''
uuname=''
vi=''
voidflags='15'
xlibpth=''
zcat=''
!END!OF!CONFIG!


tie %Config, Config;
sub TIEHASH { bless {} }
sub FETCH { 
    # check for cached value (which maybe undef so we use exists not defined)
    return $_[0]->{$_[1]} if (exists $_[0]->{$_[1]});
 
    my($value); # search for the item in the big $config_sh string
    return undef unless (($value) = $config_sh =~ m/^$_[1]='(.*)'\s*$/m);
 
    $value = undef if $value eq 'undef'; # So we can say "if $Config{'foo'}".
    $_[0]->{$_[1]} = $value; # cache it
    return $value;
}
 
sub FIRSTKEY {
    $prevpos = 0;
    my $key;
    ($key) = $config_sh =~ m/^(.*)=/;
    $key;
}

sub NEXTKEY {
    my ($pos, $len);
    $pos = $prevpos;
    $pos = index( $config_sh, "\n", $pos) + 1;
    $prevpos = $pos;
    $len = index( $config_sh, "=", $pos) - $pos;
    $len > 0 ? substr( $config_sh, $pos, $len) : undef;
}

sub EXISTS{ 
     exists($_[0]->{$_[1]})  or  $config_sh =~ m/^$_[1]=/m; 
}

sub readonly { die "\%Config::Config is read-only\n" }

sub myconfig {
	my($output);
	
	$output = <<'END';
Summary of my $package (patchlevel $PATCHLEVEL) configuration:
  Platform:
    osname=$osname, osver=$osvers, archname=$archname
    uname='$myuname'
    hint=$hint
  Compiler:
    cc='$cc', optimize='$optimize'
    cppflags='$cppflags'
    ccflags ='$ccflags'
    ldflags ='$ldflags'
    stdchar='$stdchar', d_stdstdio=$d_stdstdio, usevfork=$usevfork
    voidflags=$voidflags, castflags=$castflags, d_casti32=$d_casti32, d_castneg=$d_castneg
    intsize=$intsize, alignbytes=$alignbytes, usemymalloc=$usemymalloc, randbits=$randbits
  Libraries:
    so=$so
    libpth=$libpth
    libs=$libs
    libc=$libc
  Dynamic Linking:
    dlsrc=$dlsrc, dlext=$dlext, d_dlsymun=$d_dlsymun
    cccdlflags='$cccdlflags', ccdlflags='$ccdlflags', lddlflags='$lddlflags'

END
	$output =~ s/\$(\w+)/$Config{$1}/ge;
	$output;
}

sub STORE { &readonly }
sub DELETE{ &readonly }
sub CLEAR { &readonly }


1;
