/*
 */
package com.rlonryan.quikmod.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author RlonRyan
 */
public final class PropWrapper {

	private final Map<String, String> props = new HashMap<>();

	public PropWrapper() {
	}
	
	public PropWrapper(Properties properties) {
		for (Map.Entry e : properties.entrySet()) {
			this.addProp(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
		}
	}

	public PropWrapper(Properties properties, String prefix) {
		for (Map.Entry e : properties.entrySet()) {
			this.addProp(prefix + String.valueOf(e.getKey()), String.valueOf(e.getValue()));
		}
	}

	public String replace(String in) {
		for (Map.Entry<String, String> e : props.entrySet()) {
			in = in.replace(e.getKey(), e.getValue());
		}
		return in;
	}
	
	public void addProp(String key, String value) {
		this.props.put("${" + key + "}", value);
	}

}
