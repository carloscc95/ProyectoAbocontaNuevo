
package com.demojsf.impl;

import com.demojsf.db.JdbcConnect;
import com.demojsf.model.Comision;
import com.demojsf.model.Contrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.demojsf.dao.DaoComision;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class DaoComisionImpl implements DaoComision<Comision> {

    @Override
    public void liq_comision(Date fecha_reg,int periodo_comi,int idcontrato,int idpropiedad,int propietario,int porc_comi,double valor_comi,double valor_canon, double valor_propietario,int contrato_ini,int contrato_fin) {
          
        //OJO QUITAR ESTE SYSTEM.OUT
        //System.out.println("OJO!!!!... Si entroooo..... 1");
        
        Connection connect = null;
        try {
        
            connect = JdbcConnect.getConnect();
            
            String filtro="";
            
            //Si se coloco algun contrato se guarda un filtro de rango de contratos para aplicar en la consulta de contratos
            if(contrato_ini!=0)
                filtro+=" and between(numcontrato, "+contrato_ini+", "+contrato_fin+")";
            
            
            
            //*************************************************************************************//
            //*************************************************************************************//
            //*** Falta implementar filtro de fecha de vencimiento y tambien rango de contratos ***//
            //*************************************************************************************//
            //*************************************************************************************//
            
            
            
            
            //Buscamos los contratos activos y que la fecha de vencimiento sea menor a la fecha de factura
            //PreparedStatement pstContratos = connect.prepareStatement("Select numcontrato,idclie,idpropiedad,valor from Contrato where estado=\"Activo\" and fecvenc<="+ fec_factu.getTime() + filtro +" order by 1");
            PreparedStatement pstContratos = connect.prepareStatement("SELECT com.fecha_reg, com.periodo_comi, c.numcontrato, com.idpropiedad, com.idpropietario, com.porc_comi, com.valor_canon, c.estado "
                                                                    + "FROM contrato c "
                                                                    + "inner join comision com on c.numcontrato = com.idcontrato "
                                                                    + "where c.estado = 'Activo'");
            ResultSet rsContratos = pstContratos.executeQuery();
            
            //Preparamos sentncia del insert en la tabla de facturas
            PreparedStatement 
                pstInsertar_Comision = connect.prepareStatement("Insert into Comision (fecha_reg,periodo_comi,idcontrato,idpropiedad,"
                        + "idpropietario,observacion,porc_comi,valor_comi,valor_canon,valor_propietario) values(?,?,?,?,?,?,?,?,?,?)");
            
            
            
            //OJO QUITAR ESTE SYSTEM.OUT
            //System.out.println("OJO!!!!... Si entroooo..... 2");
            
            
        
            try{
                
                //Buscamos la informacion de la tabla config
                PreparedStatement pstConfig = connect.prepareStatement("Select num_cons_fact,prefijo_dian,resolu_dian,factura_dian from config");
                ResultSet rsConfig = pstConfig.executeQuery();
                /*
                    //---->Se inicializan las variables de configuracion
                int consecutivo_fact=0;
                String prefijo="";
                String resolucion="";
                String rango_factu="";
                    //---->Se asignan los valores traidos de la tabla config a las variables inicializadas        
                while (rsConfig.next()) {
                    consecutivo_fact=rsConfig.getInt(1);
                    prefijo=rsConfig.getString(2);
                    resolucion=rsConfig.getString(3);
                    rango_factu=rsConfig.getString(4);
                */
                }
                //Fin busqueda de la tabla de config
                
                connect.setAutoCommit(false);

 
                //OJO QUITAR ESTE SYSTEM.OUT
                //System.out.println("OJO!!!!... Si entroooo..... 3");
                
                
                //Comenzamos ciclo de los contratos activos y con fecha de venc menores de la fecha de facturacion
                while (rsContratos.next()) {
                    
                    //System.out.println("Si entro cicloCONTRATOS..... "+consecutivo_fact);
                    
                    //Creamos y preparamos el objeto comision con toda la informacion
                    Comision comi = new Comision();

                    comi.setIdcomision(rsContratos.getInt(1));
                    comi.setFecha_reg(fecha_reg);
                    comi.setPeriodo_comi(periodo_comi);
                    comi.setIdcontrato(idcontrato);
                    comi.setIdpropiedad(idpropiedad);
                    comi.setIdpropietario(propietario);
                    comi.setPorc_comi(porc_comi);
                    comi.setValor_comi(valor_comi);
                    comi.setValor_canon(valor_canon);
                    comi.setValor_propietario(valor_propietario);
                    

                    comi.setValor_canon(rsContratos.getDouble(4));
   
                    comi.setValorComision(comi.getValor_canon() - comi.getValor_propietario());
                    
                    comi.setEstado_contrato("Activo");

                    //Colocamos todos los parametros para realizar insert con el objeto pstInsertar_Facturas creado arriba
                    pstInsertar_Comision.setTimestamp(1, new Timestamp(comi.getFecha_reg().getTime()));
                    pstInsertar_Comision.setInt(2, comi.getPeriodo_comi());
                    pstInsertar_Comision.setInt(3, comi.getIdcontrato());
                    pstInsertar_Comision.setInt(4, comi.getIdpropiedad());
                    pstInsertar_Comision.setInt(5, comi.getIdpropietario());
                    pstInsertar_Comision.setString(6, comi.getObservacion());
                    pstInsertar_Comision.setInt(7, comi.getPorc_comi());
                    pstInsertar_Comision.setDouble (8, comi.getValor_comi());
                    pstInsertar_Comision.setDouble(9, comi.getValor_canon());
                    pstInsertar_Comision.setDouble(10, comi.getValor_propietario());
                    
                    //y tambien se realiza el INSERT correspondiente despues de llenar los datos, con el metodo executeUpdate()
                    pstInsertar_Comision.executeUpdate();
                    
                }
                
                connect.commit();
                
            }catch (SQLException e){
                connect.rollback();
                
                
                System.out.println("ERROR en consulta SQL.");
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                
            }
            finally
            {
              try
              {
                 connect.setAutoCommit(true);
                 connect.close();
              }
               catch (SQLException e) { 
               
               }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                    
                    System.out.println("ERROR en consulta SQL.");
                    System.out.println("SQLException: " + ex.getMessage());

                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoFacturaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void save(Comision c) {
        try {
            if (existe(c)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existente!", "Comision ya existe...."));
            } else {

                Connection connect = null;
                try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.
                    prepareStatement("Insert into Comision (idcomision,fecha_reg,periodo_comi,idcontrato,idpropiedad,idpropietario,observacion,porc_comi,valor_comi,valor_canon,valor_propietario) values(?,?,?,?,?,?,?,?,?,?,?)");
            pst.setInt(1, c.getIdcomision());
            pst.setTimestamp(2, new Timestamp(c.getFecha_reg().getTime()));
            pst.setInt(3, c.getPeriodo_comi());
            pst.setInt(4, c.getIdcontrato());
            pst.setInt(5, c.getIdpropiedad());
            pst.setInt(6, c.getIdpropietario());
            pst.setString(7, c.getObservacion());
            pst.setInt(8, c.getPorc_comi());
            pst.setDouble(9, c.getValor_comi());
            pst.setDouble(10, c.getValor_canon());
            pst.setDouble(11, c.getValor_propietario());
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        } catch (SQLException ex) {
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       

    @Override
    public void update(Comision c) {
        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.
                    prepareStatement("Update Comision set fecha_reg=?,periodo_comi=?,id_contrato=?,idpropiedad=?,idpropietario=?,observacion=?,porc_comi=?,valor_comi,valor_canon=?,valor_propietario=? where idcomision=?");
            pst.setInt(11, c.getIdcomision());
            pst.setTimestamp(1, new Timestamp(c.getFecha_reg().getTime()));
            pst.setInt(2, c.getPeriodo_comi());
            pst.setInt(3, c.getIdcontrato());
            pst.setInt(4, c.getIdpropiedad());
            pst.setInt(5, c.getIdpropietario());
            pst.setString(6, c.getObservacion());
            pst.setInt(7, c.getPorc_comi());
            pst.setDouble(8, c.getValor_comi());
            pst.setDouble(9, c.getValor_canon());
            pst.setDouble(10, c.getValor_propietario());
            pst.executeUpdate();
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Comision c) {
        Connection connect = null;
        try {

            connect = JdbcConnect.getConnect();

            PreparedStatement pst = connect.prepareStatement("Delete from Comision where idcomision=?");
            
            pst.setInt(1, c.getIdcomision());
            
            pst.executeUpdate();
            
            connect.commit();
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                if (connect != null) {
                    connect.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public List<Comision> getComision() {
        List<Comision> lista = new ArrayList<>();
        try {
            Connection connect = JdbcConnect.getConnect();
            PreparedStatement pst = connect.
                    prepareStatement("Select * from Comision order by 1");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Comision c = new Comision();
                c.setIdcomision(rs.getInt(1));
                c.setFecha_reg(rs.getDate(2));
                c.setPeriodo_comi(rs.getInt(3));
                c.setIdcontrato(rs.getInt(4));
                c.setIdpropiedad(rs.getInt(5));
                c.setIdpropietario(rs.getInt(6));
                c.setObservacion(rs.getString(7));
                c.setPorc_comi(rs.getInt(8));
                c.setValor_comi(rs.getDouble(9));
                c.setValor_canon(rs.getDouble(10));
                c.setValor_propietario(rs.getDouble(11));
                lista.add(c);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DaoComisionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
     public boolean existe(Comision c) throws SQLException, ClassNotFoundException {

        Connection connect = JdbcConnect.getConnect();
        PreparedStatement pst = connect.prepareStatement("Select * from Comision where idcomision=?");
        pst.setInt(1, c.getIdcomision());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            return true;
        }

        return false;
    }   
}
