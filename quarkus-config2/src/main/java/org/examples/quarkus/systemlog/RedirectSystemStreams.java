package org.examples.quarkus.systemlog;

import org.jboss.logging.Logger;

import org.examples.quarkus.configuration.MdPConfiguration;
import org.examples.quarkus.configuration.MdPConfigurationMapping;

public class RedirectSystemStreams {
	public static Logger log = Logger.getLogger(RedirectSystemStreams.class);
	
	public static void redirect () {
		MdPConfiguration mdPConfiguration = MdPConfigurationMapping.getMdPConfiguration();
		mdPConfiguration.log().category().forEach((category,values) -> {
        	if (values.out() != null) {
                System.setOut(SystemToLoggingStream.createPrintStream(Logger.getLogger(category), values.out()));  
                log.info ("System.out for category " + category +" redirect to Level." + values.out());
        	}
        	if (values.err() != null) {
                System.setErr(SystemToLoggingStream.createPrintStream(Logger.getLogger(category), values.err()));       		
                log.info ("System.err for category " + category +" redirect to Level." + values.err());
        	}
        });
	}
}
