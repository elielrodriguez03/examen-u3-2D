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

    public String agregar(PrestamoLlave nuevo) {
        if (nuevo == null) {
            return "Datos inválidos";
        }
        if (nuevo.getNombreSolicitante() == null || nuevo.getNombreSolicitante().trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        if (nuevo.getSalon() == null || nuevo.getSalon().trim().isEmpty()) {
            return "El salón es obligatoria";
        }
        if (nuevo.getTurno() == null || nuevo.getTurno().trim().isEmpty()) {
            return "El turno es obligatorio";
        }
        PrestamoLlave existente = repository.buscarPorNombreSolicitante(nuevo.getNombreSolicitante());
        if (existente != null) {
            return "Ya existe un registro con ese nombre";
        }
        repository.guardar(nuevo);

        return null;
    }

    public String actualizar(String nombreOriginal, String nombreNuevo, String salon, String turno) {
        if (nombreOriginal == null || nombreOriginal.trim().isEmpty()) {
            return "Nombre original inválido";
        }
        if (nombreNuevo == null || nombreNuevo.trim().isEmpty()) {
            return "El nombre es obligatorio";
       }

       if (salon == null || salon.trim().isEmpty()) {
           return "El salón es obligatoria";
      }
        if (turno == null || turno.trim().isEmpty()) {
            return "El turno es obligatorio";
       }
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreOriginal);

       if (registro == null) {
            return "Registro no encontrado";
        }
        if (!nombreOriginal.equalsIgnoreCase(nombreNuevo)) {
            PrestamoLlave repetido = repository.buscarPorNombreSolicitante(nombreNuevo);
            if (repetido != null) {
                return "El nuevo nombre ya está en uso";
           }
        }
      registro.setNombreSolicitante(nombreNuevo);
        registro.setSalon(salon);
        registro.setTurno(turno);
        return null;
    }


//_---------------------------------------
public String eliminar(String nombreSolicitante) {
//
//        // 1. Validar
        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
            return "Nombre inválido";
        }

        // 2. Buscar
        PrestamoLlave registro = repository.buscarPorNombreSolicitante(nombreSolicitante);

        if (registro == null) {
            return "No existe el registro";
        }


    repository.eliminarPorNombreSolicitante(nombreSolicitante);

        return null; // éxito
    }
public String actualizar(String nombreOriginal, PrestamoLlave actualizado) {

        if (actualizado == null) {
            return "Datos inválidos";
        }

        return actualizar(
                nombreOriginal,
                actualizado.getNombreSolicitante(),
                actualizado.getSalon(),
                actualizado.getTurno()
        );
    }

    public String[] cargarBloques() {
        return turnos;
    }}