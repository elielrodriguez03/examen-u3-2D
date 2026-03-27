package com.example.prestamollaves.repository;

import com.example.prestamollaves.model.PrestamoLlave;

import java.util.ArrayList;
import java.util.List;

public class PrestamoLlaveRepository {

    private final List<PrestamoLlave> registros = new ArrayList<>();

    //Guardar (alias de agregar)
    public void guardar(PrestamoLlave registro){
        registros.add(registro);
    }

    // Para que coincida con el service
    public void agregar(PrestamoLlave registro) {
        guardar(registro);
    }

    public List<PrestamoLlave> obtenerTodos() {
        return registros;
    }

    public PrestamoLlave buscarPorNombreSolicitante(String nombreSolicitante) {
        for (PrestamoLlave actual : registros) {
            if (actual.getNombreSolicitante().equalsIgnoreCase(nombreSolicitante)) {
                return actual;
            }
        }
        return null;
    }

    public boolean eliminarPorNombreSolicitante(String nombreSolicitante) {
        for (int i = 0; i < registros.size(); i++) {
            PrestamoLlave actual = registros.get(i);
            if (actual.getNombreSolicitante().equalsIgnoreCase(nombreSolicitante)) {
                registros.remove(i);
                return true;
            }
        }
        return false;
    }

    // Para que coincida con el service
    public boolean eliminar(String nombreSolicitante) {
        return eliminarPorNombreSolicitante(nombreSolicitante);
    }
}