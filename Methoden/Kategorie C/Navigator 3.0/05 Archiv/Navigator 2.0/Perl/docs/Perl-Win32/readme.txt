
You are reading: README.TXT
---------------------------

This file gives an overview of Perl for Win32

It includes the following:
  Conditions for use of Perl for Win32
  Acknowledgements
  Platforms Supported
  System Requirements
  Differences between/additions to the UNIX version
  Listing of other documents in this Kit
  Submit comments and feedback on Perl for Win32
  Where to get more information
 
For information on installing Perl for Win32, please refer to the file "INSTALL.TXT".

To see what has been included in this release of Perl for Win32, please
see the file "RELEASE.TXT".

Windows 95 users, be sure to check "WIN95.TXT".

The entire Perl 5 manual can be viewed with your WWW browser of choice (i.e.
Netscape) by opening the file "PerlW32.html" in the 'docs\Perl-Win32' directory.

"Windows NT" is a registered trademark of Microsoft Corporation.

-----------------------------------------------------------------------------

Conditions for use of Perl for Win32, PerlScript and Perl for ISAPI Packages
============================================================================


	'Perl for Win32', 'PerlScript', 'Perl for ISAPI', and 'ActiveWare'
	are trademarks of ActiveWare Internet Corp. Any use of these trademarks
	requires written permission from ActiveWare Internet Corp.
	
Portions (C) 1995, 1996, 1997 Microsoft Corporation. All rights reserved. 
        Developed by ActiveWare Internet Corp., http://www.ActiveWare.com

    This program is free software; you can redistribute it and/or modify
    it under the terms of either:
    
	a) the GNU General Public License as published by the Free
	Software Foundation; either version 1, or (at your option) any
	later version, or

	b) the "Artistic License" included in this kit.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See either
    the GNU General Public License or the Artistic License for more details.

    You should have received a copy of the Artistic License with this
    Kit, in the file named "LICENSE.txt".  If not, we'll be glad to provide
    one.

    You should also have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.

    For those of you that choose to use the GNU General Public License,
    our interpretation of the GNU General Public License is that no Perl
    script falls under the terms of the GPL unless you explicitly put
    said script under the terms of the GPL yourself.  Furthermore, any
    object code linked with perl does not automatically fall under the
    terms of the GPL, provided such object code only adds definitions
    of subroutines and variables, and does not otherwise impair the
    resulting interpreter from executing any standard Perl script.  We
    consider linking in C subroutines in this manner to be the moral
    equivalent of defining subroutines in the Perl language itself.  You
    may sell such an object file as proprietary provided that you provide
    or offer to provide the Perl source, as specified by the GNU General
    Public License.  (This is merely an alternate way of specifying input
    to the program.)  You may also sell a binary produced by the dumping of
    a running Perl script that belongs to you, provided that you provide or
    offer to provide the Perl source as specified by the GPL.  (The
    fact that a Perl interpreter and your code are in the same binary file
    is, in this case, a form of mere aggregation.)  This is our interpretation
    of the GPL.  If you still have concerns or difficulties understanding
    our intent, feel free to contact us.  Of course, the Artistic License
    spells all this out for your protection, so you may prefer to use that.


-----------------------------------------------------------------------------

Acknowledgments
===============

Thanks to Larry Wall for making this possible, as well as all the perl5porters
(especially Tim Bunce).

Clark Williams from Intergraph and Dean Troyer from Honeywell for their work 
on porting Perl 4 to Windows NT, and their help in this port.

Perl for Win32 Team: Dick Hardt, Douglas Lankshear.

-----------------------------------------------------------------------------

Platforms supported
===================

Perl for Win32 is currently available as a compiled binary for the following platforms:

  Intel 80x86
  ALPHA

The source code should compile on any platform supported by Windows NT. 

-----------------------------------------------------------------------------

System Requirements
===================

To run Perl for Win32, you will need a computer running Windows NT
Workstation/Server 3.5 or later, with at least 2 MB of disk space free.

If you plan on building from the source, the amount of disk space required 
increases to at least 10 MB.

Perl for Win32 was built using Microsoft Visual C++ version 4.2.
The source release of Perl for Win32 includes the source files and
test scripts. There is also a makefile for building Perl for Win32 from the SDK.

