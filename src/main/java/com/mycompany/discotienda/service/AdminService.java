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
    
    public boolean EliminarCliente(int idUsuario ){
        return new DAOAdmin().EliminarCliente(idUsuario);
    }
    public List<Artista> BuscarArtistas(){
        return new DAOAdmin().ConsultarArtistas();
    }
    public List<Album> BuscarAlbumes_XArtista(int idArtista){
        return new DAOAdmin().ConsultarAlbumes_XArtista(idArtista);
    }
    public List<Cancion> BuscarCanciones_XAlbum(int idAlbum){
        return new DAOAdmin().ConsultarCanciones_XAlbum(idAlbum);
    }
            
    
}
