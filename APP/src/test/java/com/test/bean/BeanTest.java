package com.test.bean;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest extends CamelSpringTestSupport{
	private static final Logger LOGGER = LoggerFactory.getLogger("RECEPTION");

	@Before
	public void before() {
		deleteDirectory("C:/WEB/RECORDS/inputs");
	}

	@Test
	public void testCamelRoute() throws Exception {
		template.sendBodyAndHeader("file:C:/WEB/RECORDS/inputs2", "<TEST>prueba</TEST>", Exchange.FILE_NAME, "testFile.txt");
		
		GenericFile receiveBody = (GenericFile) consumer.receiveBody("file:C:/WEB/RECORDS/outputs2");
		
		String content = receiveBody.getFileNameOnly();
		String contentBody = receiveBody.getBody().toString();
		
		LOGGER.info("BODY TEST-BEAN  => " + contentBody);
		
		assertEquals("testFile.txt", content);
		assertEquals("testFile.txt", contentBody);	

	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext
				("META-INF/spring/camel-context.xml");
	}
	
	@After
	public void after() {
		deleteDirectory("C:/WEB/RECORDS/outputs");
	}
	

}
