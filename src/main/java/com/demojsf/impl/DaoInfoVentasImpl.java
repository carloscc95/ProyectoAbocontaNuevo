/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demojsf.impl;

import com.demojsf.dao.DaoInfoVentas;
import com.demojsf.db.JdbcConnect;
import com.demojsf.model.Infoventas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class DaoInfoVentasImpl implements DaoInfoVentas<Infoventas>{
    
    public List<Infoventas> InfoVenta(int Mes_ini,int Mes_Fin){
        List<Infoventas> lista = new ArrayList<>();
       
         Connection connect = null;
        try {
           
            connect = JdbcConnect.getConnect();
            PreparedStatement pstFacturas = connect.prepareStatement("SELECT MONTH(fecha_facturacion) AS Mes\n"
                    +", SUM(valor_total) As total_mes FROM facturas WHERE month(fecha_facturacion) between Mes_Ini and Mes_Fin\n" 
                    +" GROUP BY Mes");
            
                  
           ResultSet rsFacturasMes = pstFacturas.executeQuery();
           
            while (rsFacturasMes.next()) {
                
                Infoventas info = new Infoventas();
                
                info.setMes(rsFacturasMes.getInt(1));
                info.setValorTotal(rsFacturasMes.getDouble(2));
                lista.add(info);
            }
           
            
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoInfoVentasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DaoInfoVentasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    
   return lista;
    
    
    }   

    @Override
    public void GenerarInforme() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
