package org.examples.quarkus.configuration;

import org.jboss.logging.Logger;

//import org.eclipse.microprofile.config.Config;
//import org.eclipse.microprofile.config.ConfigProvider;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigProviderResolver;

public class MdPConfigurationMapping {
	private static Logger log = Logger.getLogger (MdPConfigurationMapping.class);
    public static MdPConfiguration getMdPConfiguration() {
    	try {
//    		SmallRyeConfig smallRyeConfig = (SmallRyeConfig)ConfigProvider.getConfig();
        	SmallRyeConfig smallRyeConfig = (SmallRyeConfig) SmallRyeConfigProviderResolver.instance().getConfig();
    		return smallRyeConfig.getConfigMapping(MdPConfiguration.class);
    	} catch (Exception e) {
    		log.error("Getting MdP Configuration Mapping, message:" + e.getMessage() +"\n", e);
    		throw e;
    	}
    }

}
