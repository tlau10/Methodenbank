package createLP;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import webService.*;
//Klasse wurde von den Studierenden erstellt
public class CreateXML {

	boolean integrety = true;
	String solver = "Mops";
	String lpName = "Umladeproblem";
	String methodName = "Mops";
	String command = "CommandForMethod";

	String resultXML = "";

	public String createXML(double[][] kostenMatrix, double[] anbieterArray,
			double[] nachfragerArray) {

		try {

			Rows rows = new Rows();

			for (int i = 0; i < (kostenMatrix.length * 2) - 1; i++) {

				if (i > 0) {
					Row row = new Row();
					row.setRowName("R" + i);
					row.setRowType(ComparatorCheck.E);

					rows.getRow().add(row);
				} else {
					Row row = new Row();
					row.setRowName("ZF");
					row.setRowType(ComparatorCheck.N);

					rows.getRow().add(row);
				}

			}

			Columns columns = new Columns();
			Properties properties = new Properties();
			RightHandSides bvektorAnbieter = new RightHandSides();
			RightHandSides bvektorNachfrager = new RightHandSides();

			for (int row = 0; row < kostenMatrix.length; row++) {

				boolean setValue = false;
			

				for (int column = 0; column < kostenMatrix.length; column++) {

					if (kostenMatrix[row][column] > 0) {

						if (row != column) {

							String variablenName = "x" + (row + 1)
									+ (column + 1);

							Column zf = new Column();
							Column anbieterRestriktion = new Column();
							Column nachfragerRestriktion = new Column();

							zf.setVariable(variablenName);
							zf.setRowName("ZF");
							zf.setValue(kostenMatrix[row][column]);

							anbieterRestriktion.setVariable(variablenName);
							anbieterRestriktion.setRowName("R" + (row + 1));
							anbieterRestriktion.setValue(1.0);

							nachfragerRestriktion.setVariable(variablenName);
							nachfragerRestriktion.setRowName("R"
									+ ((kostenMatrix.length - 1) + column));
							nachfragerRestriktion.setValue(1.0);

							columns.getColumn().add(zf);
							columns.getColumn().add(anbieterRestriktion);
							columns.getColumn().add(nachfragerRestriktion);

							Property property = new Property();

							property.setIntegrity(integrety);
							property.setVariable(variablenName);

							properties.getProperty().add(property);
							
							setValue = true;
					

						} else {

							String variablenName = "t" + (row + 1);

							Column zf = new Column();
							Column anbieterRestriktion = new Column();
							Column nachfragerRestriktion = new Column();

							zf.setVariable(variablenName);
							zf.setRowName("ZF");
							zf.setValue(kostenMatrix[row][column]);

							anbieterRestriktion.setVariable(variablenName);
							anbieterRestriktion.setRowName("R" + (row + 1));
							anbieterRestriktion.setValue(-1.0);

							nachfragerRestriktion.setVariable(variablenName);
							nachfragerRestriktion.setRowName("R"
									+ ((kostenMatrix.length - 1) + column));
							nachfragerRestriktion.setValue(-1.0);

							columns.getColumn().add(zf);
							columns.getColumn().add(anbieterRestriktion);
							columns.getColumn().add(nachfragerRestriktion);

							Property property = new Property();

							property.setIntegrity(integrety);
							property.setVariable(variablenName);

							properties.getProperty().add(property);

							setValue = true;
						
						}

					}
				}// end column

				// b-Vektor
				if (setValue == true) {
					Rhs rhs = new Rhs();

					rhs.setRowName("R" + (row + 1));
					rhs.setValue(anbieterArray[row]);

					bvektorAnbieter.getRhs().add(rhs);
					
					Rhs rhs2 = new Rhs();

					rhs2.setRowName("R" + ((kostenMatrix.length) + row));
					rhs2.setValue(nachfragerArray[row + 1]);
					
					bvektorNachfrager.getRhs().add(rhs2);

				}

			}//end row
			
			Lpba lpba = new Lpba();

			lpba.setLpbaName(lpName);
			lpba.setOptimization(OptimizerCheck.MIN);
			lpba.setRows(rows);
			lpba.setColumns(columns);
				
			lpba.setRightHandSides(RightHandSides.mergeRHS(bvektorAnbieter.getRhsList(), bvektorNachfrager.getRhsList()));

			lpba.setProperties(properties);
			
			LpResults results = new LpResults();

			LpResult result = new LpResult();
			result.setSolver(solver);

			results.getLpResult().add(result);

			MethodApi api = new MethodApi();

			api.setMethodName(methodName);
			api.setCommand(command);
			api.setSessionId("");

			DataTransport transport = new DataTransport();

			transport.setMetaData("");
			transport.setLpba(lpba);
			transport.setLpResults(results);
			transport.setMethodApi(api);

			// JAXB
			JAXBContext context = JAXBContext.newInstance("webService");
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(transport, stringWriter);
			
			String xml = stringWriter.toString();
			
			System.out.println(xml);
			
			return xml;
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
}
