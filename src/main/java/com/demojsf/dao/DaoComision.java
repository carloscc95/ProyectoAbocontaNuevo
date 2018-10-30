
package com.demojsf.dao;

import java.util.Date;
import java.util.List;

/**
 *
 * @author docente
 * @param <Comision>
 */
public interface DaoComision<Comision> {
    void liq_comision(Date fecha_reg,int periodo_comi,int idcontrato,int idpropiedad,int propietario,int porc_comi,double valor_comi,double valor_canon, double valor_propietario,int contrato_ini,int contrato_fin);
    void save(Comision c);
    void update(Comision c);
    void delete(Comision c);
    List<Comision> getComision();
}
