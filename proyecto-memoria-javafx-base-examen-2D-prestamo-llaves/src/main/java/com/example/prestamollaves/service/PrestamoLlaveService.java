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
        // 2. Validar que salon no esté vacío.
        // 3. Validar que turno no sea null.
        // 4. Validar que no exista otro registro con el mismo nombreSolicitante.
        // 5. Si todo está bien, crear un objeto PrestamoLlave y guardarlo en repository.
        // 6. Regresar null cuando el registro se guarde correctamente.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "El nombre de nuestro solicitante no puede estar vacío";
        if (salon == null || salon.trim().isEmpty()) return "El nombre de nuestro salon no puede estar vacío";
        if (turno == null) return "Debe seleccionar obligatoriamente un turno";

        if (repository.buscarPorNombreSolicitante(nombreSolicitante.trim()) != null) {
            return "Tienes un error ya que el alumno ya tiene un turno asignado";
        }

        PrestamoLlave nuevo = new PrestamoLlave(nombreSolicitante.trim(), salon.trim(), turno);
        repository.guardar(nuevo);

        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        // TODO:
        // 1. Validar que nombreOriginal no sea null ni vacío.
        // 2. Validar que nombreNuevo no esté vacío.
        // 3. Validar que salon no esté vacío.
        // 4. Validar que turno no sea null.
        // 5. Buscar el registro original usando nombreOriginal.
        // 6. Si no existe, regresar mensaje de error.
        // 7. Si el nombre cambió, validar que el nuevo nombre no esté repetido.
        // 8. Si todo está bien, actualizar los atributos del objeto encontrado.
        // 9. Regresar null si todo salió bien.
        if (nombreOriginal == null || nombreOriginal.isEmpty()) return "Tienes un error de referencia original";

        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);
        if (registro == null) return "El registro original que pusiste no existe";

        if (nombreNuevo.isEmpty() || salon.isEmpty() || turno == null) return "Tienes los campos vacíos";

        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
            if (repository.buscarPorNombreSolicitante(nombreNuevo) != null) {
                return "El nuevo nombre que has puesto ya está en uso por otro alumno";
            }
        }
        registro.setNombreSolicitante(nombreNuevo.trim());
        registro.setSalon(salon.trim());
        registro.setTurno(turno);

        return null;
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        // 2. Buscar si existe el registro.
        // 3. Si no existe, regresar mensaje de error.
        // 4. Si existe, eliminarlo desde repository.
        // 5. Regresar null si se eliminó correctamente.
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) return "Nombre inválido";

        boolean eliminado = repository.eliminarPorNombreSolicitante(nombreSolicitante.trim());
        if (!eliminado) return "No se encontró ese registro para poder eliminarlo";

        return null;
    }
}
