package com.thramos.exception.usuario;

public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final String NÃO_EXISTE_NENHUM_USUÁRIO_COM_ESTE_LOGIN = "Não existe nenhum usuário com este login.";
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException() {
		super(NÃO_EXISTE_NENHUM_USUÁRIO_COM_ESTE_LOGIN);
	}
}
