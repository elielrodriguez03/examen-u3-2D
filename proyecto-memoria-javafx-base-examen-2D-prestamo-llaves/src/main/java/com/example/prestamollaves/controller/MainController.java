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

    // Aquí se guarda el nombre original del registro encontrado o seleccionado.
    private String nombreOriginal;

    @FXML
    public void initialize() {
        cargarTurnos();
        actualizarLista();

        // También se puede cargar un registro seleccionándolo en el ListView.
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

        String resultado = service.agregar(nombre, salon, turno);

        if (resultado != null) {
            mostrarMensaje("Error", resultado, Alert.AlertType.ERROR);
            return;
        }
        actualizarLista();
        limpiar();
    }

    @FXML
    public void buscar() {
        // Método de ejemplo resuelto.
        PrestamoLlave registro = service.buscarPorNombreSolicitante(txtNombreSolicitante.getText());

        if (registro == null) {
            mostrarMensaje("Aviso", "Registro no encontrado", Alert.AlertType.WARNING);
            return;
        }

        txtNombreSolicitante.setText(registro.getNombreSolicitante());
        txtSalon.setText(registro.getSalon());
        cbTurno.setValue(registro.getTurno());

        // Este valor es clave para UPDATE.
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {

        if (nombreOriginal == null) {
            mostrarMensaje("Error", "Primero debes buscar o seleccionar un registro", Alert.AlertType.ERROR);
            return;
        }

        String nombreNuevo = txtNombreSolicitante.getText();
        String salon = txtSalon.getText();
        String turno = cbTurno.getValue();

        String resultado = service.actualizar(nombreOriginal, nombreNuevo, salon, turno);

        if (resultado != null) {
            mostrarMensaje("Error", resultado, Alert.AlertType.ERROR);
            return;
        }

        actualizarLista();
        limpiar();
    }

    @FXML
    public void eliminar() {

        String nombre = txtNombreSolicitante.getText();

        String resultado = service.eliminar(nombre);

        if (resultado != null) {
            mostrarMensaje("Error", resultado, Alert.AlertType.ERROR);
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

