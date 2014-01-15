package com.thramos.framework.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class VerificadorUtil {

	public static Boolean isDiferente(String string1, String string2) {
		return !StringUtils.equalsIgnoreCase(string1, string2);
	}

	public static Boolean naoEstaNulo(Object objeto) {
		return !estaNulo(objeto);
	}

	public static Boolean estaNulo(Object objeto) {
		return objeto == null;
	}
	
	public static Boolean estaVazio(Object objeto) {
		return StringUtils.isEmpty((String) objeto);
	}
	
	public static Boolean estaNuloOuVazio(Object objeto) {
		return estaNulo(objeto) || estaVazio(objeto);
	}
	
	public static Boolean naoEstaVazio(Object objeto) {
		return !estaVazio(objeto);
	}
	
	public static Boolean naoEstaNuloNemVazio(Object objeto) {
		return naoEstaNulo(objeto) && naoEstaVazio(objeto);
	}
	
	public static Boolean colecaoNaoEstaNulaNemVazio(Collection<?> objetos) {
		return naoEstaNulo(objetos) && colecaoNaoEstaVazia(objetos); 
	}

	public static boolean colecaoEstaVazia(Collection<?> objetos) {
		return objetos.isEmpty();
	}

	public static boolean colecaoNaoEstaVazia(Collection<?> objetos) {
		return !colecaoEstaVazia(objetos);
	}
}
