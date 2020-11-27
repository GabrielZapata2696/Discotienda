/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.repo;

import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.pojo.Compra;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author SpacemanFromHell
 */
public class DAOCompra {

    public boolean RealizarCompra(Compra compra) {
        String _SQL = "SELECT * FROM tienda.f_crear_compra( ?, ?, ?, ?::text);";
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        java.sql.Date sqlStartDate = Date.valueOf(LocalDate.now());
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, compra.getIdUser());
            preparedStatement.setDate(2, sqlStartDate);
            preparedStatement.setDouble(3, compra.getTotal());
            preparedStatement.setString(4, compra.getArticulosSerializados());
            ResultSet rs = preparedStatement.executeQuery();
            rs = preparedStatement.getResultSet();
            resultado = true;
        } catch (SQLException e) {
            e.printStackTrace();
            resultado = false;
        } catch (NullPointerException nullex) {
            nullex.printStackTrace();
            resultado = false;
        } finally {
            try {
                con.close();
                _SQL = "";
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return resultado;

    }
}
