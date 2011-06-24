toSync
=======
A simple Java program to synchronize folders 

[![](http://img148.imageshack.us/img148/1802/schermata062455737alle1.png)]()
[![](http://img846.imageshack.us/img846/1802/schermata062455737alle1.png)]()
[![](http://img90.imageshack.us/img90/1802/schermata062455737alle1.png)]()
[![](http://img17.imageshack.us/img17/1802/schermata062455737alle1.png)]()

Compile and create jar-file
=========

$ make all

Execute toSync
=========
$ make run
OR
$ java -jar toSync.jar

How to Use toSync?
=========
	Select the directory "Source", then the directory "Destination". Choose the kind of synchronization and the click the button "Start toSync!".

There are three kind of  synchronization:
	• Reciprocal: if a file is in source (or in destination) but not in destination(or in source), will be copied. The most recent file will overwrite the oldest.
	• Source completes Destination: If a file is in source but not in destination, will be copied in destination.
	• Source replaces Destination: if a file is in source but not in destination, will be copied in destination. If a file is in destination but not in source, will be deleted in destination.


About License
=========
	toSync is under GNU GENERAL PUBLIC LICENSE Version 3.

Support
=========
	For the latest news about toSync, visit the website of Marco Sero at http://www.marcosero.com/

Version History
=========
	v1.0
	• First release.

Enjoy.