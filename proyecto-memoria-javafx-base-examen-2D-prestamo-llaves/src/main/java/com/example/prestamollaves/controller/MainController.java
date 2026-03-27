package com.example.prestamollaves.controller;


import com.example.prestamollaves.model.PrestamoLlave;
import com.example.prestamollaves.service.PrestamoLlaveService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class MainController {

    @FXML private TextField txtNombreSolicitante;
    @FXML private TextField txtSalon;
    @FXML private ComboBox<String> cbTurno;

    // ATENCIÓN AQUÍ: Revisa tu archivo FXML para confirmar que el fx:id coincida con "listViewPrestamos"
    @FXML private ListView<PrestamoLlave> listViewPrestamos;

    private PrestamoLlaveService service = new PrestamoLlaveService();

    // Variable crucial para el UPDATE
    private String nombreOriginal = null;

    @FXML
    public void initialize() {
        cbTurno.getItems().addAll("Matutino", "Vespertino");
    }

    @FXML
    void agregar() {
        String nombre = txtNombreSolicitante.getText().trim();
        String salon = txtSalon.getText().trim();
        String turno = cbTurno.getValue();

        String resultado = service.agregar(nombre, salon, turno);
        mostrarMensaje(resultado);

        if (resultado.startsWith("Éxito")) {
            actualizarLista();
            limpiar();
        }
    }

    @FXML
    void buscar() {
        String nombreBuscado = txtNombreSolicitante.getText().trim();
        PrestamoLlave encontrado = service.buscar(nombreBuscado);

        if (encontrado != null) {
            txtNombreSolicitante.setText(encontrado.getNombreSolicitante());
            txtSalon.setText(encontrado.getSalon());
            cbTurno.setValue(encontrado.getTurno());

            // Guardamos el nombre original para el Update
            nombreOriginal = encontrado.getNombreSolicitante();
        } else {
            mostrarMensaje("Error: Solicitante no encontrado.");
            limpiar();
        }
    }

    @FXML
    void actualizar() {
        if (nombreOriginal == null) {
            mostrarMensaje("Error: Primero busca o selecciona un registro para actualizar.");
            return;
        }

        String nuevoNombre = txtNombreSolicitante.getText().trim();
        String salon = txtSalon.getText().trim();
        String turno = cbTurno.getValue();

        String resultado = service.actualizar(nombreOriginal, nuevoNombre, salon, turno);
        mostrarMensaje(resultado);

        if (resultado.startsWith("Éxito")) {
            actualizarLista();
            limpiar();
        }
    }

    @FXML
    void eliminar() {
        String nombre = txtNombreSolicitante.getText().trim();
        String resultado = service.eliminar(nombre);

        mostrarMensaje(resultado);

        if (resultado.startsWith("Éxito")) {
            actualizarLista();
            limpiar();
        }
    }

    @FXML
    void limpiar() {
        txtNombreSolicitante.clear();
        txtSalon.clear();
        cbTurno.setValue(null);
        // Soltamos el registro para evitar modificaciones accidentales
        nombreOriginal = null;
    }

    private void actualizarLista() {
        listViewPrestamos.getItems().clear();
        listViewPrestamos.getItems().addAll(service.obtenerTodos());
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(mensaje.startsWith("Éxito") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(mensaje.startsWith("Éxito") ? "Operación Exitosa" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}



