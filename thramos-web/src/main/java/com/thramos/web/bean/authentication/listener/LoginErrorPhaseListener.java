package com.thramos.web.bean.authentication.listener;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;

public class LoginErrorPhaseListener implements PhaseListener{

	private static final long serialVersionUID = 1L;

	public void afterPhase(PhaseEvent event) {
	}

	public void beforePhase(PhaseEvent event) {
		adicionarMensagemAosFacesMessages();
	}

	private void adicionarMensagemAosFacesMessages() {
		Exception e = (Exception) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
			FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getSessionMap()
			.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Login e/ou senha inválidos",
							null));
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
