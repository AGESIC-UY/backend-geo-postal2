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
package servicios;

import java.util.List;

/**
 *
 * @author Camila.Riveron
 */
public class PerfilModel {

    private String NombrePerfil;
    private List<CapasModel> capas;
    private List<BasesModel> bases;
    private List<String> plugins;
    private Boolean usaOSM;
    private Boolean usaGP;
    private Boolean usaGS;
    private Boolean usaGH;
    private Boolean usaGSat;

    public List<String> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<String> plugins) {
        this.plugins = plugins;
    }

    public String getNombrePerfil() {
        return NombrePerfil;
    }

    public void setNombrePerfil(String NombrePerfil) {
        this.NombrePerfil = NombrePerfil;
    }

    public List<CapasModel> getCapas() {
        return capas;
    }

    public void setCapas(List<CapasModel> capas) {
        this.capas = capas;
    }

    public List<BasesModel> getBases() {
        return bases;
    }

    public void setBases(List<BasesModel> bases) {
        this.bases = bases;
    }

    public Boolean getUsaOSM() {
        return usaOSM;
    }

    public void setUsaOSM(Boolean usaOSM) {
        this.usaOSM = usaOSM;
    }

    public Boolean getUsaGP() {
        return usaGP;
    }

    public void setUsaGP(Boolean usaGP) {
        this.usaGP = usaGP;
    }

    public Boolean getUsaGS() {
        return usaGS;
    }

    public void setUsaGS(Boolean usaGS) {
        this.usaGS = usaGS;
    }

    public Boolean getUsaGH() {
        return usaGH;
    }

    public void setUsaGH(Boolean usaGH) {
        this.usaGH = usaGH;
    }

    public Boolean getUsaGSat() {
        return usaGSat;
    }

    public void setUsaGSat(Boolean usaGSat) {
        this.usaGSat = usaGSat;
    }
}
