/**
 * 
 */
package org.lapaloma.hogwarts.service;

import java.util.List;

import org.lapaloma.hogwarts.dao.ICasaDAO;
import org.lapaloma.hogwarts.excepcion.CasaNoEncontradaException;
import org.lapaloma.hogwarts.vo.Casa;
import org.springframework.stereotype.Service;

@Service
public class CasaService {

    private final ICasaDAO casaDAO;

    // Spring inyecta el DAO automáticamente
    public CasaService(ICasaDAO casaDAO) {
        this.casaDAO = casaDAO;
    }
    
    
    public Casa obtenerCasaPorClave(Integer identificador) {
    	
    	
        if (identificador == null ||  identificador <= 0) {
            throw new IllegalArgumentException("Código inválido");
        }

        Casa Casa = casaDAO.obtenerCasaPorClave(identificador);

        
        if (Casa == null) {
            throw new CasaNoEncontradaException(
                    "No existe una casa con idenficador: " + identificador
            );
        }
    	
        return Casa;
    }

    public List<Casa> obtenerListaCasas() {

        List<Casa> lista = casaDAO.obtenerListaCasas();

        // Esto provoca error
        //lista = null;
        
        if (lista == null || lista.isEmpty()) {
            throw new RuntimeException("No hay casas disponibles");
        }

        return lista;
    }

    public List<Casa> obtenerCasaPorNombre(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }

        List<Casa> lista = casaDAO.obtenerCasaPorNombre(nombre);

        // Esto provoca error s
        //lista =null;
        
        if (lista == null || lista.isEmpty()) {
            throw new CasaNoEncontradaException(
                    "No existen casas con nombre: " + nombre
            );
        }

        return lista;
    }
}