package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;

import java.util.List;

public class PrestamoLlaveService {

    private final PrestamoLlaveRepository repository = new PrestamoLlaveRepository();

    private final String[] turnos = {"Matutino", "Vespertino", "Laboratorio"};

    public String[] obtenerTurnos() {
        return turnos;
    }

    public List<PrestamoLlave> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public PrestamoLlave buscarPorNombreSolicitante(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return null;
        }
        return repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
    }

    // AGREGAR
    public boolean agregar(PrestamoLlave prestamo) {

        for (PrestamoLlave p : repository.obtenerTodos()) {
            if (p.getNombreSolicitante().equalsIgnoreCase(prestamo.getNombreSolicitante())) {
                return false;
            }
        }

        repository.obtenerTodos().add(prestamo);
        return true;
    }

    // ACTUALIZAR
    public boolean actualizar(String nombreOriginal, PrestamoLlave nuevo) {

        for (PrestamoLlave p : repository.obtenerTodos()) {

            if (p.getNombreSolicitante().equalsIgnoreCase(nombreOriginal)) {

                if (!nombreOriginal.equalsIgnoreCase(nuevo.getNombreSolicitante())) {
                    for (PrestamoLlave otro : repository.obtenerTodos()) {
                        if (otro.getNombreSolicitante().equalsIgnoreCase(nuevo.getNombreSolicitante())) {
                            return false;
                        }
                    }
                }

                p.setNombreSolicitante(nuevo.getNombreSolicitante());
                p.setSalon(nuevo.getSalon());
                p.setTurno(nuevo.getTurno());

                return true;
            }
        }

        return false;
    }

    //  ELIMINAR
    public boolean eliminar(String nombre) {

        for (PrestamoLlave p : repository.obtenerTodos()) {
            if (p.getNombreSolicitante().equalsIgnoreCase(nombre)) {
                repository.obtenerTodos().remove(p);
                return true;
            }
        }

        return false;
    }
}