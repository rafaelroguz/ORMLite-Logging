package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JOptionPane;

import mvc.model.UserModel;
import mvc.view.UserView;

/**
 * La clase UserController permite enlazar la GUI con el modelo (UserView con
 * UserModel). Permite recuperar información de los elementos de la GUI, así como
 * comunicarse con los métodos de UserModel para procesar las peticiones de registro
 * de usuario o réplica de registros de usuario.
 * @author rafael
 */

public class UserController implements ActionListener {

    private static final int SUCCESS_REGISTER = 0;
    private static final int ERROR_FILE_NOT_FOUND = 1;
    private static final int ERROR_FILE_LECTURE = 2;
    private static final int ERROR_REGISTERED_USER = 3;
    private static final int ERROR_EMPTY_FIELDS = 4;
    private static final int ERROR_EMPTY_LOGBOOK = 5;
    private static final int SUCCESS_REPLICATE = 6;
    private final UserView view;
    private final UserModel model;
    
    public UserController(UserView view, UserModel model) {
        this.view = view;
        this.model = model;
        
        this.view.getRegisterButton().addActionListener(this);
        this.view.getReplicateButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    /**
     * Recupera el usuario y contraseña de la GUI y llama al método registerUser()
     * de la clase UserModel para que intente el registro del usuario.
     */
    
    private void registerUser() {
        String userName = view.getUserName().getText();
        String password = Arrays.toString(view.getPassword().getPassword());
        String EMPTY_PASSWORD = "[]";
        
        if (userName.equals("") || password.equals(EMPTY_PASSWORD)) {
            showMessagesToUser(ERROR_EMPTY_FIELDS);
        } else {
            showMessagesToUser(model.registerUser(userName, password));
        }
    }
    
    /**
     * Llama al método adecuado para replicar los usuarios en la base de datos
     * configurada.
     */
    
    private void replicateActions() {
        if (model.replicateUsers()) {
            showMessagesToUser(SUCCESS_REPLICATE);
        } else {
            showMessagesToUser(ERROR_EMPTY_LOGBOOK);
        }
    }
    
    /**
     * Muestra distintos mensajes al usuario dependiendo del código de mensaje
     * recibido.
     * @param messageCode: Código del mensaje a mostrar. 
     */
    
    private void showMessagesToUser(int messageCode) {
        switch (messageCode) {
            case SUCCESS_REGISTER:
                JOptionPane.showMessageDialog(
                    null,
                    "Registro de usuario exitoso!");
                break;
            case ERROR_FILE_NOT_FOUND:
                JOptionPane.showMessageDialog(
                    null,
                    "No se encontró el archivo de configuración de la BD.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_FILE_LECTURE:
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
            case ERROR_EMPTY_FIELDS:
                JOptionPane.showMessageDialog(
                    null,
                    "Los campos de usuario y contraseña no pueden estar vacíos.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case ERROR_EMPTY_LOGBOOK:
                JOptionPane.showMessageDialog(
                    null,
                    "No hay historial de registro de usuarios.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case SUCCESS_REPLICATE:
                JOptionPane.showMessageDialog(
                    null,
                    "Restauración exitosa!");
                break;
        }
    }
    
    
    /**
     * Redirige el flujo de los eventos recibidos (botón presionado) al método
     * adecuado.
     * @param event Evento de botón presionado.
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();
        
        if (pressedButton == view.getRegisterButton()) {
            registerUser();
        }
        else if (pressedButton == view.getReplicateButton()) {
            replicateActions();
        }
        else if (pressedButton == view.getExitButton()) {
            System.exit(0);
        }
    }

}
