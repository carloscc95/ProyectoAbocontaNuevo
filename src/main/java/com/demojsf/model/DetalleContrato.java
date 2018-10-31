/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.model;

import java.util.Date;


/**
 *
 * @author docente
 */
//Nueva
    
 public class DetalleContrato {

    
    private int idDetalleContratoConcep;
    private int idcontra;
    private int idconc;
    private double valorMensual;
    private double valorPagado;
     private double valorTotalPagar;
    private int porcIva;
    private String estado;
    private Date periodo_ini;
    private Date periodo_fin;

    public double getValorTotalPagar() {
        return valorTotalPagar;
    }

    public void setValorTotalPagar(double valorTotalPagar) {
        this.valorTotalPagar = valorTotalPagar;
    }
   

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public int getPorcIva() {
        return porcIva;
    }

    public void setPorcIva(int porcIva) {
        this.porcIva = porcIva;
    }
    

    public int getIdDetalleContratoConcep() {
        return idDetalleContratoConcep;
    }

    public int getIdcontra() {
        return idcontra;
    }

    public int getIdconc() {
        return idconc;
    }

    
    public String getEstado() {
        return estado;
    }

    public Date getPeriodo_ini() {
        return periodo_ini;
    }

    public Date getPeriodo_fin() {
        return periodo_fin;
    }

    public void setIdDetalleContratoConcep(int idDetalleContratoConcep) {
        this.idDetalleContratoConcep = idDetalleContratoConcep;
    }

    public void setIdcontra(int idcontra) {
        this.idcontra = idcontra;
    }

    public void setIdconc(int idconc) {
        this.idconc = idconc;
    }

   

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPeriodo_ini(Date periodo_ini) {
        this.periodo_ini = periodo_ini;
    }

    public void setPeriodo_fin(Date periodo_fin) {
        this.periodo_fin = periodo_fin;
    }
    
    
  
    @Override
    public String toString() {
        return "DetalleContrato{" + "idDetalleContratoConcep=" + idDetalleContratoConcep 
                + ", idcontra=" + idcontra + ", idconc=" + idconc 
                + ", valorMensual=" + valorMensual  + ", valorPagado=" + valorPagado 
                + ", valorTotalPagar=" + valorTotalPagar 
                + ", porcIva=" + porcIva  +  ", estado=" + estado 
                +  ", periodo_ini=" + periodo_ini + ", periodo_fin=" + periodo_fin +'}';
    }
    
    
}
