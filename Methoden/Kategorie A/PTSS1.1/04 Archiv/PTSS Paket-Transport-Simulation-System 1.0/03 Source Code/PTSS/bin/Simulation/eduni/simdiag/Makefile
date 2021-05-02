# To make the eduni.simdiag package

include ../../makefile.config
 
all:
	$(JAVAC) $(LIBFLAGS) *.java

%.class:
	$(JAVAC) $(LIBFLAGS) $*.java
doc:
	javadoc -d ../../doc/ref/ -verbose eduni.simdiag *.java
clean:
	rm $(LIBPATH)/eduni/simdiag/*.class

testgraph:
	$(JAVA) eduni.simdiag.GraphDiagram

