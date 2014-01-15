package com.thramos.restservice.resources.authentication;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thramos.framework.json.JsonUtil;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.service.usuario.UsuarioService;

@Component
@Path("/auth")
@Transactional
@Produces(MediaType.APPLICATION_JSON)
public class AutenticacaoResourceImpl {

	@Autowired UsuarioService usuarioService;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String login(@FormParam("username")String username, @FormParam("senha")String senha) throws JsonProcessingException {
		Usuario usuarioLogado = this.usuarioService.login(username, senha);
		String jsonUsuario = JsonUtil.convertObjectToJsonString(usuarioLogado);
		return jsonUsuario;
	}

}
	