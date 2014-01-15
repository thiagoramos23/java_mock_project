package com.thramos.framework.security.authentication;

import org.springframework.security.core.GrantedAuthority;

public class ProjetoMockGrantedAuthority implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	private String authority;
	
	public ProjetoMockGrantedAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return this.authority;
	}

}
