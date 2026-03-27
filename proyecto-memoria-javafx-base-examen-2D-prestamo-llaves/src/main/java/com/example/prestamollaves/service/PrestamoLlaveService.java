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
        if (nombreSolicitante == null || nombreSolicitante.isEmpty()) {
            return "el nombre es obligatorio";
        }
        if (salon == null || salon.isEmpty()) {
            return "el salon es obligatorio";
        }
        if (turno == null) {
            return "se debe seleccionar un turno";
        }
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "ya esta registrado un alumno con ese nombre";
        }
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante, salon, turno);
        repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.isEmpty()) {
            return "no se a podido actualizar";
        }
        PrestamoLlave archivoexixtente = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (archivoexixtente == null) {
            return "el registro no existe";
        }
        if (nombreNuevo == null || nombreNuevo.isEmpty()) {
            return "el nuevo nombre no puede estar vacio";
        }
        if (repository.buscarPorNombreSolicitante(nombreOriginal.trim()) != null) {
            return "ya hay un alumno registrado con ese nombre";
        }
        if (salon == null || salon.isEmpty()) {
            return "el salon es obligatorio";
        }
        if (turno == null) {
            return "se debe seleccionar un turno";
        }
        if (nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "el nombre ya esta usado";
            }
            archivoexixtente.setNombreSolicitante(nombreNuevo.trim());
            archivoexixtente.setSalon(salon.trim());
            archivoexixtente.setTurno(turno.trim());
            return null;
        }
        PrestamoLlave nuevo = new PrestamoLlave(nombreOriginal, salon, turno);
        repository.guardar(nuevo);
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null) {
            return "el nombre no puede estar vacio";
        }
        if (buscarPorNombreSolicitante(nombreSolicitante) == null) {
            return "el nombre no existe";
        }
        repository.eliminarPorNombreSolicitante(nombreSolicitante);
        return  null;
    }
}
