package com.thramos.framework.security.provider;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.thramos.framework.security.authentication.ProjetoMockStant;
import com.thramos.framework.security.exception.ProjetoMockAuthenticationException;
import com.thramos.framework.security.service.THRamosAuthenticationService;

public class THRamosAuthenticationProvider implements AuthenticationProvider{

	private static final String MENSAGEM_NAO_FOI_POSSIVEL_EFETUAR_LOGIN_DO_USUARIO_SENHA_NAO_CONFERE = "Não foi possível efetuar o login do usuário. A senha não confere";
	
	private THRamosAuthenticationService authenticationService;
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication)	throws AuthenticationException {
		UsernamePasswordAuthenticationToken thramosAuthentication = (UsernamePasswordAuthenticationToken) authentication;
		ProjetoMockStant usuarioRetornado = (ProjetoMockStant) authenticationService.loadUserByUsername(authentication.getName());
		String senha = passwordEncoder.encodePassword(thramosAuthentication.getCredentials().toString(), null);
		
		verificaSeSenhasSaoEquivalentesLancandoExcecaoSeNaoForem(usuarioRetornado.getPassword(), senha);
		return new UsernamePasswordAuthenticationToken(usuarioRetornado, usuarioRetornado.getPassword(), usuarioRetornado.getAuthorities());
		
	}

	@Override
	public boolean supports(Class<?> classe) {
		return true;
	}

	public void setAuthenticationService(THRamosAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	private void verificaSeSenhasSaoEquivalentesLancandoExcecaoSeNaoForem(String senhaDoUsuarioCadastrado, String senhaPassadaNoFormulario) {
		if(senhasNaoSaoIguais(senhaDoUsuarioCadastrado, senhaPassadaNoFormulario)) {
			throw new ProjetoMockAuthenticationException(MENSAGEM_NAO_FOI_POSSIVEL_EFETUAR_LOGIN_DO_USUARIO_SENHA_NAO_CONFERE);
		}
	}

	private boolean senhasNaoSaoIguais(String senhaDoUsuarioCadastrado,
			String senhaPassadaNoFormulario) {
		return !StringUtils.equals(senhaDoUsuarioCadastrado, senhaPassadaNoFormulario);
	}
	
}
