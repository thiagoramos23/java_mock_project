package com.thramos.framework.consulta;


public class Intervalo {
	
	private Object valorInicial;
	private Object valorFinal;
	
	public Intervalo(Object valorInicial, Object valorFinal) {
		setValorInicial(valorInicial);
		setValorFinal(valorFinal);
	}

	public Object getValorInicial() {
		return valorInicial;
	}

	public Object getValorFinal() {
		return valorFinal;
	}
	
	private void setValorInicial(Object valorInicial) {
		this.valorInicial = valorInicial;
	}


	private void setValorFinal(Object valorFinal) {
		this.valorFinal = valorFinal;
	}
	
	
}
