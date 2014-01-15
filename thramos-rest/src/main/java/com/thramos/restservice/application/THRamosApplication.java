package com.thramos.restservice.application;

import org.glassfish.jersey.server.ResourceConfig;

import com.thramos.restservice.resources.authentication.AutenticacaoResourceImpl;
import com.thramos.restservice.resources.pessoa.PessoaResourceImpl;

public class THRamosApplication extends ResourceConfig{

	public THRamosApplication() {
		register(AutenticacaoResourceImpl.class);
		register(PessoaResourceImpl.class);
		//packages("com.thramos.restservice.resources");
	}
}
