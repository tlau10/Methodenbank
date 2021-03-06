;# pwd.pl - keeps track of current working directory in PWD environment var
;#
;# $RCSfile: pwd.pl,v $$Revision: 4.1 $$Date: 92/08/07 18:24:11 $
;#
;# $Log:	pwd.pl,v $
;#
;# Usage:
;#	require "pwd.pl";
;#	&initpwd;
;#	...
;#	&chdir($newdir);

use Cwd;

package pwd;

sub main'initpwd {
    if ($ENV{'PWD'}) {
	local($dd,$di) = stat('.');
	local($pd,$pi) = stat($ENV{'PWD'});
	if ($di != $pi || $dd != $pd) {
	    chop($ENV{'PWD'} = cwd);
	}
    }
    else {
	chop($ENV{'PWD'} = cwd);
    }
    if ($ENV{'PWD'} =~ m|(/[^/]+(/[^/]+/[^/]+))(.*)|) {
	local($pd,$pi) = stat($2);
	local($dd,$di) = stat($1);
	if ($di == $pi && $dd == $pd) {
	    $ENV{'PWD'}="$2$3";
	}
    }
}

sub main'chdir {
    local($newdir) = shift;
    $newdir =~ s|/{2,}|/|g;
    if (chdir $newdir) {
	if ($newdir =~ m#^/#) {
	    $ENV{'PWD'} = $newdir;
	}
	else {
	    local(@curdir) = split(m#/#,$ENV{'PWD'});
	    @curdir = '' unless @curdir;
	    foreach $component (split(m#/#, $newdir)) {
		next if $component eq '.';
		pop(@curdir),next if $component eq '..';
		push(@curdir,$component);
	    }
	    $ENV{'PWD'} = join('/',@curdir) || '/';
	}
    }
    else {
	0;
    }
}

1;
