/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.jsf.bean;

import com.demojsf.impl.DaoInfoVentasImpl;
import com.demojsf.model.Infoventas;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;


@Named(value = "infoventasBean")
@ViewScoped
public class InfoVentasJSFManagedBean implements Serializable {
    private Infoventas infoventas = new Infoventas();
    private DaoInfoVentasImpl dao = new DaoInfoVentasImpl();
    private List<Infoventas> lista = new ArrayList<>();
    private ChartSeries InfoData;
    private BarChartModel barModel;
    private double valorMax = 0;

    
    private int Mes_Ini;
    private int Mes_Fin;

    public int getMes_Ini() {
        return Mes_Ini;
    }

    public void setMes_Ini(int Mes_Ini) {
        this.Mes_Ini = Mes_Ini;
    }

    public int getMes_Fin() {
        return Mes_Fin;
    }

    public void setMes_Fin(int Mes_Fin) {
        this.Mes_Fin = Mes_Fin;
    }
    
  
    
    public void GenerarInforme() {
        dao.InfoVenta(Mes_Ini,Mes_Fin);
        lista = dao.InfoVenta(Mes_Ini, Mes_Fin);
    }

    public List<Infoventas> getLista() {
        return lista;
    }

    public void setLista(List<Infoventas> lista) {
        this.lista = lista;
    }
    
    public BarChartModel getBarModel(){
        return barModel;
    }
    
    private BarChartModel initBarModel(){
        BarChartModel model = new BarChartModel();
        
        InfoData = new ChartSeries();
        InfoData.setLabel("ValorTtotal");
        valorMax = 0;
        for (Infoventas cData: lista){
            if(cData.getValorTotal() > valorMax ){
                valorMax = cData.getValorTotal();
            }
            InfoData.set(cData.getMes(), cData.getValorTotal());
        }
        
        model.addSeries(InfoData);
        
        return model;
               
    }
    
    private void createBarModel(){
        barModel = initBarModel();
        
        barModel.setTitle("Informe de ventas mensual");
        barModel.setLegendPosition("ne");
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Mes");
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Valor Total");
        yAxis.setMin(0);
        yAxis.setMax(valorMax);
        
        
        
    }
    
    
    
    
    
}
