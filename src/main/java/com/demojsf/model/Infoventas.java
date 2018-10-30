/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.model;

/**
 *
 * @author Carlos
 */
public class Infoventas {
    
    private int mes;
    private double valorTotal;

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    @Override
    public String toString() {
        return "InfoVentas{" + "mes=" + mes + ", ValorTotal=" + valorTotal + '}';
    }
    
    
    
    
}
