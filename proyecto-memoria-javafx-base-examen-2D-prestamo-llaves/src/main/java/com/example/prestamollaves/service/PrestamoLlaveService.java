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
        if (nombreSolicitante==null || nombreSolicitante.trim().isEmpty()) {
            return null;
        }
        return repository.buscarPorNombreSolicitante(nombreSolicitante.trim());
    }


    public String agregar(String nombreSolicitante, String salon, String turno) {
        if (nombreSolicitante==null || nombreSolicitante.trim().isEmpty()) return "el nombre no puede estar vacio";
        if (salon == null || salon.trim().isEmpty()) return "el salon no puede estar vacio";
        if (turno == null) return "debes seleccionar un turno";

        if(repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "error";
        }

        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(nuevo);

        return null;
    }


    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) { // TODO:

        if (nombreOriginal==null || nombreOriginal.isEmpty()) return "error ";
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro==null) return "el registro original no existe";
        if (nombreNuevo.isEmpty() || salon.isEmpty() || turno == null) return "los campos estan vacios";
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo) != null) {
                return "ya hay un nombre igual";
            }
        }
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setSalon(salon.trim());
        registro.setTurno(turno);

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "nombre invalido";

        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) return "no se encontro el registro para eliminar";

        return null;
    }

}
