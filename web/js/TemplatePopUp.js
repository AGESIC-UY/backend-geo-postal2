/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function mostrarPopUp(){
    var template = document.getElementById("formularioCapas:templatePopUpCapaToModify").value;
    var hash = {};
    hash['hola'] = 'chau';
    hash['bla'] = 'blo';
    var myString = "<h1><#hola></h1>";
    var match = myRegexp.exec(myString);
    alert(match[1]);
    var keys = Object.keys(hash);
    for(var x = 0; x<keys.length; x++ ){
        alert(keys[x]);
    }
    alert(template);
/*    var template = document.getElementById("formularioCapas:templatePopUpCapaToModify").value;
    var hash = {};
    hash['hola'] = 'chau';
    hash['bla'] = 'blo';
    var myString = "something format_abc";
    var myRegexp = /(?:^|\s)format_(.*?)(?:\s|$)/g;
    var match = myRegexp.exec(myString);
    alert(match[1]);
    alert(template);*/
}

function ocultarPopUp(){
}


function remplazarCampos(template, toReplace){
    
    
    
}
