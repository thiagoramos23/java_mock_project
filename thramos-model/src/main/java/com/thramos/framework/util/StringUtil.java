package com.thramos.framework.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	public static String removerMascaraDeCampo(String campo, String charParaRemover){
		for (int i = 0; i < charParaRemover.length(); i++) {
			Character character = charParaRemover.charAt(i);
			campo = StringUtils.remove(campo, character);
		}
		
		return campo;
	}
}
