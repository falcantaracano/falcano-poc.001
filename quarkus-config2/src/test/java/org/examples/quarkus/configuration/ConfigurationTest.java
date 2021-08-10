package org.examples.quarkus.configuration;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import javax.inject.Inject;

import org.jboss.logging.Logger;

@QuarkusTest
public class ConfigurationTest {
	private static Logger log = Logger.getLogger(ConfigurationTest.class);
	
	@Inject
	MdPConfiguration mdPConfiguration;
	
	@Test
	@DisplayName("Test1 Paths Configuration")
    public void test1PathsConfiguration() {
		MdPConfiguration mdPConfiguration = MdPConfigurationMapping.getMdPConfiguration();
		log.info ("test1: " + mdPConfiguration.toOuput());
	}

	@Test
	@DisplayName("Test2 Paths Configuration")
    public void test2PathsConfiguration() {
		log.info ("test2: " + mdPConfiguration.toOuput());
	}

}
