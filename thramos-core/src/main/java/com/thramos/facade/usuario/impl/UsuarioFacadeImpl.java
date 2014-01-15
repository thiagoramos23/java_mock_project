package com.thramos.facade.usuario.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thramos.facade.usuario.UsuarioFacade;
import com.thramos.framework.facade.impl.AbstractFacade;
import com.thramos.framework.service.Service;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.service.usuario.UsuarioService;

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioFacadeImpl extends AbstractFacade<Usuario> implements UsuarioFacade{

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioFacadeImpl(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Override
	protected Service<Usuario> getService() {
		return this.usuarioService;
	}

}
