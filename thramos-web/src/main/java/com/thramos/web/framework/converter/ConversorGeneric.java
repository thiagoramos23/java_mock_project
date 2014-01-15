package com.thramos.web.framework.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public abstract class ConversorGeneric implements Converter{

	
	protected static final String key = "jsf.EntityConverter";
	protected static final String EMPTY = "";
	
	protected Map<String, Object> getViewMap(FacesContext context) {
	    Map<String, Object> viewMap = context.getViewRoot().getViewMap();
	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    Map<String, Object> idMap = (Map) viewMap.get(getKey());
	    if (idMap == null) {
	        idMap = new HashMap<String, Object>();
	        viewMap.put(getKey(), idMap);
	    }
	    return idMap;
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent c, String value) {
	    if (value.isEmpty()) {
	        return null;
	    }
	    return getViewMap(context).get(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent c, Object value) {
	    if (value == null) {
	    	getViewMap(context).put(EMPTY, value);
	        return EMPTY;
	    }
	    String id = getID(value);
	    getViewMap(context).put(id, value);
	    return id;
	}
	
	protected abstract String getKey();
	protected abstract String getID(Object value);

}
