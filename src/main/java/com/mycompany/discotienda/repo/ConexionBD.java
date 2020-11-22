/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 20/11/2020
 * @version 1.0.0
 */
public class ConexionBD {

    public ConexionBD() {
    }

    public Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/discotienda", "postgres", "1234");
            DriverManager.getDriver("org.postgresql.Driver");         
            return conexion;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOLogin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        return conexion;

    }

}
