/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.service;

import com.mycompany.discotienda.controller.SessionKey;
import com.mycompany.discotienda.pojo.Album;
import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.pojo.Cancion;
import com.mycompany.discotienda.pojo.Usuario;
import com.mycompany.discotienda.repo.DAOAdmin;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author SpacemanFromHell
 */
public class AdminService {

    @Inject
    private SessionKey sessionKey;

    public List<Usuario> BuscarClientes() {
        return new DAOAdmin().ConsultarClientes();
    }

    public boolean EditarUsuario(int id, String columna, Object newValue) {

        Usuario user = this.BuscarClienteXId(id);
        switch (columna.toLowerCase()) {
            case "nombre":
                user.setNombre(newValue.toString());
                break;
            case "apellido":
                user.setApellido(newValue.toString());
                break;
            case "documento":
                user.setDocumento(newValue.toString());
                break;
            case "email":
                user.setEmail(newValue.toString());
                break;
            default:
                break;
        }

        return new DAOAdmin().EditarCliente(user);
    }

    public Usuario BuscarClienteXId(int id) {
        return new DAOAdmin().Consultar_ClienteXId(id);
    }

    public boolean EliminarCliente(int idUsuario) {
        return new DAOAdmin().EliminarCliente(idUsuario);
    }

    public boolean EditarAlbum(int id, String columna, Object newValue) {

        Album album = this.BuscarAlbumXId(id);
        switch (columna.toLowerCase()) {
            case "nombre":
                album.setNombre(newValue.toString());
                break;
            case "precio":
                album.setPrecio(Double.parseDouble(newValue.toString()));
                break;
            case "año":
                album.setAnio(Integer.parseInt(newValue.toString()));
                break;
            case "duracion":
                album.setDuracion(Integer.parseInt(newValue.toString()));
                break;
            default:
                break;
        }

        return new DAOAdmin().EditarAlbum(album);
    }

    public List<Artista> BuscarArtistas() {
        return new DAOAdmin().ConsultarArtistas();
    }

    public List<Album> BuscarAlbumes_XArtista(int idArtista) {
        return new DAOAdmin().ConsultarAlbumes_XArtista(idArtista);
    }

    public List<Cancion> BuscarCanciones_XAlbum(int idAlbum) {
        return new DAOAdmin().ConsultarCanciones_XAlbum(idAlbum);
    }

    public Album BuscarAlbumXId(int id) {
        return new DAOAdmin().Consultar_AlbumXId(id);
    }

    public boolean EditarCancion(int id, String columna, Object newValue) {

        Cancion cancion = this.BuscarCancionXId(id);
        switch (columna.toLowerCase()) {
            case "nombre":
                cancion.setNombre(newValue.toString());
                break;
            case "precio":
                cancion.setPrecio(Double.parseDouble(newValue.toString()));
                break;
            case "duracion":
                cancion.setDuracion(newValue.toString());
                break;
            default:
                break;
        }

        return new DAOAdmin().EditarCancion(cancion);
    }

    public Cancion BuscarCancionXId(int id) {
        return new DAOAdmin().Consultar_CancionXId(id);
    }

    public boolean EliminarCancion(String idCancion) {
        boolean val = false;
        //Cancion cancion = this.BuscarCancionXId(Integer.parseInt(idCancion));
        //Album album = this.BuscarAlbumXId(cancion.getId_album());

        if (new DAOAdmin().EliminarCancion(Integer.parseInt(idCancion))) {
            val = true;
            /*
            List<Cancion> listaCanciones = this.BuscarCanciones_XAlbum(album.getId_album());
            int numCancionesAnt = listaCanciones.size() + 1;
            double valAlbumInicial = album.getPrecio();
            double valCancion = valAlbumInicial / numCancionesAnt;
            double valFinalAlbum = valAlbumInicial - valCancion;
            album.setPrecio(valFinalAlbum);
            System.out.println("Nuevo precio Album: " + album.getPrecio());
            if (this.EditarPrecioAlbum(album)) {
                val = true;
            }
            */
        }

        return val;
    }

    public boolean EliminarAlbum(String idAlbum) {
        List<Cancion> listaCanciones = this.BuscarCanciones_XAlbum(Integer.parseInt(idAlbum));
        boolean val = false;
        if (!listaCanciones.isEmpty()) {
            val = false;
        } else {
            val = new DAOAdmin().EliminarAlbum(Integer.parseInt(idAlbum));
        }
        return val;
    }

    public boolean CrearAlbum(Album newAlbum) {
        return new DAOAdmin().CrearAlbum(newAlbum);
    }

    public boolean CrearCancion(Cancion newCancion) {
        boolean val = false;
        Album album = this.BuscarAlbumXId(newCancion.getId_album());
        double precioCancion = album.getPrecio() * 0.1;
        newCancion.setPrecio(precioCancion);
        System.out.println("precio cancion: "+newCancion.getPrecio());
        if (new DAOAdmin().CrearCancion(newCancion)) {
            val = true;
            /* List<Cancion> listaCanciones = this.BuscarCanciones_XAlbum(newCancion.getId_album());
            int numCanciones = listaCanciones.size();
            double valorFinalAlbum = 0.0;
            double valCancion = newCancion.getPrecio();
            double valAlbumInicial = (numCanciones * valCancion);
            valorFinalAlbum = valAlbumInicial - ((valCancion * (numCanciones / 100)) * (valAlbumInicial));
            Album editAlbum = this.BuscarAlbumXId(newCancion.getId_album());
            editAlbum.setPrecio(valorFinalAlbum);
            
            if (this.EditarPrecioAlbum(editAlbum)) {
                val = true;
            }
             */
        }
        return val;
    }

    public boolean EditarPrecioAlbum(Album editAlbum) {
        return new DAOAdmin().EditarAlbum(editAlbum);
    }

}
