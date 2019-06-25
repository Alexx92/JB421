package com.test.bean;

import org.apache.camel.Exchange;
import org.apache.camel.builder.xml.XPathBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanPrintData3 {

	private static final Logger LOGGER = LoggerFactory.getLogger("BEAN_AUTOWIRED");

	public void printDataNAME(Exchange exchange) throws Exception{
		String bodyXML  = exchange.getIn().getBody(String.class);
		String name = XPathBuilder.xpath("/order/name").evaluate(exchange.getContext(), bodyXML);
		LOGGER.info("---> BEAN DECLARE WITCH AUTOWIRED <--- | NAME Order => " +  name);
	}
	
	public void printDataDireccion(Exchange exchange) throws Exception {
		String bodyXML  = exchange.getIn().getBody(String.class);
		String name = XPathBuilder.xpath("/order/direccion").evaluate(exchange.getContext(), bodyXML);
		LOGGER.info("---> BEAN DECLARE WITCH AUTOWIRED <--- | DIRECCION Order => " +  name);
	}
	
	public void printTransformExceptionA(Exchange exchange) {
		String bodyXML  = exchange.getIn().getBody(String.class);
		String name = XPathBuilder.xpath("/order/name").evaluate(exchange.getContext(), bodyXML);
		LOGGER.info("---> TRANSFORM INCORRECT <--- | VALUE => " +  name);
		int value = Integer.parseInt(name);
	}
	
	public void printTransformExceptionB(Exchange exchange) {
		String bodyXML  = exchange.getIn().getBody(String.class);
		String tel = XPathBuilder.xpath("/order/tel").evaluate(exchange.getContext(), bodyXML);
		LOGGER.info("---> TRANSFORM INCORRECT <--- | VALUE => " +  tel);
		double value = Double.parseDouble("tel");
		
	}
}
