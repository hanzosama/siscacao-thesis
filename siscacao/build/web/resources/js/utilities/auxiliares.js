//autoseleccionador js para los formularios con select

function seleccionaOpcion(__form,__select,__elemento){
    var __cantidad = eval('document.'+__form+'.'+__select+'.options.length');
    for (i = 0; i < __cantidad	; i++) {
        if(eval('document.'+__form+'.'+__select+'[i].value')==__elemento){
            eval('document.'+__form+'.'+__select+'[i].selected=true');			
        }
    }
}
    
function fecha_futura(fecha_val,opcion){
    var selectfec=fecha_val;    
    var	today =	new Date();
    var	dateNow	 = today.getDate();
    var	monthNow = today.getMonth()+1;
    var	yearNow	 = today.getFullYear();
    var Dia = Number(selectfec.substr(8, 2)); 
    var Mes = Number(selectfec.substr(5, 2)); 
    var Ano = Number(selectfec.substr(0, 4)); 
    var fecha1= new Date(Ano,Mes,Dia);
    var fecha2= new Date(yearNow,monthNow,dateNow);
    var msegFecha1 = fecha1.getTime();
    var msegFecha2=  fecha2.getTime();
    if(opcion==2){
        var Diferencia = msegFecha1-msegFecha2;
    }else if(opcion==3){
        var selectfec2= "2038-01-19"; 
        Dia = Number(selectfec2.substr(8, 2)); 
        Mes = Number(selectfec2.substr(5, 2)); 
        Ano = Number(selectfec2.substr(0, 4)); 
        var fechamaxphp= new Date(Ano,Mes,Dia);
        var msegFechamaxphp = fechamaxphp.getTime();
        var Diferencia = msegFechamaxphp - msegFecha1;
    }
    else{
        var Diferencia = msegFecha2 - msegFecha1;
    }
    Diferencia /= 86400000;
    if (Diferencia < 0) {
        alert("Fecha Seleccionada no valida");
        return 1;
    }else{
        return 0;
    }
        
}

(function($) {

    /**
	 * Spoofs placeholders in browsers that don't support them (eg Firefox 3)
	 * 
	 * Copyright 2011 Dan Bentley
	 * Licensed under the Apache License 2.0
	 *
	 * Author: Dan Bentley [github.com/danbentley]
	 */

    // Return if native support is available.
    if ("placeholder" in document.createElement("input")) return;

    $(document).ready(function(){
        $(':input[placeholder]').not(':password').each(function() {
            setupPlaceholder($(this));
        });

        $(':password[placeholder]').each(function() {
            setupPasswords($(this));
        });
	   
        $('form').submit(function(e) {
            clearPlaceholdersBeforeSubmit($(this));
        });
    });

    function setupPlaceholder(input) {

        var placeholderText = input.attr('placeholder');

        setPlaceholderOrFlagChanged(input, placeholderText);
        input.focus(function(e) {
            if (input.data('changed') === true) return;
            if (input.val() === placeholderText) input.val('');
        }).blur(function(e) {
            if (input.val() === '') input.val(placeholderText); 
        }).change(function(e) {
            input.data('changed', input.val() !== '');
        });
    }

    function setPlaceholderOrFlagChanged(input, text) {
        (input.val() === '') ? input.val(text) : input.data('changed', true);
    }

    function setupPasswords(input) {
        var passwordPlaceholder = createPasswordPlaceholder(input);
        input.after(passwordPlaceholder);

        (input.val() === '') ? input.hide() : passwordPlaceholder.hide();

        $(input).blur(function(e) {
            if (input.val() !== '') return;
            input.hide();
            passwordPlaceholder.show();
        });
			
        $(passwordPlaceholder).focus(function(e) {
            input.show().focus();
            passwordPlaceholder.hide();
        });
    }

    function createPasswordPlaceholder(input) {
        return $('<input>').attr({
            placeholder: input.attr('placeholder'),
            value: input.attr('placeholder'),
            id: input.attr('id'),
            readonly: true
        }).addClass(input.attr('class'));
    }

    function clearPlaceholdersBeforeSubmit(form) {
        form.find(':input[placeholder]').each(function() {
            if ($(this).data('changed') === true) return;
            if ($(this).val() === $(this).attr('placeholder')) $(this).val('');
        });
    }
})(jQuery);