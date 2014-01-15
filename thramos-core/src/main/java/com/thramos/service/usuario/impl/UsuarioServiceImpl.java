package com.thramos.service.usuario.impl;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

import com.thramos.framework.service.impl.AbstractService;
import com.thramos.framework.util.VerificadorUtil;
import com.thramos.framework.validador.Validador;
import com.thramos.model.autenticacao.Usuario;
import com.thramos.repository.Repository;
import com.thramos.repository.usuario.UsuarioRepository;
import com.thramos.service.usuario.UsuarioService;

@Component
public class UsuarioServiceImpl extends AbstractService<Usuario> implements UsuarioService{

	private UsuarioRepository usuarioRepository;
	private Md5PasswordEncoder passwordEncoder;

	@Autowired
	public UsuarioServiceImpl(Validador validador, UsuarioRepository usuarioRepository, Md5PasswordEncoder passwordEncoder) {
		super(validador);
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected Repository<Usuario> getRepository() {
		return this.usuarioRepository;
	}

	@Override
	public Usuario login(String username, String senha) {
		String senhaEncriptada = this.passwordEncoder.encodePassword(senha, null);
		Usuario usuario = this.usuarioRepository.consultarUsuarioPorLogin(username);
		if(usuarioForNuloOuSenhaNaoForIgual(senhaEncriptada, usuario)) {
			throw new RuntimeException("Login e/ou senha não coincidem. Por favor, tente novamente!");
		}
		
		return usuario;
	}

	private boolean usuarioForNuloOuSenhaNaoForIgual(String senhaEncriptada, Usuario usuario) {
		return VerificadorUtil.estaNulo(usuario) || !usuario.getSenha().equals(senhaEncriptada);
	}

	public static void main(String[] args) {
		Md5PasswordEncoder enconer = new Md5PasswordEncoder();
		System.out.println(enconer.encodePassword("test", null));
	}
}
