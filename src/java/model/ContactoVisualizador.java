/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Silvina.Lizardi
 */
@Entity
@Table(name = "contacto_visualizador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactoVisualizador.findAll", query = "SELECT c FROM ContactoVisualizador c"),
    @NamedQuery(name = "ContactoVisualizador.findById", query = "SELECT c FROM ContactoVisualizador c WHERE c.id = :id"),
    @NamedQuery(name = "ContactoVisualizador.findByNombre", query = "SELECT c FROM ContactoVisualizador c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ContactoVisualizador.findByEmail", query = "SELECT c FROM ContactoVisualizador c WHERE c.email = :email"),
    @NamedQuery(name = "ContactoVisualizador.findByMensaje", query = "SELECT c FROM ContactoVisualizador c WHERE c.mensaje = :mensaje"),
    @NamedQuery(name = "ContactoVisualizador.findByLink", query = "SELECT c FROM ContactoVisualizador c WHERE c.link = :link"),
    @NamedQuery(name = "ContactoVisualizador.findByFecha", query = "SELECT c FROM ContactoVisualizador c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "ContactoVisualizador.findByVerificado", query = "SELECT c FROM ContactoVisualizador c WHERE c.verificado = :verificado"),
    @NamedQuery(name = "ContactoVisualizador.findByRespuesta", query = "SELECT c FROM ContactoVisualizador c WHERE c.respuesta = :respuesta")})
public class ContactoVisualizador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "email")
    private String email;
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "link")
    private String link;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "verificado")
    private Boolean verificado;
    @Column(name = "respuesta")
    private String respuesta;

    public ContactoVisualizador() {
    }

    public ContactoVisualizador(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public ContactoVisualizador(String nombre, String email, String mensaje, String link, Date fecha, Boolean verificado) {
        this.nombre = nombre;
        this.email = email;
        this.mensaje = mensaje;
        this.link = link;
        this.fecha = fecha;
        this.verificado = verificado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactoVisualizador)) {
            return false;
        }
        ContactoVisualizador other = (ContactoVisualizador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ContactoVisualizador[ id=" + id + " ]";
    }
    
}
