
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
    public void liq_comision(int periodo){
     
        Connection connect = null;
        try {
            
            boolean continuar=true;
            
            connect = JdbcConnect.getConnect();
            
            
            Comision comi = new Comision();
             
            if (existeLiqPeriodo(periodo)){// Aqui es donde vas a preguntar la validacion que te dice carlos
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existente!", "Periodo ya liquidado...."));
                
                //aqui vas a lanzar pregunta si desea continuar, preguntale a carlos como se lanza el mensaje que el ya sabe
                
                //aqui haces condicional, preguntas si colocaron continuar o no
                //si colocan continuar 
                /*
                if(mesaje==NoContinuar){//ojo aqui no va esto... solo que lo puse pa que sepas que alli dentro tienes que preguntar lo que coloquen en el mensaje emergente
                    continuar=false;
                }
                */
            //ya solo eso... creo que no se necesita mas....

              
            }
            
            if(continuar){
            
            
                

                //Buscamos los contratos activos 
                PreparedStatement pstContratos = connect.prepareStatement("SELECT c.numcontrato, c.idpropied, pt.idpropietario, pt.porccomi, c.valor "
                                                                        + " FROM contrato c "
                                                                        + " inner join propiedad pd on c.idpropied = pd.idpropiedad "
                                                                        + " inner join propietarios pt on  pd.idpropietario = pt.idpropietario "
                                                                        + " where c.estado = \"Activo\" order by 1");
                ResultSet rsContratos = pstContratos.executeQuery();

                //Preparamos sentncia del insert en la tabla de Comision
                PreparedStatement pstInsertar_Comision = connect.prepareStatement("Insert into Comision (fecha_reg,periodo_comi,idcontrato,idpropiedad,"
                            + "idpropietario,observacion,porc_comi,valor_comi,valor_canon,valor_propietario) values(?,?,?,?,?,?,?,?,?,?)");
                try{
                    connect.setAutoCommit(false);

                    //Comenzamos ciclo de los contratos activos
                    while (rsContratos.next()) {

                        //Creamos y preparamos el objeto comision con toda la informacion
                        //Comision comi = new Comision();

                        //comi.setFecha_reg(new Date()); //Fecha actual del PC
                        comi.setPeriodo_comi(periodo); //Variable que pasa por parametro
                        comi.setIdcontrato(rsContratos.getInt(1));
                        comi.setIdpropiedad(rsContratos.getInt(2));
                        comi.setIdpropietario(rsContratos.getInt(3));
                        comi.setObservacion("COMISIÓN CORRESPONDIENTE AL PERIODO "+periodo); //Observacion Automatica
                        comi.setPorc_comi(rsContratos.getInt(4));
                        comi.setValor_comi(rsContratos.getDouble(5)*rsContratos.getInt(4)/100); // --->valor_canon*proc_comi/100
                        comi.setValor_canon(rsContratos.getDouble(5));
                        comi.setValor_propietario(comi.getValor_canon()-comi.getValor_comi());

                        //Colocamos todos los parametros para realizar insert con el objeto pstInsertar_Comision creado arriba
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
            int periodo = 0;

            PreparedStatement pst = connect.prepareStatement("Delete from Comision where periodo_comi = '" + periodo + "'");
            
            pst.setInt(1, c.getPeriodo_comi());
            
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
     
     public boolean existeLiqPeriodo(int periodo) throws SQLException, ClassNotFoundException {
        Connection connect = JdbcConnect.getConnect();
        PreparedStatement pst = connect.prepareStatement("Select periodo_comi from comision where periodo_comi=?");
        pst.setInt(1,periodo);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            return true;
        }

        return false;
    }
}
