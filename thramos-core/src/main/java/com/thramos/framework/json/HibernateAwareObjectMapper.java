package com.thramos.framework.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

public class HibernateAwareObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 1L;
	private Hibernate4Module hibernate4Module;

	public HibernateAwareObjectMapper() {
		hibernate4Module = new Hibernate4Module();
		registerModule(hibernate4Module);
	}
	
	public void disableTransientAnnotationEffect() {
		hibernate4Module.disable(Feature.USE_TRANSIENT_ANNOTATION);
	}
}
