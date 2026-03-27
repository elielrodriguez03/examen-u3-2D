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
        return "Completa la lógica de agregar en el service";
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
        return "Completa la lógica de actualizar en el service";
    }

    public String eliminar(String nombreSolicitante) {
        // TODO:
        // 1. Validar que nombreSolicitante no esté vacío.
        // 2. Buscar si existe el registro.
        // 3. Si no existe, regresar mensaje de error.
        // 4. Si existe, eliminarlo desde repository.
        // 5. Regresar null si se eliminó correctamente.
        return "Completa la lógica de eliminar en el service";
    }
}
