
package com.demojsf.dao;

import java.util.Date;
import java.util.List;

/**
 *
 * @author docente
 * @param <Comision>
 */
public interface DaoComision<Comision> {
    void liq_comision(int periodo);
    void save(Comision c);
    void update(Comision c);
    void delete(Comision c);
    List<Comision> getComision(int periodo);
    List<Comision> getListado(int periodo);
    
   
}
