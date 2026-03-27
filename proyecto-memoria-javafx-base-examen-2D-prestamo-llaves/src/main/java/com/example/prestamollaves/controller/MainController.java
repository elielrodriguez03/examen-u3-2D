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

    // Aqui se guarda el nombre original del registro encontrado o seleccionado
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTurnos();
        actualizarLista();

        // Tambien se puede cargar un registro seleccionandolo en el ListView
        lvRegistros.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarSeleccion(newValue);
            }
        });
    }

    private void cargarTurnos() {
        String[] turnos = service.obtenerTurnos();
        for (int i = 0; i < turnos.length; i++) {
            cbTurno.getItems().add(turnos[i]);
        }
    }

    @FXML
    public void agregar() {
        String nombre = txtNombreSolicitante.getText();
        String salon = txtSalon.getText();
        String turno = cbTurno.getValue();

        String error = service.agregar(nombre, salon, turno);

        if (error != null) {
            mostrarMensaje("Error de validacion", error, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
            mostrarMensaje("Exito", "Registro agregado correctamente.", Alert.AlertType.INFORMATION);
        }
    }

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

        // Este valor es clave para UPDATE
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {
        if (nombreOriginal == null) {
            mostrarMensaje("Aviso", "Primero debes buscar o seleccionar un registro para poder actualizarlo.", Alert.AlertType.WARNING);
            return;
        }

        String nombreNuevo = txtNombreSolicitante.getText();
        String salon = txtSalon.getText();
        String turno = cbTurno.getValue();

        String error = service.actualizar(nombreOriginal, nombreNuevo, salon, turno);

        if (error != null) {
            mostrarMensaje("Error al actualizar", error, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
            mostrarMensaje("Exito", "Registro actualizado correctamente.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void eliminar() {
        String nombre = txtNombreSolicitante.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarMensaje("Aviso", "Escribe o selecciona el nombre del solicitante que deseas eliminar.", Alert.AlertType.WARNING);
            return;
        }

        String error = service.eliminar(nombre);

        if (error != null) {
            mostrarMensaje("Error al eliminar", error, Alert.AlertType.ERROR);
        } else {
            actualizarLista();
            limpiar();
            mostrarMensaje("Éxito", "Registro eliminado correctamente.", Alert.AlertType.INFORMATION);
        }
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