-----------------------------------------------------------------------------

Differences between/additions to the UNIX distribution
======================================================

This port of Perl for Win32 is based upon the UNIX distribution of Perl 5.
Any modifications made to the UNIX distribution will be submitted to the UNIX
porting team so that they may be included when their next port is released.

Perl scripts written under UNIX terminate lines with a line feed. 
Win32, however,  terminates text lines with two characters: a carriage
return AND a line feed.  Be warned: if you are moving Perl scripts over to
your Win32 machine from UNIX, you may have some reformatting to do!

Since not all of the functions from the UNIX version of Perl 5 have analogs in
the Win32 realm, we've left some unimplemented!  A list of all functions
NOT implemented in this release can be found in "STATUS.TXT".

-----------------------------------------------------------------------------

Listing of other documents:
===========================

        INSTALL.txt       Instructions for installing Perl for Win32
        LICENSE.txt       The GNU "General Public License" and "Artistic License" 
        PerlW32.html      First page of Perl for Win32 HTML documentation,
                          including the original Perl 5 Manual Pages, and our
                          extensions to Perl 5.  This file and the rest of the
                          HTML pages are located in the 'docs\Perl-Win32' 
                          subdirectory.
        RELEASE.txt	  Perl for Win32 revision history, changes since
                          last release, and plans for future releases.
        STATUS.txt	  List of unsupported functions and features.

-----------------------------------------------------------------------------

Submitting comments and feedback on Perl for Win32
==================================================

All questions, comments, and feedback can be sent to Perl-Win32-users@ActiveWare.com,
or to the discussion list (see below for information on how to subscribe).  

Bug reports can be submitted at http://www.ActiveState.com/bugs 

New features can be suggested at http://www.ActiveState.com/support/addfeature.htm

-----------------------------------------------------------------------------

Where to get more info
======================

By Mail:
~~~~~~~~
   If you are interested in the latest news on Perl for Win32, we have set
   up two lists: 
 
   perl-win32-users - discussion list

	    send mail to:       ListManager@ActiveWare.com 

		    with:       SUBSCRIBE perl-win32-users 

		    as the message body

		or  with:	DIGEST perl-win32-users 
		
		    as the message body to join in digest mode


   perl-win32-announce - announcements

	    send mail to:       ListManager@ActiveWare.com 

		    with:       SUBSCRIBE perl-win32-announce 

		    as the message body

In print:
~~~~~~~~~
There are two good books by O'Reilly & Associates, Inc. about Perl: "Learning
Perl", and "Programming Perl", both by Randal Schwartz and Larry Wall.
O'Reilly's web site is http://www.ora.com.


By Usenet Newsgroups:
~~~~~~~~~~~~~~~~~~~~~

The Usenet newsgroup comp.lang.perl is your best place to get help on Perl,
although it is soon to be depreciated in use, to be replaced by
comp.lang.perl.announce and comp.lang.perl.misc.


On the World Wide Web:
~~~~~~~~~~~~~~~~~~~~~~

Perl Meta-FAQ
    (http://www.khoros.unm.edu/staff/neilb/perl/metaFAQ/metaFAQ.html)

Perl5 Information, Announcements, and Discussion
    (http://www.metronet.com/perlinfo/perl5.html)

PERL Man pages 
    (http://www.metronet.com/0/perlinfo/perl5/manual/perl.html)

Perl reference materials
    (http://www.eecs.nwu.edu/perl/perl.html)

UF/NA Perl Archive
    (http://www.cis.ufl.edu:80/perl/)


Via FTP:
~~~~~~~~

ftp://ftp.microsoft.com/bussys/winnt/winnt-public/reskit/nt351/
ftp://ftp.khoros.unm.edu/pub/perl/
ftp://perl.com/pub/perl/
ftp://ftp.cis.ufl.edu/pub/perl/
ftp://ftp.cs.ruu.nl/pub/PERL/
ftp://ftp.funet.fi/pub/languages/perl/ports/
ftp://ftp.khoros.unm.edu/pub/perl/
ftp://src.doc.ic.ac.uk/packages/perl5/


-----------------------------------------------------------------------------

