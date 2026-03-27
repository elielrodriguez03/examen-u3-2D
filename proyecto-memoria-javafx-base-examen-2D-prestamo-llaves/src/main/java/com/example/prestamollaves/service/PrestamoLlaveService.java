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
            return "nombre del solicitante es obligatorio";
        }

        if (salon == null || salon.trim().isEmpty()) {
            return "salón es obligatorio";
        }

        if (turno == null || turno.trim().isEmpty()) {
            return "turno es obligatorio";
        }

        String nombreLimpio = nombreSolicitante.trim();
        if (repository.buscarPorNombreSolicitante(nombreLimpio) != null) {
            return "ya existe un registro con ese nombre";
        }

        PrestamoLlave registro = new PrestamoLlave(nombreLimpio, salon.trim(), turno);
        repository.guardar(registro);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "nombre original no puede ser vacío";
        }

        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "nombre nuevo es obligatorio";
        }

        if (salon == null || salon.trim().isEmpty()) {
            return "salón es obligatorio";
        }

        if (turno == null || turno.trim().isEmpty()) {
            return "turno es obligatorio";
        }

        PrestamoLlave registroExistente = repository.buscarPorNombreSolicitante(nombreOriginal.trim());
        if (registroExistente == null) {
            return "registro original no existe";
        }

        String nombreNuevoLimpio = nombreNuevo.trim();
        if (!nombreOriginal.trim().equalsIgnoreCase(nombreNuevoLimpio)) {
            PrestamoLlave otro = repository.buscarPorNombreSolicitante(nombreNuevoLimpio);
            if (otro != null) {
                return "otro registro ya tiene ese nombre";
            }
        }

        registroExistente.setNombreSolicitante(nombreNuevoLimpio);
        registroExistente.setSalon(salon.trim());
        registroExistente.setTurno(turno.trim());

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "nombre del solicitante es obligatorio";
        }

        String nombreLimpio = nombreSolicitante.trim();
        PrestamoLlave registroExistente = repository.buscarPorNombreSolicitante(nombreLimpio);
        if (registroExistente == null) {
            return "registro no encontrado para eliminar";
        }

        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreLimpio);
        if (!eliminado) {
            return "no se pudo eliminar el registro";
        }

        return null;
    }
}
