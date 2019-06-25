package com.test.bean;

import org.apache.camel.Exchange;
import org.apache.camel.builder.xml.XPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanPrintData2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("RECEPTION");

	public void printDataNAME(Exchange exchange) throws Exception{
		String bodyXML  = exchange.getIn().getBody(String.class);
		String name = XPathBuilder.xpath("/order/name").evaluate(exchange.getContext(), bodyXML);
		LOGGER.info("---> BEAN DECLARE IN CAMEL-CONTEXT <--- | NAME Order => " +  name);
	}
	
	public void printDataDireccion(Exchange exchange) throws Exception {
		String bodyXML  = exchange.getIn().getBody(String.class);
		String name = XPathBuilder.xpath("/order/direccion").evaluate(exchange.getContext(), bodyXML);
		LOGGER.info("---> BEAN DECLARE IN CAMEL-CONTEXT <--- | DIRECCION Order => " +  name);
	}

}
