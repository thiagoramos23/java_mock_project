package com.thramos.web.converter.simnaoparaboolean;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class SimNaoParaBooleanConversor implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("Passou pelo get as object");
		return true;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		System.out.println("Passou pelo get as string");
		return null;
	}

}
