package com.thramos.framework.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.thramos.framework.security.authentication.ProjetoMockGrantedAuthority;
import com.thramos.framework.security.authentication.ProjetoMockStant;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.model.autenticacao.UsuarioPermissao;
import com.thramos.repository.usuario.UsuarioRepository;

public class THRamosAuthenticationService implements UserDetailsService{

	private static final String PREFIXO_PERMISSAO = "ROLE_";

	private static final String MENSAGEM_USUARIO_NAO_ENCONTRADO_OU_USUARIO_NAO_POSSUI_NENHUMA_PERMISSAO_ASSOCIADA = "Usuário não encontrado ou usuário não possui nenhuma permissão associada";


	@Autowired UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.consultarUsuarioPorLogin(username);
		lancaExcecaoUsuarioNaoEncontradoSeUsuarioForNulo(usuario);
		Collection<GrantedAuthority> authorities = criarAuthorities(usuario);
		return new ProjetoMockStant(usuario, usuario.getLogin(), usuario.getSenha(), authorities);
	}


	private Collection<GrantedAuthority> criarAuthorities(Usuario usuario) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (UsuarioPermissao permissao : usuario.getPermissoes()) {
			authorities.add(new ProjetoMockGrantedAuthority(PREFIXO_PERMISSAO + permissao.getPermissao().getDescricao().toUpperCase()));
		}
		
		return authorities;
	}

	private void lancaExcecaoUsuarioNaoEncontradoSeUsuarioForNulo(Usuario usuarioLogado) {
		if(VerificadorUtil.estaNulo(usuarioLogado)) {
			throw new UsernameNotFoundException(MENSAGEM_USUARIO_NAO_ENCONTRADO_OU_USUARIO_NAO_POSSUI_NENHUMA_PERMISSAO_ASSOCIADA);
		}
	}
}
