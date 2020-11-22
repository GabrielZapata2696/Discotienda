/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.repo;

import com.mycompany.discotienda.pojo.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.discotienda.repo.ConexionBD;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Zapata
 * @author Mary Zapata
 * @since 19/11/2020
 * @version 1.0.0
 */
public class DAOLogin {

    public Usuario AutenticarUsuario(String _correo, String _clave) {             
        String _SQL =  "SELECT * FROM tienda.f_autenticar_usuario( ?, ?);";        
        Usuario user = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {            
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setString(1, _correo);
            preparedStatement.setString(2, _clave);                     
            ResultSet rs = preparedStatement.executeQuery();            
            rs = preparedStatement.getResultSet();            
            while (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setApellido(rs.getString("apellido"));
                user.setDocumento(rs.getString("documento"));
                user.setEmail(rs.getString("correo"));
                user.setPassword(rs.getString("clave"));
                user.setRol(rs.getInt("rol_id"));
                user.setEstado(rs.getInt("estado"));              
                preparedStatement.close();               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {                 
                con.close();
                _SQL = "";
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return user;
    }

   
}
