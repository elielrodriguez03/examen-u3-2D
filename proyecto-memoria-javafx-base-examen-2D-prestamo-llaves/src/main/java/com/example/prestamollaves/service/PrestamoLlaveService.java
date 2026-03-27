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
            if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()){
                return "El nombre del solicitante es obligatorio.";
            }
            if (salon == null || salon.trim().isEmpty()){
                return "El salon es obligatorio.";
            }
            if (turno == null) {
                return "Debe seleccionar un turno.";
            }
            if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
                return "Ya existe un alumno registrado con ese nombre.";
            }
            PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
            repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()){
            return "No se ha seleccionado un registro para actualizar";
        }
        PrestamoLlave registroExistente = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registroExistente == null){
            return "El registro original no existe";
        }
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "El nuevo nombre no puede estar vacío";
        }
        if (turno == null || turno.trim().isEmpty()){
            return "El turno no puede estar vacío";
        }
        if (salon == null) {
            return "El salon no puede ser nulo";
        }
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) {
                return "El nuevo nombre ya está siendo usado por otro registro";
            }
        }
        registroExistente.setNombreSolicitante(nombreNuevo.trim());
        registroExistente.setSalon(salon.trim());
        registroExistente.setTurno(turno);
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null){
            return "El nombre no puede ser vacio";
        }
        if (buscarPorNombreSolicitante(nombreSolicitante) == null){
            return "El nombre no existe";
        }
        repository.eliminarPorNombreSolicitante(nombreSolicitante);
        return null;
    }
}
