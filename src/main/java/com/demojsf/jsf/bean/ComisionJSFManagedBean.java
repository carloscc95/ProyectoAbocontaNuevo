
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

@Named(value = "comisionBean")
@ViewScoped

public class ComisionJSFManagedBean implements Serializable {

    private Comision comision = new Comision();
    private List<Comision> lista = new ArrayList<>();
    //private ListCargar<Comision> lista = new ArrayList<>();
    private DaoComisionImpl dao = new DaoComisionImpl();
    private boolean modoInsert = false;
    private boolean modoEdit = true;
    
    //Variable De periodo de la vista
    private int periodo= new Date().getMonth();

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

    @PostConstruct
    public void iniciar() {
        //lista = dao.getComision();
        //comision.setIdcomision(lista.size() + 1);
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
        lista = dao.getComision();
        comision = new Comision();
        //comision.setIdcomision(lista.size() + 1);
    }
    
    public void delete() {

        dao.delete(comision);
        lista = dao.getComision();
        comision = new Comision();
        comision.setIdcomision(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void update() {

        dao.update(comision);
        lista = dao.getComision();
        comision = new Comision();
        comision.setIdcomision(lista.size() + 1);
        modoEdit = true;
        modoInsert = false;
    }

    public void changeMode() {
        modoEdit = false;
        modoInsert = true;
    }
}
