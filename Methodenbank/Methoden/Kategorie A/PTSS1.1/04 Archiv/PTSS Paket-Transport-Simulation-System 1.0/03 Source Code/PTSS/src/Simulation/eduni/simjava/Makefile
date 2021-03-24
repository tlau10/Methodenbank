# To make the eduni.simjava package

include ../../makefile.config	

SRC = Sim_entity.java Sim_system.java Sim_event.java Semaphore.java \
      Sim_port.java Evqueue.java Sim_predicate.java Sim_from_p.java \
      Sim_none_p.java Sim_type_p.java Sim_any_p.java Sim_normal_obj.java \
      Sim_negexp_obj.java Sim_uniform_obj.java Sim_accum.java \
      Sim_outfile.java Sim_output.java

all:
	$(JAVAC) $(LIBFLAGS) $(SRC)

doc:
	javadoc -d ../../doc/ref -verbose eduni.simjava $(SRC)
clean:
	rm $(LIBPATH)/eduni/simjava/*.class

tst:
	(cd ../../; javadoc -d doc/ref -verbose eduni.simjava)
