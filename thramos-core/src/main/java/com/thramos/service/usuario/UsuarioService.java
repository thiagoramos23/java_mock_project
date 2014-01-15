package com.thramos.service.usuario;

import com.thramos.framework.service.Service;
import com.thramos.model.autenticacao.Usuario;

public interface UsuarioService extends Service<Usuario>{

	Usuario login(String username, String senha);
}
