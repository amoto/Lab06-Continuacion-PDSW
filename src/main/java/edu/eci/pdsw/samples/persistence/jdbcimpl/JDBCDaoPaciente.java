/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.persistence.jdbcimpl;

import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Paciente;
import edu.eci.pdsw.samples.persistence.DaoPaciente;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hcadavid
 */
public class JDBCDaoPaciente implements DaoPaciente {

    Connection con;

    public JDBCDaoPaciente(Connection con) {
        this.con = con;
    }
        

    @Override
    public Paciente load(int idpaciente, String tipoid) throws PersistenceException {
        PreparedStatement ps;
        Paciente p;
        try {
            String consulta="select id,tipo_id,nombre,fecha_nacimiento from PACIENTES where id = ? and tipo_id = ?";
            ps=con.prepareStatement(consulta);
            ps.setInt(0, idpaciente);
            ps.setString(1, tipoid);
            ResultSet result=ps.executeQuery();
            result.next();
            p= new Paciente(result.getInt(1), result.getString(3), result.getString(2), result.getDate(4));
            String consultaConsultas ="";
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading "+idpaciente,ex);
        }
        return p;
        
    }

    @Override
    public void save(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        try {
        String insertar="insert on PACIENTES (id,tipo_id,nombre,fecha_nacimiento) values(?,?,?,?)";
        ps=con.prepareStatement(insertar);
        ps.setInt(1,p.getId());
        ps.setString(2, p.getTipo_id());
        ps.setString(3, p.getNombre());
        ps.setDate(4, p.getFechaNacimiento());
        int res=ps.executeUpdate();
        if(res!=1)throw new PersistenceException("Ese paciente ya esta registrado");
        con.commit();
        } catch (SQLException ex) {
            throw new PersistenceException("No se registro el paciente correctamente",ex);
        }
        
        throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");

    }

    @Override
    public void update(Paciente p) throws PersistenceException {
        PreparedStatement ps;
        /*try {
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        } */
        throw new RuntimeException("No se ha implementado el metodo 'load' del DAOPAcienteJDBC");
    }
    
}
