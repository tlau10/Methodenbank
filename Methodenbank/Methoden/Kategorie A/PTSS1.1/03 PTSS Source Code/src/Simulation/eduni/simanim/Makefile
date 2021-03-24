# To make the eduni.simanim package

include ../../makefile.config
 
all:
	$(JAVAC) $(LIBFLAGS) *.java

%.class:
	$(JAVAC) $(LIBFLAGS) $*.java
doc:
	javadoc -d ../../doc/ref/ -verbose eduni.simanim *.java
clean:
	rm $(LIBPATH)/eduni/simanim/*.class
