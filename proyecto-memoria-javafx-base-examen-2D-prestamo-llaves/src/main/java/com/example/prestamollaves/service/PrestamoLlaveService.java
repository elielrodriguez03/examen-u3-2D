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
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "El nombre del solicitante es obligatorio.";
        }
        // 2. Validar que salon no esté vacío.
        if (salon == null || salon.trim().isEmpty()) {
            return "El salón es obligatorio.";
        }
        // 3. Validar que turno no sea null.
        if (turno == null) {
            return "El turno es obligatorio.";
        }

        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        if (buscarPorNombreSolicitante(nombreSolicitante) != null) {
            return "Ya existe un préstamo a nombre de este solicitante.";
        }

        // 5. Si todo está bien, crear un objeto PrestamoLlave y guardarlo en repository.
        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(nuevo);

        // 6. Regresar null cuando el registro se guarde correctamente.

        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "El nombre original es necesario para la actualización.";
        }
        // 2. Validar que nombreNuevo no esté vacío.
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "El nuevo nombre no puede estar vacío.";
        }
        // 3. Validar que salon no esté vacío.
        if (salon == null || salon.trim().isEmpty()) {
            return "El salón no puede estar vacío.";
        }
        // 4. Validar que turno no sea null.
        if (turno == null) {
            return "El turno no puede ser nulo.";
        }
        // 5. Buscar el registro original usando nombreOriginal.
        PrestamoLlave prestamoExistente = buscarPorNombreSolicitante(nombreOriginal);

        // 6. Si no existe, regresar mensaje de error.
        if (prestamoExistente == null) {
            return "No se encontró el registro original.";
        }

        // 7. Si el nombre cambió, validar que el nuevo nombre no esté repetido.
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
            if (buscarPorNombreSolicitante(nombreNuevo) != null) {
                return "El nuevo nombre ya está registrado en otro préstamo.";
            }
        }
        // 8. Si todo está bien, actualizar los atributos del objeto encontrado.
        prestamoExistente.setNombreSolicitante(nombreNuevo.trim());
        prestamoExistente.setSalon(salon.trim());
        prestamoExistente.setTurno(turno);
        // 9. Regresar null si todo salió bien.
        return null;

    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Debe proporcionar un nombre para eliminar.";
        }
        // 2. Buscar si existe el registro.
        PrestamoLlave prestamoAEliminar = buscarPorNombreSolicitante(nombreSolicitante);


        // 3. Si no existe, regresar mensaje de error.
        if (prestamoAEliminar == null) {
            return "No se encontró ningún registro con ese nombre.";
        }

        // 4. Si existe, eliminarlo desde repository.
        repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());

        // 5. Regresar null si se eliminó correctamente.

        return null;
    }
}
