/*
 * Licensed under the GPL License.  You may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package utils;

import com.icesoft.faces.context.effects.JavascriptContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Diego.Gonzalez
 */
public class JSFUtils {

    public static void mostrarError(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, mensaje);
        context.addMessage(null, message);
    }

    public static void mostrarMensajeExito(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje);
        context.addMessage(null, message);
    }

    public static void ejecutarJavascript(String valor) {
        JavascriptContext.addJavascriptCall(FacesContext.getCurrentInstance(), valor);
    }
}
