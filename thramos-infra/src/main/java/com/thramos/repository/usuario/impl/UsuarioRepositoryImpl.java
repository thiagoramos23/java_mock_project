package com.thramos.repository.usuario.impl;

import org.springframework.stereotype.Component;

import com.thramos.framework.consulta.ConsultaJPA;
import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.repository.AbstractRepository;
import com.thramos.repository.command.CommandSingleResult;
import com.thramos.repository.command.ValidadorSingleResult;
import com.thramos.repository.usuario.UsuarioRepository;

@Component
public class UsuarioRepositoryImpl extends AbstractRepository<Usuario> implements UsuarioRepository{

	@Override
	public Usuario consultarUsuarioPorLogin(final String username) {
		return new ValidadorSingleResult<Usuario>().getSingleResult(new CommandSingleResult<Usuario>() {
			
			@Override
			public Usuario execute() {
				return new ConsultaJPA<Usuario>(getEntityManager(), new Usuario().getClass().getSimpleName())
						.addParametroQuery(Restricoes.igual("login", username))
						.singleResult();
			}
		});
	}

}
