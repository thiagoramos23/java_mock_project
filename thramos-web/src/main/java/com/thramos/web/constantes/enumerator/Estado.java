package com.thramos.web.constantes.enumerator;

public enum Estado {

	AC("AC"), AL("AL"), AP("AP"), AM("AM"), BA("BA"), CE("CE"), 
	DF("DF"), ES("ES"), GO("GO"), MA("MA"), MT("MT"), MS("MS"), MG("MG"), 
	PR("PR"), PB("PB"), PA("PA"), PE("PE"), PI("PI"),
	RJ("RJ"), RN("RN"), RS("RS"), RO("RO"), RR("RR"), SC("SC"), SE("SE"), SP("SP"), TO("TO");
	
	private String estado;

	private Estado(String estado) {
		this.estado = estado;
	}
	
	public String getEstado() {
		return estado;
	}
}
