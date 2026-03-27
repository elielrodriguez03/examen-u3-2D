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
            return "nombre solicitante requerido";
        }

        if (salon == null || salon.trim().isEmpty()) {
            return "salón requerido";
        }

        if (turno == null || turno.trim().isEmpty()) {
            return "turno requerido";
        }

        String nombreLimpio = nombreSolicitante.trim();
        String salonLimpio = salon.trim();
        String turnoLimpio = turno.trim();

        if (repository.buscarPorNombreSolicitante(nombreLimpio) != null) {
            return "ya existe un registro con ese nombre";
        }

        PrestamoLlave registro = new PrestamoLlave(nombreLimpio, salonLimpio, turnoLimpio);
        repository.guardar(registro);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "nombre original requerido";
        }

        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "nombre nuevo requerido";
        }

        if (salon == null || salon.trim().isEmpty()) {
            return "salón requerido";
        }

        if (turno == null || turno.trim().isEmpty()) {
            return "turno requerido";
        }

        String nombreOriginalLimpio = nombreOriginal.trim();
        String nombreNuevoLimpio = nombreNuevo.trim();
        String salonLimpio = salon.trim();
        String turnoLimpio = turno.trim();

        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginalLimpio);
        if (registro == null) {
            return "registro original no encontrado";
        }

        if (!nombreOriginalLimpio.equalsIgnoreCase(nombreNuevoLimpio)) {
            PrestamoLlave duplicado = repository.buscarPorNombreSolicitante(nombreNuevoLimpio);
            if (duplicado != null) {
                return "ya existe otro registro con el nuevo nombre";
            }
        }

        registro.setNombreSolicitante(nombreNuevoLimpio);
        registro.setSalon(salonLimpio);
        registro.setTurno(turnoLimpio);

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "nombre solicitante requerido";
        }

        String nombreLimpio = nombreSolicitante.trim();
        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreLimpio);
        if (!eliminado) {
            return "registro no encontrado";
        }

        return null;
    }
}
