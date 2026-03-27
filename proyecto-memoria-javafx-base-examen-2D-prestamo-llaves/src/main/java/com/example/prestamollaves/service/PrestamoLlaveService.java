package com.example.prestamollaves.service;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.repository.PrestamoLlaveRepository;
import java.util.List;

public class PrestamoLlaveService {
    private PrestamoLlaveRepository repository = new PrestamoLlaveRepository();

    public List<PrestamoLlave> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public PrestamoLlave buscar(String nombre) {
        return repository.buscarPorNombre(nombre);
    }

    public String agregar(String nombre, String salon, String turno) {
        if (nombre.isEmpty() || salon.isEmpty() || turno == null || turno.isEmpty()) {
            return "Error: Todos los campos son obligatorios.";
        }

        if (repository.buscarPorNombre(nombre) != null) {
            return "Error: Ya existe un préstamo a nombre de este solicitante.";
        }

        PrestamoLlave nuevoPrestamo = new PrestamoLlave(nombre, salon, turno);
        repository.agregar(nuevoPrestamo);
        return "Éxito: Préstamo registrado correctamente.";
    }

    public String actualizar(String nombreOriginal, String nuevoNombre, String salon, String turno) {
        if (nuevoNombre.isEmpty() || salon.isEmpty() || turno == null || turno.isEmpty()) {
            return "Error: Todos los campos son obligatorios.";
        }

        PrestamoLlave prestamoExistente = repository.buscarPorNombre(nombreOriginal);
        if (prestamoExistente == null) {
            return "Error: No se encontró el registro original.";
        }

        if (!nombreOriginal.equalsIgnoreCase(nuevoNombre)) {
            if (repository.buscarPorNombre(nuevoNombre) != null) {
                return "Error: El nuevo nombre ya está registrado en otro préstamo.";
            }
        }

        prestamoExistente.setNombreSolicitante(nuevoNombre);
        prestamoExistente.setSalon(salon);
        prestamoExistente.setTurno(turno);
        return "Éxito: Préstamo actualizado correctamente.";
    }

    public String eliminar(String nombre) {
        PrestamoLlave prestamo = repository.buscarPorNombre(nombre);
        if (prestamo != null) {
            repository.eliminar(prestamo);
            return "Éxito: Préstamo eliminado.";
        }
        return "Error: No se encontró el registro para eliminar.";
    }
}

