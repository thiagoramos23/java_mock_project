package com.thramos.framework.consulta;

public enum TipoOperador {
	IGUAL("=", "Igual"), MAIORIGUAL(">=", "Maior ou Igual"), LIKE("LIKE", "Like"), MENORIGUAL("<=", "Menor ou Igual"), DIFERENTEDE("<>", "Diferente de"),
	BETWEEN("BETWEEN", "Between"), ISNULL("is null", "is null"), ISNOTNULL("is not null", "is not null");
	
	private String operador;
	private String nomeOperador;
	
	private TipoOperador(String operador, String nomeOperador){
		this.operador = operador;
		this.nomeOperador = nomeOperador;
	}

	public String getOperador() {
		return operador;
	}
	
	public String getNomeOperador() {
		return nomeOperador;
	}

}