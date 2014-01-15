package com.thramos.repository.usuario;

import com.thramos.model.autenticacao.Usuario;
import com.thramos.repository.Repository;

public interface UsuarioRepository extends Repository<Usuario>{

	Usuario consultarUsuarioPorLogin(String username);
	
}
