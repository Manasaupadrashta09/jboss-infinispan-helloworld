package org.jboss.as.quickstarts.greeter.web;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.Cache;

import java.util.Set;

@Named
@RequestScoped
public class GetController {
	@Inject
	private Logger log;

	@Inject
	DefaultCacheManager m;

	private String key;
	private String message;
	private StringBuffer allKeyValues = new StringBuffer();

	// Called by the get.xhtml - get button
	public void getSomething() {
		Cache<String, String> c = m.getCache();
		message = c.get(key);
		log.info("get: " + key + " " + message);
	}
	
	// Called by the get.xhtml - get all button
	public void getAll() {
		Cache<String, String> c = m.getCache();
		
		Set<String> keySet = c.keySet();
		for (String key : keySet) {
		    
		    String value = c.get(key);
		    log.info("k: " + key + " v: " + value);
		    
		    allKeyValues.append(key + "=" + value + " " );
		} // for
		if (allKeyValues == null || allKeyValues.length() == 0) {
			message = "Nothing in the Cache";
		} else {
			message = allKeyValues.toString();
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

}
