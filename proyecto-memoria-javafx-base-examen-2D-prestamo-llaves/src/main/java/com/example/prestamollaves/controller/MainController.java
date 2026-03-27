package com.example.prestamollaves.controller;

import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.service.PrestamoLlaveService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        if (txtNombreSolicitante.getText().isEmpty() ||  txtSalon.getText().isEmpty() || cbTurno.getValue() == null || cbTurno.getValue().isEmpty())
                 {
            mostrarMensaje("Aviso", "Completa todos los campos antes de agregar", Alert.AlertType.WARNING);
            return;
        }
        service.agregar(   txtNombreSolicitante.getText(),
                txtSalon.getText(),
                cbTurno.getValue());
        actualizarLista();
        limpiar();
        mostrarMensaje("Éxito", "Registro agregado correctamente", Alert.AlertType.INFORMATION);
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
        nombreOriginal = registro.getNombreSolicitante();
    }

    @FXML
    public void actualizar() {



        mostrarMensaje("Actualizar:", "Se ha actualizado la informacion con exito", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void eliminar() {
            MultipleSelectionModel<String> seleccionado = lvRegistros.getSelectionModel();
            if (seleccionado != null) {
                limpiar();
                actualizarLista();
                mostrarMensaje("Eliminar", "Registro eliminado con éxito", Alert.AlertType.INFORMATION);

            } else {
                mostrarMensaje("Eliminar", "Por favor, selecciona un registro de la lista para eliminar", Alert.AlertType.WARNING);
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
