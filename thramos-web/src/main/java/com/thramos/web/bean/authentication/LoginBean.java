package com.thramos.web.bean.authentication;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thramos.framework.security.authentication.ProjetoMockStant;

@Component
@ManagedBean
@Scope("request")
@Transactional(propagation = Propagation.REQUIRED)
public class LoginBean {
	
	public String doLogin() throws ServletException, IOException {
		continuarRequestParaSpringSecurity();
		
		return null;
	}
		
	private void continuarRequestParaSpringSecurity() throws ServletException, IOException {	
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");

        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());

        FacesContext.getCurrentInstance().responseComplete();
	}
	
	public String getUserName() {
		try {
			UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
			ProjetoMockStant userStant = (ProjetoMockStant) authentication.getPrincipal();
			return userStant.getUsuario().getPessoa().getNome();
		} catch (ClassCastException e) {
			return "Não autenticado";
		}
	}
}
