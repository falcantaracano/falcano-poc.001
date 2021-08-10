package org.examples.quarkus.secretkeys;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.examples.quarkus.configuration.MdPConfiguration;
import org.examples.quarkus.configuration.MdPConfigurationMapping;
import org.examples.quarkus.configuration.MdPConfiguration.SecurityConfiguration;
import org.examples.quarkus.configuration.MdPConfiguration.SecurityConfiguration.Credential;
import org.examples.quarkus.exception.DontExistTypeCredentialException;

import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import io.quarkus.arc.Unremovable;
import io.quarkus.credentials.CredentialsProvider;

@ApplicationScoped
@Unremovable 
@Named("my-bbdd-credentials-provider")
public class MyBbddCredentialsProvider implements CredentialsProvider {

	private static final Logger log = Logger.getLogger(MyBbddCredentialsProvider.class);	
	private MdPConfiguration mdPConfiguration;
	private SecurityConfiguration securityConfiguration;
	
	private final String DEFAULT_BD_KIND = "oracle"; // Default oracle
	
//	@Inject
	public MyBbddCredentialsProvider () {
//		this.mdPConfiguration = mdPConfiguration;
		this.mdPConfiguration = MdPConfigurationMapping.getMdPConfiguration();
		this.securityConfiguration = this.mdPConfiguration.security();
	}

	@Override
	public Map<String, String> getCredentials(String credentialsProviderName) {
		Optional<String> bbddTypeO = ConfigProvider.getConfig().getOptionalValue("quarkus.datasource.db-kind", String.class);
		String bbddType = bbddTypeO.orElse(DEFAULT_BD_KIND); 
		Credential credential = null;
		try {
			credential = securityConfiguration.getCredential(bbddType);
		} catch (DontExistTypeCredentialException e) {
			log.error("Not credentials in the list for bbdd type: " + bbddType + ". Error: " + e.getMessage());
			return null;
		} catch (Exception e) {
			log.error("Error: " + e.getMessage());
			return null;
		}
		String username = credential.username();
		String password = credential.password();
		if (password == null) {
			password = "";
		} 
		String anonymizedPassword = anonymizeString (password);
		log.debug("my-bbdd-credentials-provider/username:" + username);
		log.debug ("my-bbdd-credentials-provider/password:" + anonymizedPassword);
		Map<String, String> properties = new HashMap<>();
		properties.put(USER_PROPERTY_NAME, username);
		properties.put(PASSWORD_PROPERTY_NAME, password);
		return properties;
	}
	
	private String anonymizeString (String value) {
		if (value == null)
			return null;
		if (value.length() > 0) {
			StringBuilder aux = new StringBuilder ();
			for (int i = 0; i < value.length(); i++)
				aux.append ('*');
			return aux.toString();
		} else
			return "";
	}

}
