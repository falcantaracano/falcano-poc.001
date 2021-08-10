package org.examples.quarkus.configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.examples.quarkus.exception.DontExistTypeCredentialException;
import org.jboss.logging.Logger.Level;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "mdp")
public interface MdPConfiguration {

	@WithName("security")
	public SecurityConfiguration security();
	@WithName("log")
	public SystemLoggingConfiguration log();

    public default String toOuput () {
    	return "MdPConfiguration [ \n" +
    				"security: " + security().toOuput() + "\n" +
    				", log: " + log().toOutput() + "\n" +
    			"]";
    }


	interface SecurityConfiguration {
		public SharedKey sharedKey();
	    public List<Credential> credentials();

	    public default String toOuput () {
	    	return "SecurityConfiguration [ " +
	    				"sharedKey: " + sharedKey().toOuput() +
	    				", credentials: " + credentials().stream().map(Credential::toOutput).collect(Collectors.toList()).toString() +
	    			"]";
	    }

	    public default Credential getCredential (String type) throws DontExistTypeCredentialException, Exception {
	    	if (type == null) 
	    		throw new RuntimeException ("The credential type must be not null");
	    	Credential credential = null;
	    	for (Credential credentialAux : credentials()) {
	    		if (type.equals (credentialAux.type())) {
	    			credential = credentialAux;
	    			break;
	    		}
	    	}
	    	if (credential == null) {
	    		throw new DontExistTypeCredentialException ("Not exist credential of type: " + type);
	    	}
	    	return credential;
	    }
	        
		interface Credential {
	    	public String type ();
	    	public String username ();
	    	public String password ();

	    	public default String toOutput () {
		    	return "Credential [ " +
		    				"type: " + type() +
		    				", username: " + username() +
		    				", password: " + password() +
		    			"]";
		    }
	    }
	    
		interface SharedKey {
	    	public String filename ();
	    	public String typeFile (); // Valores posibles file or resource

	    	public default String toOuput () {
		    	return "SharedKey [ " +
		    				"filename: " + filename() +
		    				", typeFile: " + typeFile() +
		    			"]";
		    }
	    }
	}
	
	interface SystemLoggingConfiguration {
		public Map<String, CategoryInfo> category();

		public default String toOutput () {
	    	return "SystemLoggingConfiguration [ " +
	    				"category: " + category().keySet().stream().map(key -> key + ": " + category().get(key).toOutput()).collect(Collectors.joining(", ", "{", "}")) +
	    			"]";
	    }
	    
		interface CategoryInfo {
			@WithDefault("DEBUG")
			public Level err ();
			@WithDefault("INFO")
	    	public Level out ();

			public default String toOutput () {
		    	return "CategoryInfo [ " +
		    				"out: " + out().name() +
		    				", err: " + err().name() +
		    			"]";
		    }
	    }
	}
	
}
