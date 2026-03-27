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
        //poner service para error
        //___________________________________
        String nombre = txtNombreSolicitante.getText();
        String salon = txtSalon.getText();
        String turno = cbTurno.getValue();

        if (nombre == null || nombre.isEmpty() ||
                salon == null || salon.isEmpty() ||
                turno == null || turno.isEmpty()) {
            mostrarMensaje("Pendiente", "Completa la lógica de Agregar", Alert.AlertType.INFORMATION);
        }
        PrestamoLlave nuevo = new PrestamoLlave(nombre, turno, salon);
        service.agregar(nuevo);
        actualizarLista();
        limpiar();
    }

    @FXML
    public void buscar() {
        String nombre = txtNombreSolicitante.getText();
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre para buscar.", Alert.AlertType.INFORMATION);
            return;
        }
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
        if (nombreOriginal == null) {
            mostrarMensaje("Error", "Primero busca o selecciona un registro para actualizar", Alert.AlertType.INFORMATION);
            return;
        }

        String nuevoNombre = txtNombreSolicitante.getText();
        String nuevoSalon = txtSalon.getText();
        String nuevoTurno = cbTurno.getValue();

        PrestamoLlave actualizar = new PrestamoLlave(nuevoNombre, nuevoSalon, nuevoTurno);

        if (Boolean.parseBoolean(service.actualizar(nombreOriginal, actualizar))) {
            actualizarLista();
            limpiar();
            nombreOriginal = null;
        }
        mostrarMensaje("Pendiente", "Completa la lógica de Actualizar", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void eliminar() {
        String nombre = txtNombreSolicitante.getText();
        if (Boolean.parseBoolean(service.eliminar(nombre))) {
            actualizarLista();
            limpiar();
        }
        else {
            mostrarMensaje("Error", "No existe el registro a eliminar.", Alert.AlertType.INFORMATION);
 }

}
//
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


//SERVICE
//
//package com.example.prestamosextensiones.service;
//
//import com.example.prestamosextensiones.model.PrestamoExtension;
//import com.example.prestamosextensiones.repository.PrestamoExtensionRepository;
//
//import java.util.List;
//
//public class PrestamoExtensionService {
//
//    private final PrestamoExtensionRepository repository = new PrestamoExtensionRepository();
//
//    private final String[] bloques = {"Bloque A", "Bloque B", "Bloque C", "Laboratorio"};
//
//    public String[] obtenerBloques() {
//        return bloques;
//    }
//
//    public List<PrestamoExtension> obtenerTodos() {
//        return repository.findAll();
//    }
//
//    public PrestamoExtension buscarPorNombreSolicitante(String nombreSolicitante) {
//        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
//            return null;
//        }
//
//        return repository.findByNombreSolicitante(nombreSolicitante.trim());
//    }
//
//    public String agregar(PrestamoExtension nuevo) {
//
//        // 1. Validaciones
//        if (nuevo == null) {
//            return "Datos inválidos";
//        }
//
//        if (nuevo.getNombreSolicitante() == null || nuevo.getNombreSolicitante().trim().isEmpty()) {
//            return "El nombre es obligatorio";
//        }
//
//        if (nuevo.getArea() == null || nuevo.getArea().trim().isEmpty()) {
//            return "El área es obligatoria";
//        }
//
//        if (nuevo.getBloque() == null || nuevo.getBloque().trim().isEmpty()) {
//            return "El bloque es obligatorio";
//        }
//
//        // 2. Validar duplicado
//        PrestamoExtension existente = repository.findByNombreSolicitante(nuevo.getNombreSolicitante());
//        if (existente != null) {
//            return "Ya existe un registro con ese nombre";
//        }
//
//        // 3. Guardar
//        repository.save(nuevo);
//
//        return null; // éxito
//    }
//
//
//
//    public String eliminar(String nombreSolicitante) {
//
//        // 1. Validar
//        if (nombreSolicitante == null || nombreSolicitante.trim().isEmpty()) {
//            return "Nombre inválido";
//        }
//
//        // 2. Buscar
//        PrestamoExtension registro = repository.findByNombreSolicitante(nombreSolicitante);
//
//        if (registro == null) {
//            return "No existe el registro";
//        }
//
//        // 3. Eliminar
//        repository.deleteByNombreSolicitante(nombreSolicitante);
//
//        return null; // éxito
//    }
//
//    // 🔥 ESTE ES EL QUE USA TU CONTROLLER
//    public String actualizar(String nombreOriginal, PrestamoExtension actualizado) {
//
//        if (actualizado == null) {
//            return "Datos inválidos";
//        }
//
//        return actualizar(
//                nombreOriginal,
//                actualizado.getNombreSolicitante(),
//                actualizado.getArea(),
//                actualizado.getBloque()
//        );
//    }
//
//    public String[] cargarBloques() {
//        return bloques;
//    }