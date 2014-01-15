package com.thramos.framework.validador;

public interface Validador {

	void validar(Object entidade, Class<?>... contextos);

	boolean isValido(Object entidade, Class<?>... grupo);
	
}