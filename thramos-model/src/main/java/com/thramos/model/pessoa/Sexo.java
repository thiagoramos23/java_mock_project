package com.thramos.model.pessoa;

public enum Sexo {

	M("M", "Masculino"),
	F("F", "Feminino");

	private String value;
	private String descricao;

	private Sexo() {}
	
	private Sexo(String value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
