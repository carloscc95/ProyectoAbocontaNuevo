
package com.demojsf.model;

import java.util.Date;
 
 public class Comision {

    private int idcomision;
    private Date fecha_reg=new Date();
    private int periodo_comi;
    private int idcontrato;
    private int idpropiedad;
    private int idpropietario;
    private String observacion;
    private int porc_comi;
    private double valor_comi;
    private double valor_canon;
    private double valor_propietario;
    private String NombrePropiedad;
    private String NombrePropietario;

    public String getNombrePropiedad() {
        return NombrePropiedad;
    }

    public void setNombrePropiedad(String NombrePropiedad) {
        this.NombrePropiedad = NombrePropiedad;
    }

    public String getNombrePropietario() {
        return NombrePropietario;
    }

    public void setNombrePropietario(String NombrePropietario) {
        this.NombrePropietario = NombrePropietario;
    }
    
    

    public int getIdcomision() {
        return idcomision;
    }

    public void setIdcomision(int idcomision) {
        this.idcomision = idcomision;
    }

    public Date getFecha_reg() {
        return fecha_reg;
    }

    public void setFecha_reg(Date fecha_reg) {
        this.fecha_reg = fecha_reg;
    }

    public int getPeriodo_comi() {
        return periodo_comi;
    }

    public void setPeriodo_comi(int periodo_comi) {
        this.periodo_comi = periodo_comi;
    }

    public int getIdcontrato() {
        return idcontrato;
    }

    public void setIdcontrato(int idcontrato) {
        this.idcontrato = idcontrato;
    }

    public int getIdpropiedad() {
        return idpropiedad;
    }

    public void setIdpropiedad(int idpropiedad) {
        this.idpropiedad = idpropiedad;
    }

    public int getIdpropietario() {
        return idpropietario;
    }

    public void setIdpropietario(int idpropietario) {
        this.idpropietario = idpropietario;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getPorc_comi() {
        return porc_comi;
    }

    public void setPorc_comi(int porc_comi) {
        this.porc_comi = porc_comi;
    }

    public double getValor_comi() {
        return valor_comi;
    }

    public void setValor_comi(double valor_comi) {
        this.valor_comi = valor_comi;
    }

    public double getValor_canon() {
        return valor_canon;
    }

    public void setValor_canon(double valor_canon) {
        this.valor_canon = valor_canon;
    }

    public double getValor_propietario() {
        return valor_propietario;
    }

    public void setValor_propietario(double valor_propietario) {
        this.valor_propietario = valor_propietario;
    }

    
    
    @Override
    public String toString() {
        return "Comision{" + "idcomision=" + idcomision + ", fecha_reg=" 
                + fecha_reg + ", periodo_comi=" + periodo_comi + ", idcontrato=" 
                + idcontrato + ", idpropiedad=" + idpropiedad + ", idpropietario=" 
                + idpropietario + ", observacion=" + observacion + ", porc_comi=" 
                + porc_comi + ", valor_comi=" + valor_comi + ", valor_canon=" 
                + valor_canon + ", valor_propietario=" + valor_propietario + '}';
    }
    
    
}
