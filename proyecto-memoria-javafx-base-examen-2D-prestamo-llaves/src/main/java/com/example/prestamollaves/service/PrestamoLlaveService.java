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
        //1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre del solicitante es obligatorio";
        }
        // 2. Validar que salon no esté vacío.
        if (salon == null || salon.trim().isEmpty()) {
            return "El salOn es obligatorio";
        }
        // 3. Validar que turno no sea null.
        if (turno == null) {
            return "Debe seleccionar un turno";
        }
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "El nombre del alumno ya existe: " + nombreSolicitante;
        }
        // 5. Si todo está bien, crear un objeto PrestamoLlave y guardarlo en repository.
        // 6. Regresar null cuando el registro se guarde correctamente.
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);

        repository.guardar(nuevo);

        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "Debe buscar o seleccionar un registro primero";
        }
        // 2. Validar que nombreNuevo no esté vacío.
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        // 3. Validar que salon no esté vacío.
        if (salon == null || salon.trim().isEmpty()) {
            return "El salón es obligatorio";
        }
        // 4. Validar que turno no sea null.
        if (turno == null) {
            return "Debe seleccionar un turno";
        }
        // 5. Buscar el registro original usando nombreOriginal.
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        // 6. Si no existe, regresar mensaje de error.
        if (registro == null) {
            return "Registro no encontrado";
        }
        // 7. Si el nombre cambió, validar que el nuevo nombre no esté repetido.
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo) != null) {
                return "Ya existe un registro con ese nombre";
            }
        }
        // 8. Si todo está bien, actualizar los atributos del objeto encontrado.
        // 9. Regresar null si todo salió bien.
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setSalon(salon.trim());
        registro.setTurno(turno);

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // 1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Debe escribir un nombre";
        }
        // 2. Buscar si existe el registro.
        // 3. Si no existe, regresar mensaje de error.
        // 4. Si existe, eliminarlo desde repository.
        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante);
        if (!eliminado) {
            return "Registro no encontrado";
        }
        // 5. Regresar null si se eliminó correctamente.
        return null;
    }
}