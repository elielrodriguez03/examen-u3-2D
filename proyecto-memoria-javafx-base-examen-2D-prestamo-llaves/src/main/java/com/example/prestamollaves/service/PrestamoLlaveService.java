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
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        if(nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre solicitado es obligatorio";
        }
        // 2. Validar que salon no esté vacío.
        if(salon == null || salon.trim().isEmpty()) {
            return "El salon es un campo obligatorio";
        }
        // 3. Validar que turno no sea null.
        if(turno == null) {
            return "Debes seleccionar un turno";
        }
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        if(repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null){
            return "El registro ya existe";
        }

        // 5. Si todo está bien, crear un objeto PrestamoLlave y guardarlo en repository.
        PrestamoLlave prestamoKey = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(prestamoKey);
        // 6. Regresar null cuando el registro se guarde correctamente.
        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if(nombreOriginal == null || nombreOriginal.trim().isEmpty()){
            return "Primero selecciona o busca un registro";
        }
        // 2. Validar que nombreNuevo no esté vacío.
        if(nombreNuevo == null || nombreNuevo.trim().isEmpty()){
            return "El nombre no puede estar vacio";
        }
        // 3. Validar que salon no esté vacío.
        if(salon == null || salon.trim().isEmpty()){
            return "El salon no puede estar vacio";
        }
        // 4. Validar que turno no sea null.
        if(turno == null){
            return "El turno no puede estar vacio";
        }
        // 5. Buscar el registro original usando nombreOriginal.
        PrestamoLlave nombreOrig = repository.buscarPorNombreSolicitante(nombreOriginal);
        if(nombreOrig == null){
            return "El nombre ya no existe";
        }
        // 6. Si no existe, regresar mensaje de error.
        // 7. Si el nombre cambió, validar que el nuevo nombre no esté repetido.
        if(!nombreOriginal.equalsIgnoreCase(nombreNuevo.trim())){
            if(repository.buscarPorNombreSolicitante(nombreNuevo.trim()) != null) return "El registro ya existe";
        }
        // 8. Si todo está bien, actualizar los atributos del objeto encontrado.
        nombreOrig.setNombreSolicitante(nombreNuevo);
        nombreOrig.setSalon(salon);
        nombreOrig.setTurno(turno);
        // 9. Regresar null si todo salió bien.
        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        if(nombreSolicitante == null || nombreSolicitante.trim().isEmpty()){
            return "Primero busca o selecciona un registro";
        }
        // 2. Buscar si existe el registro.
        boolean eliminar = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        return eliminar ? null : "El registro no existe";
        // 3. Si no existe, regresar mensaje de error.
        // 4. Si existe, eliminarlo desde repository.
        // 5. Regresar null si se eliminó correctamente.;
    }
}
