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
            return "El nombre de la persona es obligatorio!!";
        }
        if (salon == null || salon.trim().isEmpty()) {
            return "El salón de la persona es obligatorio!!";
        }

        if (turno == null) {
            return "Debes seleccionar un turno";
        }

        PrestamoLlave existente = repository.buscarPorNombreSolicitante(nombreSolicitante);
        if (existente != null) {
            return "Ya existe un registro con ese nombre";
        }

        PrestamoLlave nuevo = new PrestamoLlave(
                nombreSolicitante.trim(),
                salon.trim(),
                turno
        );

        repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {

        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "Primero debes buscar o seleccionar un registro";
        }

        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }

        if (salon == null || salon.trim().isEmpty()) {
            return "El salón es obligatorio";
        }

        if (turno == null) {
            return "Debes seleccionar un turno";
        }

        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) {
            return "El registro original no existe";
        }

        // Validar duplicado si cambia el nombre
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
            PrestamoLlave existente = repository.buscarPorNombreSolicitante(nombreNuevo);
            if (existente != null) {
                return "Ya existe otro registro con ese nombre";
            }
        }

        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setSalon(salon.trim());
        registro.setTurno(turno);

        return null;
    }

    public String eliminar(String nombreSolicitante) {

        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Debes escribir un nombre";
        }

        PrestamoLlave existente = repository.buscarPorNombreSolicitante(nombreSolicitante);
        if (existente == null) {
            return "El registro no existe";
        }

        repository.eliminarPorNombreSolicitante(nombreSolicitante);

        return null;
    }
}