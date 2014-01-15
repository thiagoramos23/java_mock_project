package com.thramos.framework.exception;

import java.util.ArrayList;
import java.util.List;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String MENSAGEM_PADRAO = "Erro não especificado";
	private List<String> erroList = new ArrayList<String>();
	public static final String SEPARADOR = "; ";
	public String codigoErro;

	public NegocioException() {
		super();
	}

	public NegocioException(String mensagem, Exception exception) {
		super(mensagem, exception);
		adicionarMensagemErro(mensagem);
	}

	public NegocioException(String mensagemErro) {
		super(mensagemErro);
		adicionarMensagemErro(mensagemErro);
	}
	
	public NegocioException(String codigoErro, String mensagemErro) {
		super(mensagemErro);
		adicionarMensagemErro(mensagemErro);
		setCodigoErro(codigoErro);
	}
	
	public NegocioException(String mensagemErro, Throwable cause) {
		super(mensagemErro, cause);
		adicionarMensagemErro(mensagemErro);
	}

	public NegocioException(Throwable throwable) {
		super(throwable);
	}

	public String getMensagemErro(int indice) {
		try {
			if (erroList.isEmpty()) {
				return MENSAGEM_PADRAO;
			}
			return erroList.get(indice).toString();
		} catch (IndexOutOfBoundsException e) {
			return "";
		}
	}

	public void adicionarMensagemErro(String mensagem) {
		erroList.add(mensagem);
	}

	public boolean hasMensagemErro() {
		return !erroList.isEmpty();
	}

	@Override
	public String getMessage() {
		return getMensagem();
	}

	public int getQuantidadeMensagemErro() {
		return erroList.size();
	}

	private String getMensagem() {
		StringBuffer mensagens = new StringBuffer("");
		if (erroList.isEmpty()) {
			return MENSAGEM_PADRAO;
		} else {
			final int INDICE_PRIMEIRO_ELEMENTO = 0;
			for (int i = INDICE_PRIMEIRO_ELEMENTO; i <= erroList.size() - 1; i++) {
				mensagens.append(getMensagemErro(i));
				mensagens.append(SEPARADOR);
			}
			return mensagens.substring(INDICE_PRIMEIRO_ELEMENTO, mensagens.length() - SEPARADOR.length());
		}
	}

	public String getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}
	
}