package com.thramos.web.framework.combomodel;

public class ItemCombo {

	private String labelCampo;
	private Object objetoCampo;
	
	public ItemCombo() {}

	public ItemCombo(String labelCampo, Object objetoCampo) {
		super();
		this.labelCampo = labelCampo;
		this.objetoCampo = objetoCampo;
	}

	public String getLabelCampo() {
		return labelCampo;
	}

	public void setLabelCampo(String labelCampo) {
		this.labelCampo = labelCampo;
	}

	public Object getObjetoCampo() {
		return objetoCampo;
	}

	public void setObjetoCampo(Object objetoCampo) {
		this.objetoCampo = objetoCampo;
	}
	
}
