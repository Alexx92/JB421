package com.test.bean;

import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.route.TransformingData_Route;

public class BeanTest3 extends CamelTestSupport{

	private static final Logger LOGGER = LoggerFactory.getLogger("RECEPTION");

	
	@Produce(uri = "file:C:/WEB/RECORDS/inputs2")
	private ProducerTemplate producer;
	
	@EndpointInject(uri = "mock:direct:testInputs2")
	private MockEndpoint destination;
	
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new TransformingData_Route();
	}
	
	@Before
	public void before() {
		deleteDirectory("C:/WEB/RECORDS/inputs2");
	}

	
	@Test
	public void testRouteNonAdmin() throws Exception{
		
		AdviceWithRouteBuilder mockRoute = new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("direct:testInputs2")
				.skipSendToOriginalEndpoint()
				.to("mock:direct:testInputs2");
			}
		};
		
		context.getRouteDefinition("test").adviceWith(context, mockRoute);
		context.start();
		
		NotifyBuilder builder = new NotifyBuilder(context).whenDone(1).create();
		builder.matches(2, TimeUnit.SECONDS);
		producer.sendBodyAndHeader("<TEXT_XML>test of text 3 xml</TEXT_XML>", Exchange.FILE_NAME, "output2.xml");
		
		 
		destination.expectedMessageCount(1);		 
		assertMockEndpointsSatisfied();
		context.stop();
	
	}
	
//	@After
//	public void after() {
//		deleteDirectory("C:/WEB/RECORDS/inputs2");
//	}
}
