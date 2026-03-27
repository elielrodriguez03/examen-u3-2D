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
        cbTurno.getItems().addAll(service.obtenerTurnos());
    }

    @FXML
    public void agregar() {
        String nombre = txtNombreSolicitante.getText();
        String salon = txtSalon.getText();
        String turno = cbTurno.getValue();

        String error = service.agregar(nombre, salon, turno);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.WARNING);
            return;
        }

        actualizarLista();
        limpiar();
    }

    @FXML
    public void buscar() {
        PrestamoLlave registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.ERROR);
            return;
        }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtSalon.setText(registro.getSalon());
        cbTurno.setValue(registro.getTurno());

        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        String nuevoNombre = txtNombreSolicitante.getText();
        String salon = txtSalon.getText();
        String turno = cbTurno.getValue();

        String error = service.actualizar(nombreOriginal, nuevoNombre, salon, turno);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
    }

    @FXML
    public void eliminar() {
        String nombre = (nombreOriginal != null) ? nombreOriginal : txtNombreSolicitante.getText();

        String error = service.eliminar(nombre);

        if (error != null) {
            mostrarMensaje("Error", error, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
    }

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

        for (PrestamoLlave p : registros) {
            lvRegistros.getItems().add(p.toString());
        }
    }

    private void cargarSeleccion(String textoSeleccionado) {
        List<PrestamoLlave> registros = service.obtenerTodos();

        for (PrestamoLlave actual : registros) {
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
