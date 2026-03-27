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
        // TODO:
        // 1. Leer txtNombreSolicitante, txtSalon y cbTurno.
        // 2. Mandar esos datos al service.
        // 3. Si el service regresa un mensaje, mostrar error.
        // 4. Si regresa null, refrescar la lista y limpiar.
        String nombre = txtNombreSolicitante.getText();
        String turno = cbTurno.getValue();
        String salon = txtSalon.getText();

        if (nombre.isEmpty() || salon.isEmpty() || turno == null) {
            mostrarMensaje("Error", "por favor completa los campos", Alert.AlertType.ERROR);
        return;
        }
        String mensajeError = service.agregar(nombre, turno, salon);
         if (mensajeError != null){
             mostrarMensaje("Error", mensajeError, Alert.AlertType.ERROR);
         }else {
             actualizarLista();
             limpiar();
             mostrarMensaje("Exito", "Registro exitoso", Alert.AlertType.INFORMATION);
         }

        txtNombreSolicitante.clear();
        txtSalon.clear();
        cbTurno.setValue(null);


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
        // TODO:
        // UPDATE reutiliza los mismos controles.
        //
        // Flujo esperado:
        // 1. Primero buscar por nombre o seleccionar desde el ListView.
        // 2. Eso debe cargar los datos en pantalla y guardar nombreOriginal.
        // 3. Luego el usuario modifica txtNombreSolicitante, txtSalon y cbTurno.
        // 4. Al presionar Actualizar, mandar al service:
        //      - nombreOriginal
        //      - txtNombreSolicitante.getText()
        //      - txtSalon.getText()
        //      - cbTurno.getValue()
        // 5. El service debe buscar el registro original usando nombreOriginal.
        // 6. Si lo encuentra, debe cambiar sus datos.
        // 7. Luego refrescar el ListView y limpiar los controles.
        //
        // Importante:
        // Si nombreOriginal es null, entonces no se ha buscado ni seleccionado nada.
        if (nombreOriginal == null){
mostrarMensaje("Aviso", "Selecciona uno para editar", Alert.AlertType.WARNING);
return;
        }
        String nuevonombre = txtNombreSolicitante.getText();
        String nuevoturno = cbTurno.getValue();
        String nuevosalon = txtSalon.getText();

        String msgError = service.actualizar(nombreOriginal, nuevonombre, nuevosalon, nuevoturno);

        if(msgError == null){
            actualizarLista();
            limpiar();
            mostrarMensaje("Exito", "Registro actualizado", Alert.AlertType.INFORMATION);
        }else {
            mostrarMensaje("Error", "no se pudo actualizar" , Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void eliminar() {
        // TODO:
        // DELETE sí borra el objeto de la lista.
        //
        // Flujo esperado:
        // 1. Tomar el nombre desde txtNombreSolicitante.
        // 2. Mandarlo al service.
        // 3. El service debe buscarlo y eliminarlo de la lista.
        // 4. Refrescar el ListView.
        // 5. Limpiar controles.
        //
        // También se puede seleccionar un elemento del ListView
        // y luego presionar Eliminar.
        String nombredelete = txtNombreSolicitante.getText();
        if (nombredelete.isEmpty()) {
            mostrarMensaje("Aviso", "seleccione el nombre a eliminar", Alert.AlertType.WARNING);
            return;
        }
        String msgError = service.eliminar(nombredelete);

        if (msgError == null){
            actualizarLista();
            limpiar();
            mostrarMensaje("Exito", "Registro eliminado" , Alert.AlertType.INFORMATION);
        }else {
            mostrarMensaje("Error", "no se pudo eliminar", Alert.AlertType.ERROR);
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
