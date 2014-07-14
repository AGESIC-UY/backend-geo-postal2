/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

/**
 *
 * @author Camila.Riveron
 */
public class BarraCampoCapa {
    private String campo;   
    private boolean like;
    private boolean filtro;
    private String capa;

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
      
    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isFiltro() {
        return filtro;
    }

    public void setFiltro(boolean filtro) {
        this.filtro = filtro;
    }   
}
