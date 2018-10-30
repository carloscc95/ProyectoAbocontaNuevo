
package com.demojsf.model;

import java.util.Date;

public class DetalleFactura {
    private int idDetafact;
    private int idConc;
    private int idFact;
    private String nomConc;
    private double precio;
    private int porcIva;
    private double valorIva;
    private double valorAntIva;
    private Date fechaReg;

    public int getIdDetafact() {
        return idDetafact;
    }

    public void setIdDetafact(int idDetafact) {
        this.idDetafact = idDetafact;
    }

    public int getIdConc() {
        return idConc;
    }

    public void setIdConc(int idConc) {
        this.idConc = idConc;
    }

    public int getIdFact() {
        return idFact;
    }

    public void setIdFact(int idFact) {
        this.idFact = idFact;
    }

    public String getNomConc() {
        return nomConc;
    }

    public void setNomConc(String nomConc) {
        this.nomConc = nomConc;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getPorcIva() {
        return porcIva;
    }

    public void setPorcIva(int porcIva) {
        this.porcIva = porcIva;
    }

    public double getValorIva() {
        return valorIva;
    }

    public void setValorIva(double valorIva) {
        this.valorIva = valorIva;
    }

    public double getValorAntIva() {
        return valorAntIva;
    }

    public void setValorAntIva(double valorAntIva) {
        this.valorAntIva = valorAntIva;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }
    
    @Override
    public String toString() {
        return "DetalleFactura{" + "idDetafact=" + idDetafact + ", idConc=" + idConc + ", idFact=" + idFact 
                + ", nomConc=" + nomConc + ", precio=" + precio +", porcIva=" + porcIva + ", valorIva=" + valorIva 
                + ", valorAntIva=" + valorAntIva + ", fechaReg=" + fechaReg +'}';
        
    }
}
