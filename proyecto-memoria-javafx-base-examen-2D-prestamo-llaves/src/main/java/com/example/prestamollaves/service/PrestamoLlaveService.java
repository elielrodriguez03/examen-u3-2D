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
        if (salon == null || salon.trim().isEmpty()) {
        return "El salón es obligatorio";
    }

    if (turno == null) {
        return "Debe seleccionar un turno";
    }

    // Validar duplicado
    PrestamoLlave existente = repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
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
        return "No hay registro seleccionado";
    }

    if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
        return "El nombre nuevo es obligatorio";
    }

    if (salon == null || salon.trim().isEmpty()) {
        return "El salón es obligatorio";
    }

    if (turno == null) {
        return "Debe seleccionar un turno";
    }

    PrestamoLlave existente = repository.buscarPorNombreSolicitante(nombreOriginal.trim());

    if (existente == null) {
        return "El registro original no existe";
    }

    if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())) {
        PrestamoLlave duplicado = repository.buscarPorNombreSolicitante(nombreNuevo.trim());
        if (duplicado != null) {
            return "Ya existe otro registro con ese nombre";
        }
    }

    existente.setNombreSolicitante(nombreNuevo.trim());
    existente.setSalon(salon.trim());
    existente.setTurno(turno);

    return null;
}
    

  public String eliminar(String nombreSolicitante) {

    if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
        return "El nombre es obligatorio para eliminar";
    }

    boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());

    if (!eliminado) {
        return "No se encontró el registro";
    }

    return null;
}
}
