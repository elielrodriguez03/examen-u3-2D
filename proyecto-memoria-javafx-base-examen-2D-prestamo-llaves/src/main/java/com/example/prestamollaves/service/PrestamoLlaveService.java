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

    public String agregar(String nombreSolicitante, String salon, String turno) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "El nombre del solicitante no puede estar vacio.";
        if (salon == null || salon.trim().isEmpty()) return "El salon no puede estar vacio.";
        if (turno == null) return "Debe seleccionar un turno.";

        if (buscarPorNombreSolicitante(nombreSolicitante) != null) {
            return "Ya existe un registro con ese nombre de solicitante.";
        }

        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(nuevo);

        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) return "No hay un registro original seleccionado para actualizar.";
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) return "El nombre no puede estar vacio.";
        if (salon == null || salon.trim().isEmpty()) return "El salón no puede estar vacio" + "o.";
        if (turno == null) return "Debe seleccionar un turno.";

        PrestamoLlave existente = buscarPorNombreSolicitante(nombreOriginal);
        if (existente == null) {
            return "El registro original no existe en la base de datos.";
        }

        // Si el usuario cambió el nombre, validamos que el nuevo nombre no le pertenezca a alguien mas
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (buscarPorNombreSolicitante(nombreNuevo) != null) {
                return "El nuevo nombre ya esta registrado por otro solicitante.";
            }
        }

        existente.setNombreSolicitante(nombreNuevo.trim());
        existente.setSalon(salon.trim());
        existente.setTurno(turno);

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre del solicitante no puede estar vacio.";
        }

        if (buscarPorNombreSolicitante(nombreSolicitante) == null) {
            return "No se encontro el registro para eliminar.";
        }

        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (eliminado) {
            return null;
        } else {
            return "Ocurrio un error inesperado al intentar eliminar el registro.";
        }
    }
}