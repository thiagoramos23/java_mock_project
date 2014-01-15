package com.thramos.framework.validador.impl;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thramos.framework.exception.NegocioException;
import com.thramos.framework.validador.Validador;

@Component("validador")
public class ValidadorImpl implements Validador {

	private Validator validator;
	private Set<ConstraintViolation<Object>> violacoes;
	private NegocioException negocioException;
	
	@Autowired
	public ValidadorImpl(Validator validator) {
		this.validator = validator;
	}

	public boolean isValido(Object entidade,Class<?>... contextos){
		validate(entidade, contextos);
		return isValido();
	}
	
	public void validar(Object entidade,Class<?>... contextos){
		validate(entidade, contextos);
		if (isInvalido()) {
			lancarExcecaoComViolacoes();
		}
	}
	
	private void validate(Object entidade,Class<?>... contextos){
		violacoes = validator.validate(entidade, contextos);
	}
	
	private boolean isInvalido(){
		return !isValido();
	}
	
	private boolean isValido(){
		return violacoes.isEmpty();
	}
	
	private void lancarExcecaoComViolacoes(){
		negocioException = criarNegocioException();
		adicionarViolacoes();
		throw negocioException;
	}
	
	private NegocioException criarNegocioException(){
		return new NegocioException();
	}
	
	private void adicionarViolacoes(){
		for (ConstraintViolation<Object> violacao : violacoes) {
			negocioException.adicionarMensagemErro(violacao.getMessage());
		}
	}
	
}