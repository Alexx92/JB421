package com.test.bean;

import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;
import com.test.route.TransformingData_Route;

public class BeanTest2 extends CamelTestSupport{

	@Produce(uri = "file:C:/WEB/RECORDS/inputs2")
	private ProducerTemplate producer;
	
	@EndpointInject(uri = "mock:inventoryTest")
	private MockEndpoint destination;
	
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new TransformingData_Route();
	}
	
	@Override
	public boolean isUseAdviceWith() {
		return true;
	}
	
	@Before
	public void before() throws Exception{
		deleteDirectory("C:/WEB/RECORDS/inputs");		
		AdviceWithRouteBuilder mockRoute = new AdviceWithRouteBuilder() {			
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("file:C:/WEB/RECORDS/outputs")
				.skipSendToOriginalEndpoint()
				.to("mock:inventoryTest");				
			}
		};
		
		context.getRouteDefinition("test").adviceWith(context, mockRoute);
		context.start();
	}
	
	@Test
	public void testRouteNonAdmin() throws Exception{
		
		 NotifyBuilder biulder = new NotifyBuilder(context).whenDone(1).create();
		 
		 biulder.matches(10000, TimeUnit.MILLISECONDS);
		 
		 String test = "<TEXT_ORDER>text xml 2</TEXT_ORDER>";
		 
//		 Object testObject = createTestOrder(false);
//		
//		 String nonAdminXML = getExpectedXmlString(testObject);
		 
		 producer.sendBodyAndHeader(test, Exchange.FILE_NAME, "output.xml");
		 
		 destination.expectedMessageCount(1);
		 
		 assertMockEndpointsSatisfied();
		
		
		
//		context.getRouteDefinition("test")
//		.adviceWith(fileRouteBuilder, new AdviceWithRouteBuilder() {
//			
//			@Override
//			public void configure() throws Exception {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		//PAGINA 177
//		
//		template.sendBodyAndHeader("file", Exchange.FILE_NAME, "testFile.txt");
//		GenericFile receiveBody = (GenericFile) mock.receiveBody;
	}
}
