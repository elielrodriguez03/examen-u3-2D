package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;
import javafx.scene.control.Alert;

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
        // TODO:
            if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "El nombre no puede estar vacío";
            if (salon == null || salon.trim().isEmpty()) return "El libro no puede estar vacío";
            if (turno == null) return "Debe seleccionar un turno";

            if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
                return "Error: El alumno ya tiene un turno asignado";
            }

        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
            repository.guardar(nuevo);

            return null;
        }


    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        // TODO:
            if (nombreOriginal == null || nombreOriginal.isEmpty()) return "Error de referencia original";

            PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
            if (registro == null) return "El registro original no existe";

            if (nombreNuevo.isEmpty() || salon.isEmpty() || turno == null) return "Campos vacíos";

            if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
                if (repository.buscarPorNombreSolicitante(nombreNuevo) != null) {
                    return "El nuevo nombre ya está en uso por otro alumno";
                }
            }
            registro.setNombreSolicitante(nombreNuevo.trim());
            registro.setSalon(salon.trim());
            registro.setTurno(turno);

            return null;
        }


        public String eliminar(String nombreSolicitante) {
        // TODO:
                if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Nombre inválido";

                boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
                if (!eliminado) return "No se encontró el registro para eliminar";

                return null;
            }

}
