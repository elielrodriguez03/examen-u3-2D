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
            return "el nombre es obligatorio";
        }
        if (salon == null || salon.trim().isEmpty()){
            return "Se debe ingresar un salon";
        }
        if (turno == null){
            return "se debe seleccionar un turno";
        }
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null){
            return "Ya hay registro de un alumno con ese nombre";
        }
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(nuevo);
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {

        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()){
            return "no seleccionó un registro para su actualización";
        }
        PrestamoLlave registroExiste = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registroExiste == null){
            return "el registro original no existe";
        }
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()){
            return "no se ha ingresado ningun nuevo nombre";
        }
        if (turno == null || turno.trim().isEmpty()){
            return "ingresar el turno es obligatorio";
        }
        if (salon == null || salon.trim().isEmpty()){
            return "el salón es obligatorio";
        }
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())){
            if (repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null){
                return "Ese nombre ya está en uso";
            }
        }

        registroExiste.setNombreSolicitante(nombreNuevo.trim());
        registroExiste.setSalon(salon);
        registroExiste.setTurno(turno.trim());
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        if (nombreSolicitante == null){
            return "El nombre no puede estar vacío";
        }
        if (buscarPorNombreSolicitante(nombreSolicitante) == null){
            return "el nombre no existe";
        }

        repository.eliminarPorNombreSolicitante(nombreSolicitante);
        return null;
    }
}
