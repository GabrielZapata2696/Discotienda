/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.discotienda.service;

import com.mycompany.discotienda.pojo.Album;
import com.mycompany.discotienda.pojo.Artista;
import com.mycompany.discotienda.pojo.Cancion;
import com.mycompany.discotienda.repo.*;
import java.util.List;

/**
 *
 * @author SpacemanFromHell
 */
public class ArtistaService {

    public List<Artista> BuscarArtistas() {
        return new DAOAdmin().ConsultarArtistas();
    }

    public boolean EditarArtista(int id, String columna, Object newValue) {

        Artista artista = this.BuscarArtistaXId(id);
        switch (columna.toLowerCase()) {
            case "nombre":
                artista.setNombre(newValue.toString());
                break;
            case "descripcion":
                artista.setDescripcion(newValue.toString());
                break;
            default:
                break;
        }

        return new DAOArtista().EditarArtista(artista);
    }

    public Artista BuscarArtistaXId(int id) {
        return new DAOArtista().Consultar_ArtistaXId(id);
    }

    public boolean EliminarArtista(String idArtista) {
        List<Album> listaAlbumes = new AdminService().BuscarAlbumes_XArtista(Integer.parseInt(idArtista));
        boolean val = false;
        if (!listaAlbumes.isEmpty()) {
            val = false;
        } else {
            val = new DAOArtista().EliminarArtista(Integer.parseInt(idArtista));
        }
        return val;
    }
    
     public boolean CrearArtista(Artista newArtista) {
        return new DAOArtista().CrearArtista(newArtista);
    }
    
     public String ObtenerNombreArtista(int idArtista){
         return (this.BuscarArtistaXId(idArtista)).getNombre();         
     }
}
