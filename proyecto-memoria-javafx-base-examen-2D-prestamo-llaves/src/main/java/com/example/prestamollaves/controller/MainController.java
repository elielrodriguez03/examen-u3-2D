package com.example.prestamollaves.controller;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.service.PrestamoLlaveService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MainController {

    @FXML
    private TextField txtNombreSolicitante;

    @FXML
    private TextField txtSalon;

    @FXML
    private ComboBox<String> cbTurno;

    @FXML
    private ListView<String> lvRegistros;

    private final PrestamoLlaveService service = new PrestamoLlaveService();

    // Guarda el nombre original para UPDATE
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTurnos();
        actualizarLista();

        lvRegistros.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarSeleccion(newVal);
            }
        });
    }

    private void cargarTurnos() {
        String[] turnos = service.obtenerTurnos();
        for (int i = 0; i < turnos.length; i++) {
            cbTurno.getItems().add(turnos[i]);
        }
    }

    //  AGREGAR
    @FXML
    public void agregar() {

        String nombre = txtNombreSolicitante.getText().trim();
        String salon = txtSalon.getText().trim();
        String turno = cbTurno.getValue();

        if (nombre.isEmpty() || salon.isEmpty() || turno == null) {
            mostrarMensaje("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        PrestamoLlave nuevo = new PrestamoLlave(nombre, salon, turno);

        boolean agregado = service.agregar(nuevo);

        if (!agregado) {
            mostrarMensaje("Error", "Ya existe un registro con ese nombre", Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
    }

    //  BUSCAR (ya venía hecho)
    @FXML
    public void buscar() {
        PrestamoLlave registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtSalon.setText(registro.getSalon());
        cbTurno.setValue(registro.getTurno());

        nombreOriginal = registro.getNombreSolicitante();
    }

    //  ACTUALIZAR
    @FXML
    public void actualizar() {

        if (nombreOriginal == null) {
            mostrarMensaje("Error", "Primero debes buscar o seleccionar un registro", Alert.AlertType.ERROR);
            return;
        }

        String nombre = txtNombreSolicitante.getText().trim();
        String salon = txtSalon.getText().trim();
        String turno = cbTurno.getValue();

        if (nombre.isEmpty() || salon.isEmpty() || turno == null) {
            mostrarMensaje("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        PrestamoLlave actualizado = new PrestamoLlave(nombre, salon, turno);

        boolean resultado = service.actualizar(nombreOriginal, actualizado);

        if (!resultado) {
            mostrarMensaje("Error", "No se pudo actualizar (nombre repetido o no existe)", Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
    }

    //  ELIMINAR
    @FXML
    public void eliminar() {

        String nombre = txtNombreSolicitante.getText().trim();

        if (nombre.isEmpty()) {
            mostrarMensaje("Error", "Ingresa un nombre para eliminar", Alert.AlertType.ERROR);
            return;
        }

        boolean eliminado = service.eliminar(nombre);

        if (!eliminado) {
            mostrarMensaje("Error", "No se encontró el registro", Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
    }

    //  LIMPIAR
    @FXML
    public void limpiar() {
        txtNombreSolicitante.clear();
        txtSalon.clear();
        cbTurno.setValue(null);
        lvRegistros.getSelectionModel().clearSelection();
        nombreOriginal = null;
    }

    private void actualizarLista() {
        lvRegistros.getItems().clear();
        List<PrestamoLlave> registros = service.obtenerTodos();

        for (int i = 0; i < registros.size(); i++) {
            lvRegistros.getItems().add(registros.get(i).toString());
        }
    }

    private void cargarSeleccion(String textoSeleccionado) {
        List<PrestamoLlave> registros = service.obtenerTodos();

        for (int i = 0; i < registros.size(); i++) {
            PrestamoLlave actual = registros.get(i);

            if (actual.toString().equals(textoSeleccionado)) {
                txtNombreSolicitante.setText(actual.getNombreSolicitante());
                txtSalon.setText(actual.getSalon());
                cbTurno.setValue(actual.getTurno());
                nombreOriginal = actual.getNombreSolicitante();
                break;
            }
        }
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}