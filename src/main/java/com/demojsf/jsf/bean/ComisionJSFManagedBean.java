
package com.demojsf.jsf.bean;

import com.demojsf.impl.DaoComisionImpl;
import com.demojsf.model.Comision;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.demojsf.model.Propiedad;
import com.demojsf.dao.DaoComision;
import java.sql.SQLException;
import java.util.Date;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@Named(value = "comisionBean")
@ViewScoped

public class ComisionJSFManagedBean implements Serializable {

    private Comision comision = new Comision();
    private List<Comision> lista = new ArrayList<>();
    //private ListCargar<Comision> lista = new ArrayList<>();
    private List<Comision> listaInforme = new ArrayList<>();
    private DaoComisionImpl dao = new DaoComisionImpl();
    private boolean modoInsert = false;
    private boolean modoEdit = true;
    private ChartSeries ComiData;
    private BarChartModel barModel;
    private double valorMax = 0;
    
    //Variable De periodo de la vista
    private int periodo= new Date().getMonth()+1;

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    
    //fin de variable de la vista
    
    public boolean isModoInsert() {
        return modoInsert;
    }

    public void setModoInsert(boolean modoInsert) {
        this.modoInsert = modoInsert;
    }

    public boolean isModoEdit() {
        return modoEdit;
    }

    public void setModoEdit(boolean modoEdit) {
        this.modoEdit = modoEdit;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    public List<Comision> getListaInforme() {
        return listaInforme;
    }

    public void setListaInforme(List<Comision> listaInforme) {
        this.listaInforme = listaInforme;
    }
    
    

    @PostConstruct
    public void iniciar() {
        //dao.liq_comision(periodo);
        lista = dao.getComision(periodo);
        listaInforme = dao.getListado(periodo);
        createBarModel();
       
    }

    public List<Comision> getLista() {
        return lista;
    }

    public void setLista(List<Comision> lista) {
        this.lista = lista;
    }
    
    /*public ListCargar<Comision> getLista() {
        return lista;
    }
    
    public void setLista(ListCargar<Comision> lista) {
        this.lista = lista;
    }*/

    public Comision getComision() {
        return comision;
    }

    public ComisionJSFManagedBean() {
    }
    
    public void liquidar(){
        //dao.liq_comision(periodo);
    }    
    public void save() throws ClassNotFoundException, SQLException {
        
        dao.liq_comision(periodo);
        
        //dao.save(comision);
        lista = dao.getComision(periodo);
        comision = new Comision();
        //comision.setIdcomision(lista.size() + 1);
    }
    
    public void delete() {

        dao.delete(comision);
        lista = dao.getComision(periodo);
        comision = new Comision();
        comision.setIdcomision(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void update() {

        dao.update(comision);
        lista = dao.getComision(periodo);
        comision = new Comision();
        comision.setIdcomision(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void changeMode() {
        modoEdit = false;
        modoInsert = true;
    }
    
    public BarChartModel getBarModel(){
        return barModel;
    }
    
    private BarChartModel initBarModel(){
        BarChartModel model = new BarChartModel();
        
        ComiData = new ChartSeries();
        ComiData.setLabel("Valor Comision");
        valorMax = 0;
        for (Comision cData: listaInforme){
            if(cData.getValor_comi() > valorMax ){
                valorMax = cData.getValor_comi();
            }
            ComiData.set(cData.getPeriodo_comi(), cData.getValor_comi());
        }
        
        if(valorMax==0){ 
            ComiData.set(0, 0);
        }
        
        model.addSeries(ComiData);
        
        return model;
               
    }
    
    private void createBarModel(){
        barModel = initBarModel();
        
        barModel.setTitle("Valor de comisiones por mes");
        barModel.setLegendPosition("ne");
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Mes");
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Valor Comision");
        yAxis.setMin(0);
        yAxis.setMax(valorMax);
        
        
        
    }
    
    
    
}
