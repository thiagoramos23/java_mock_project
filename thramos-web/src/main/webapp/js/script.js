function validarData(campo) {
    var expReg = /^(([0-2]\d|[3][0-1])\/([0]\d|[1][0-2])\/[1-2][0-9]\d{2})$/;
    
    if ((campo.value!='') && !(campo.value.match(expReg))) {
    	alert('Formato inválido de data!');
    	campo.focus();
    }
}

function returnNumber(e){  
	var tecla=(window.event)?event.keyCode:e.which;  
	if((tecla > 47 && tecla < 58)) return true;  
	else{  
		if ((tecla != 0)&&(tecla !=8)) return false;  
		else return true;   
	}    
}