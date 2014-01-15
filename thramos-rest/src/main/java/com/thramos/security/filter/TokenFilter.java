package com.thramos.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.thramos.framework.consulta.restricao.Restricoes;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.service.usuario.impl.UsuarioServiceImpl;

public class TokenFilter extends GenericFilterBean {
	
	public static final String VAZIO = "";
	public static final String TOKEN_HEADER = "token_acesso";
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		if(naoForUrlDeRegistroNemDeLoginNemDeSalvarUsuario(httpRequest)) {
			String token = httpRequest.getHeader(TOKEN_HEADER);
			 
			Usuario userByToken = consultarUsuarioSeUrlNaoForNemDeLoginNemDeRegistro(httpRequest, token);
			 
			//Usuário não autenticado 
			if(userByToken == null && naoForUrlDeRegistroNemDeLoginNemDeSalvarUsuario(httpRequest)){
				httpResponse.setStatus(503);
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Só usuários autenticados devem ter acesso.");
			}
		}

		chain.doFilter(request, response);
	}

	private Usuario consultarUsuarioSeUrlNaoForNemDeLoginNemDeRegistro(HttpServletRequest httpServletRequest, String token) {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		UsuarioServiceImpl usuarioService = context.getBean(UsuarioServiceImpl.class);
		
		List<Restricoes> listaRestricoes = new ArrayList<Restricoes>();
		listaRestricoes.add(Restricoes.igual("tokenAcesso", token));
		return usuarioService.consultarEntidade(new Usuario(), listaRestricoes);
	}

	private boolean naoForUrlDeRegistroNemDeLoginNemDeSalvarUsuario(HttpServletRequest httpRequest) {
		return urlDiferenteDe("/user/register", httpRequest) && urlDiferenteDe("/auth/login", httpRequest) && urlDiferenteDe("/user/salvar", httpRequest);
	}
	
	private boolean urlDiferenteDe(String url, HttpServletRequest httpRequest) {
		return httpRequest.getPathInfo().indexOf(url) == -1;
	}

}
