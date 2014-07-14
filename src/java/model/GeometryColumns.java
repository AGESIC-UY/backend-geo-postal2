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
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Camila.Riveron
 */
@Entity
@Table(name = "geometry_columns")
@NamedQueries({
    @NamedQuery(name = "GeometryColumns.findAll", query = "SELECT g FROM GeometryColumns g")})
public class GeometryColumns implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "f_table_catalog")
    private String fTableCatalog;
    @Column(name = "f_table_schema")
    private String fTableSchema;
    @Column(name = "f_table_name")
    @Id
    private String fTableName;
    @Column(name = "f_geometry_column")
    private String fGeometryColumn;
    @Column(name = "coord_dimension")
    private Integer coordDimension;
    @Column(name = "srid")
    private Integer srid;
    @Column(name = "type")
    @Transient
    public Boolean selected;
    private String type;
    

    public GeometryColumns() {
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }   
    

    public String getFTableCatalog() {
        return fTableCatalog;
    }

    public void setFTableCatalog(String fTableCatalog) {
        this.fTableCatalog = fTableCatalog;
    }

    public String getFTableSchema() {
        return fTableSchema;
    }

    public void setFTableSchema(String fTableSchema) {
        this.fTableSchema = fTableSchema;
    }

    public String getFTableName() {
        return fTableName;
    }

    public void setFTableName(String fTableName) {
        this.fTableName = fTableName;
    }

    public String getFGeometryColumn() {
        return fGeometryColumn;
    }

    public void setFGeometryColumn(String fGeometryColumn) {
        this.fGeometryColumn = fGeometryColumn;
    }

    public Integer getCoordDimension() {
        return coordDimension;
    }

    public void setCoordDimension(Integer coordDimension) {
        this.coordDimension = coordDimension;
    }

    public Integer getSrid() {
        return srid;
    }

    public void setSrid(Integer srid) {
        this.srid = srid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
