package com.test.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
//import org.springframework.beans.factory.annotation.Autowired;

import com.test.bean.BeanPrintData;
import com.test.bean.BeanPrintData3;

public class BeansRegistries_Route extends RouteBuilder{
	/*
	 * Necesario para utilizacion definicion 
	 * WHEN => /order[(contains(id,'1-C'))] 
	 * */
//	@Autowired
//	private BeanPrintData3 printData1;
	
	@Override
	public void configure() throws Exception {
		/*
		 * CHOICE
		 * 		->WHEN => "/order/id/text() = '1-A'"
		 * 			//call bean for JAVA IMPORT
		 * 		->WHEN => "/order[(contains(id,'1-B'))]"
		 * 			//call bean CALL BEAN METHODS DECLARING BEAN IN CAMEL-CONTEXT
		 * 		->WHEN => "/order/id/text() = '1-C'"
		 * 			//call bean 
		 * */
		from("file:C:/WEB/RECORDS/Input_BeansRoute").id("Route_JAVA-BEANS")			
			.log(LoggingLevel.INFO, "com.pruebas", "--> INIT BEANS | BODY ==> ${body}")			
			.choice()
				.when().xpath("/order/id/text() = '1-A'")				
					.log("*** INIT WHEN 1-A | DECLARING JAVA BEANS***")
					.bean(BeanPrintData.class)
					.log("*** END WHEN 1-A ***")
				.when().xpath("/order[(contains(id,'1-B'))]")
					.log("*** WHEN 1-B | CALL BEAN METHOD DECLARE IN CAMEL-CONTEXT ***")
					.to("bean://printData2?method=printDataNAME")
					.to("bean://printData2?method=printDataDireccion")
				.when().xpath("/order/id/text() = '1-C'")
					.log("*** WHEN 1-C | CALL BEAN METHOD DECLARE WITH AUTOWIRED ***")
					.bean(BeanPrintData3.class,"printDataNAME")
					.bean(BeanPrintData3.class,"printDataDireccion")
				.otherwise()
					.log("*** OTHERWISE ***")
			.log("******  END BEANS  ******")	
	;		
	}

}
