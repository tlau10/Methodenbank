# Usage:
#	require "finddepth.pl";
#
#	&finddepth('/foo','/bar');
#
#	sub wanted { ... }
#		where wanted does whatever you want.  $dir contains the
#		current directory name, and $_ the current filename within
#		that directory.  $name contains "$dir/$_".  You are cd'ed
#		to $dir when the function is called.  The function may
#		set $prune to prune the tree.
#
# This library is primarily for find2perl, which, when fed
#
#   find2perl / -name .nfs\* -mtime +7 -exec rm -f {} \; -o -fstype nfs -prune
#
# spits out something like this
#
#	sub wanted {
#	    /^\.nfs.*$/ &&
#	    (($dev,$ino,$mode,$nlink,$uid,$gid) = lstat($_)) &&
#	    int(-M _) > 7 &&
#	    unlink($_)
#	    ||
#	    ($nlink || (($dev,$ino,$mode,$nlink,$uid,$gid) = lstat($_))) &&
#	    $dev < 0 &&
#	    ($prune = 1);
#	}


use File::Find ();

*name = *File::Find::name;
*prune = *File::Find::prune;
*dir = *File::Find::dir;

sub finddepth {
    &File::Find::finddepth(\&wanted, @_);
}

1;
