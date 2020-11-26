/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.repo;

import com.mycompany.discotienda.pojo.Album;
import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.pojo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SpacemanFromHell
 */
public class DAOArtista {

    public Artista Consultar_ArtistaXId(int id) {
        String _SQL = "SELECT * FROM tienda.f_consultar_artista_x_id( ?);";
        Artista artista = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs = preparedStatement.getResultSet();
            while (rs.next()) {
                artista = new Artista();
                artista.setId(rs.getInt("id"));
                artista.setNombre(rs.getString("nombre"));
                artista.setUrl_img(rs.getString("url_imagen"));
                artista.setEstado(rs.getInt("estado"));
                artista.setDescripcion(rs.getString("descripcion"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException nullex) {
            nullex.printStackTrace();
        } finally {
            try {
                con.close();
                _SQL = "";
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        return artista;
    }

    public boolean EditarArtista(Artista artista) {
        String _SQL = "SELECT * FROM tienda.f_editar_artista_x_id( ?, ?::text, ?::text,?::text);";
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, artista.getId());
            preparedStatement.setString(2, artista.getNombre());
            preparedStatement.setString(3, artista.getUrl_img());
            preparedStatement.setString(4, artista.getDescripcion());
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

    public boolean EliminarArtista(int idArtista) {
        String _SQL = "SELECT * FROM tienda.f_eliminar_artista( ?);";
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, idArtista);
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

    public boolean CrearArtista(Artista newArtista) {
        String _SQL = "SELECT * FROM tienda.f_crear_artista( ?::text, ?::text, ?::text);";
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setString(1, newArtista.getNombre());
            preparedStatement.setString(2, newArtista.getDescripcion());        
            preparedStatement.setString(3, newArtista.getUrl_img());
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
