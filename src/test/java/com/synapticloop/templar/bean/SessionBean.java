package com.synapticloop.templar.bean;

import java.util.HashMap;
import java.util.Map;

public class SessionBean {
	private Map<String, Object> sessionObjects = new HashMap<String, Object>();
	
	public void addToSession(String key, Object value) {
		sessionObjects.put(key, value);
	}

	public Map<String, Object> getSessionObjects() {
		return(sessionObjects);
	}
}
