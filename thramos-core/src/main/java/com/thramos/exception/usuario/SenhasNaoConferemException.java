package com.thramos.exception.usuario;

public class SenhasNaoConferemException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String SENHAS_NAO_CONFEREM = "As senhas não conferem. Usuário não autenticado!";

	public SenhasNaoConferemException() {
		super(SENHAS_NAO_CONFEREM);
	}
}
