package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;

import java.security.InvalidParameterException;
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
        validar(nombreSolicitante,salon,turno);
        // 5. Si todo está bien, crear un objeto PrestamoLlave y guardarlo en repository.
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(),salon.trim(),turno);
        repository.guardar(nuevo);
        // 6. Regress null cuando el registry se guarde correctamente.
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if(nombreOriginal == null || nombreOriginal.isBlank()){
            throw new InvalidParameterException();
        }
        if(nombreNuevo == null || nombreNuevo.isBlank()){
            throw new InvalidParameterException();
        }
        if(salon == null || salon.isBlank()){
            throw new InvalidParameterException();
        }
        if(turno == null || turno.isBlank()){
            throw new InvalidParameterException();
        }
        PrestamoLlave existente = buscarPorNombreSolicitante(nombreOriginal);
        if (existente == null){
            throw new InvalidParameterException();
        }
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())){
            if (buscarPorNombreSolicitante(nombreNuevo)!=null){
                throw new InvalidParameterException();
            }
        }
        existente.setNombreSolicitante(nombreNuevo.trim());
        existente.setSalon(salon.trim());
        existente.setTurno(turno.trim());
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        if(nombreSolicitante == null || nombreSolicitante.isBlank()){
            throw new InvalidParameterException();
        }
        if (buscarPorNombreSolicitante(nombreSolicitante)!=null){
            repository.eliminarPorNombreSolicitante(nombreSolicitante);
        }else {
            throw new RuntimeException();
        }
        return null;
    }
    public void validar(String nombreSolicitante, String salon, String turno){
        try {
        if(nombreSolicitante == null || nombreSolicitante.isBlank()){
            throw new InvalidParameterException();
        }
        // 2. Validar que salon no esté vacío.
        if(salon == null || salon.isBlank()){
            throw new InvalidParameterException();
        }
        // 3. Validar que turno no sea null.
        if(turno == null || turno.isBlank()){
            throw new InvalidParameterException();
        }
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        if (buscarPorNombreSolicitante(nombreSolicitante) != null){
            throw new InvalidParameterException();
        }
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        }
    }
}
