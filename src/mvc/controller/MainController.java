package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import mvc.model.DeleteModel;

import mvc.model.MainModel;
import mvc.model.RegisterModel;
import mvc.model.UpdateModel;
import mvc.view.DeleteView;
import mvc.view.MainView;
import mvc.view.RegisterView;
import mvc.view.UpdateView;

/**
 * La clase MainController maneja los eventos ocurridos en la GUI (que ocurren en
 * la clase MainView), y administra el procesamiento de peticiones de registro,
 * actualización, eliminación y replicación a través de diferentes vistas, modelos
 * y controladores correspondientes.
 * @author Rafael Rodríguez Guzmán
 */

public class MainController implements ActionListener {

    private final int SUCCESS = 1;
    private final int ERROR_DBCONFIG_FILE = 2;
    private final int ERROR_DBCONFIG_LECTURE = 3;
    private final int ERROR_REGISTERED_USER = 4;
    private final int ERROR_UPDATE = 5;
    private final int ERROR_DELETE = 6;
    private final int ERROR_EMPTY_LOGBOOK = 7;
    private final int ERROR_REPLICATE = 8;
    private final MainView view;
    private final MainModel model;
    
    public MainController(MainView view, MainModel model) {
        this.model = model;
        this.view = view;
        this.view.getRegisterButton().addActionListener(this);
        this.view.getUpdateButton().addActionListener(this);
        this.view.getDeleteButton().addActionListener(this);
        this.view.getReplicateButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    /**
     * Recibe la petición de replicar la BD desde la GUI, y la redirige a MainModel
     * para ser procesada.
     */
    
    private void replicateDB() {
        int resultCode = model.replicate();
        
        showMessageToUser(resultCode);
    }
    
    /**
     * Muestra distintos mensajes al usuario dependiendo del código de mensaje
     * recibido.
     * @param messageCode: Código del mensaje a mostrar. 
     */
    
    private void showMessageToUser(int messageCode) {
        switch (messageCode) {
            case SUCCESS:
                JOptionPane.showMessageDialog(
                    null,
                    "Réplica exitosa!");
                break;
            case ERROR_DBCONFIG_FILE:
                JOptionPane.showMessageDialog(
                    null,
                    "No se encontró el archivo de configuración de la BD.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_DBCONFIG_LECTURE:
                JOptionPane.showMessageDialog(
                    null,
                    "Error al leer el archivo de configuración de la BD.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_REGISTERED_USER:
                JOptionPane.showMessageDialog(
                    null,
                    "El usuario ya se encuentra registrado en la BD.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;  
            case ERROR_UPDATE:
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudo actualizar el usuario.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_DELETE:
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudo eliminar el usuario.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_EMPTY_LOGBOOK:
                JOptionPane.showMessageDialog(
                    null,
                    "La bitácora está vacía.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_REPLICATE:
                JOptionPane.showMessageDialog(
                    null,
                    "Error al realizar réplica de la BD.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
    
    /**
     * Recibe un evento (un botón presionado en la GUI) y administra el flujo del
     * programa hacia la vista, modelo y controlador correspondiente.
     * @param event Evento de botón presionado recibido.
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();
        
        if (pressedButton == view.getRegisterButton()) {
            RegisterController controller = 
                    new RegisterController(new RegisterView(), new RegisterModel());
        } 
        else if (pressedButton == view.getUpdateButton()) {
            UpdateController controller = 
                    new UpdateController(new UpdateView(), new UpdateModel());
        }
        else if (pressedButton == view.getDeleteButton()) {
            DeleteController controller = 
                    new DeleteController(new DeleteView(), new DeleteModel());
        }
        else if (pressedButton == view.getReplicateButton()) {
            replicateDB();
        }
        else if (pressedButton == view.getExitButton()) {
            System.exit(0);
        }
    }
    
}
