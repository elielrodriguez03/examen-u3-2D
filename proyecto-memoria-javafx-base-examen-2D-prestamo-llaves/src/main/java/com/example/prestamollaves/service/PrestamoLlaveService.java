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

            if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
                return "El nombre del solicitante no puede estar vacío.";
            }
            if (salon == null || salon.trim().isEmpty()) {
                return "El salón no puede estar vacío.";
            }
            if (turno == null || turno.trim().isEmpty()) {
                return "El turno no puede ser nulo.";
            }
            if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
                return "Ya existe un registro con ese nombre.";
            }
            PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno.trim());
            repository.guardar(nuevo);

            return null;
        }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
            if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
                return "El nombre original no puede estar vacío.";
            }
            if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
                return "El nuevo nombre no puede estar vacío.";
            }
            if (salon == null || salon.trim().isEmpty()) {
                return "El salón no puede estar vacío.";
            }
            if (turno == null || turno.trim().isEmpty()) {
                return "El turno no puede ser nulo.";
            }
            PrestamoLlave existente = new PrestamoLlaveRepository().buscarPorNombreSolicitante(nombreOriginal.trim());
            if (existente == null) {
                return "No se encontró el registro con nombre: " + nombreOriginal;
            }
        if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null)
            if (!nombreOriginal.trim().equalsIgnoreCase(nombreNuevo.trim())) {
                return "Ya existe un registro con el nuevo nombre.";
            }
            existente.setNombreSolicitante(nombreNuevo.trim());
            existente.setSalon(salon.trim());
            existente.setTurno(turno.trim());
            return null;
        }



    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "ERROR: El nombre del solicitante no puede estar vacío.";
        }
        PrestamoLlave existente = repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
        if (existente == null) {
            return "ERROR: No se encontró ningún registro con el nombre: " + nombreSolicitante;
        }
        repository.buscarPorNombreSolicitante(nombreSolicitante);
        return null;
    }

}
