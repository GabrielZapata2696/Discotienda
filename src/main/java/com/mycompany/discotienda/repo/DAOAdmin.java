/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.repo;

import com.mycompany.discotienda.pojo.Album;
import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.pojo.Cancion;
import com.mycompany.discotienda.pojo.Usuario;
import com.sun.javafx.scene.control.skin.VirtualFlow;
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
public class DAOAdmin {

    public List<Usuario> ConsultarClientes() {
        String _SQL = "SELECT * FROM tienda.f_consultar_clientes();";
        List<Usuario> listadoUsers = new ArrayList<Usuario>();
        Usuario user = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
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
                listadoUsers.add(user);
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
        return listadoUsers;
    }

    public Usuario Consultar_ClienteXId(int id) {
        String _SQL = "SELECT * FROM tienda.f_consultar_cliente_x_id( ?);";
        Usuario user = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, id);
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
        return user;
    }

    public boolean EditarCliente(Usuario user) {
        String _SQL = "SELECT * FROM tienda.f_editar_cliente_x_id( ?, ?::text, ?::text, ?::text, ?::text, ?::text);";
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getNombre());
            preparedStatement.setString(3, user.getApellido());
            preparedStatement.setString(4, user.getDocumento());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
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

    public boolean EliminarCliente(int idUsuario) {
        String _SQL = "SELECT * FROM tienda.f_eliminar_cliente( ?);";
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        boolean resultado = false;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, idUsuario);
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

    public List<Artista> ConsultarArtistas() {
        String _SQL = "SELECT * FROM tienda.f_consultar_artistas();";
        List<Artista> listadoArtistas = new ArrayList<Artista>();
        Artista artista = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            rs = preparedStatement.getResultSet();
            while (rs.next()) {
                artista = new Artista();
                artista.setId(rs.getInt("id"));
                artista.setNombre(rs.getString("nombre"));
                artista.setUrl_img(rs.getString("url_imagen"));
                artista.setEstado(rs.getInt("estado"));
                listadoArtistas.add(artista);
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
        return listadoArtistas;
    }

    public List<Album> ConsultarAlbumes_XArtista(int idArtista) {
        String _SQL = "SELECT * FROM tienda.f_consultar_album_x_artista( ?);";
        Album album = null;
        List<Album> listaAlbum = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, idArtista);
            ResultSet rs = preparedStatement.executeQuery();
            rs = preparedStatement.getResultSet();
            listaAlbum = new ArrayList<Album>();
            while (rs.next()) {
                album = new Album();
                album.setId_album(rs.getInt("id_album"));
                album.setNombre(rs.getString("nombre"));
                album.setPrecio(rs.getDouble("precio"));               
                album.setEstado(rs.getInt("estado"));
                album.setId_artista(rs.getInt("id_artista"));
                album.setAnio(rs.getInt("anio"));
                album.setDuracion(rs.getInt("duracion"));
                album.setUrl_img(rs.getString("img_url"));                                                
                listaAlbum.add(album);
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
        return listaAlbum;

    }
    
    
    public List<Cancion> ConsultarCanciones_XAlbum(int idAlbum){
        String _SQL = "SELECT * FROM tienda.f_consultar_canciones_x_album( ?);";
        Cancion cancion = null;
        List<Cancion> listaCanciones = null;
        ConexionBD bd = new ConexionBD();
        Connection con = bd.getConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(_SQL);
            preparedStatement.setInt(1, idAlbum);
            ResultSet rs = preparedStatement.executeQuery();
            rs = preparedStatement.getResultSet();
            listaCanciones = new ArrayList<Cancion>();
            while (rs.next()) {
                cancion = new Cancion();
                cancion.setId(rs.getInt("id"));
                cancion.setNombre(rs.getString("nombre"));
                cancion.setDuracion(rs.getString("duracion"));               
                cancion.setPrecio(rs.getInt("precio"));
                cancion.setEstado(rs.getInt("estado"));
                cancion.setId_artista(rs.getInt("id_artista"));
                cancion.setId_album(rs.getInt("id_album"));
                listaCanciones.add(cancion);
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
        
        return listaCanciones;

    }
    

}
