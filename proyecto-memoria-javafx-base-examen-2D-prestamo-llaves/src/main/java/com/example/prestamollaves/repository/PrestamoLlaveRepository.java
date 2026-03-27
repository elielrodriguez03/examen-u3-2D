package com.example.prestamollaves.repository;

import com.example.prestamollaves.model.PrestamoLlave;
import java.util.ArrayList;
import java.util.List;

public class PrestamoLlaveRepository {
    private List<PrestamoLlave> prestamos = new ArrayList<>();

    public void agregar(PrestamoLlave prestamo) {
        prestamos.add(prestamo);
    }

    public List<PrestamoLlave> obtenerTodos(){
        return prestamos;
    }

    public PrestamoLlave buscarPorNombre(String nombre) {
        for (PrestamoLlave p : prestamos) {
            if (p.getNombreSolicitante().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    public void eliminar(PrestamoLlave prestamo) {
        prestamos.remove(prestamo);
    }
}
