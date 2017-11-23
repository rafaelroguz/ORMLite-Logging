package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import mvc.model.RegisterModel;
import mvc.model.User;
import mvc.view.RegisterView;

/**
 * La clase RegisterController realiza la conexión entre RegisterView y RegisterModel,
 * permitiendo enlazar las peticiones de la GUI con el modelo correspondiente.
 * Recupera la información necesaria de los elementos de la GUI y los envía al 
 * modelo para ser procesados de acuerdo a los eventos solicitados por la GUI.
 * @author Rafael Rodríguez Guzmán
 */

public class RegisterController implements ActionListener {
    
    private final int SUCCESS_REGISTER = 1;
    private final int ERROR_DBCONFIG_FILE = 2;
    private final int ERROR_DBCONFIG_LECTURE = 3;
    private final int ERROR_REGISTERED_USER = 4;
    private final int ERROR_EMPTY_FIELDS = 10;
    
    private final RegisterView view;
    private final RegisterModel model;
    
    public RegisterController(RegisterView view, RegisterModel model) {
        this.model = model;
        this.view = view;
        this.view.getRegisterButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    /**
     * Recupera los datos del usuario de la GUI y los envía a RegisterModel para
     * intentar realizar el registro del usuario.
     */
    
    private void registerUser() {
        String userName = view.getUserName().getText();
        String firstName = view.getFirstName().getText();
        String lastName = view.getLastName().getText();
        String email = view.getEmail().getText();
        boolean emptyFields = checkEmptyFields(userName, firstName, lastName, email);
        
        if (emptyFields) {
            showMessageToUser(ERROR_EMPTY_FIELDS);
        } else {
            User user = new User();            
            user.setUserName(userName);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            
            int resultCode = model.registerUser(user);
            
            showMessageToUser(resultCode);
            
            cleanFields();
        }
    }
    
    /**
     * Valida si alguno de los campos de usuario en la GUI se encuentra vacío.
     * @param userName Cadena de texto que representa la entrada en el campo de 
     * nombre de usuario.
     * @param firstName Cadena de texto que representa la entrada en el campo de
     * nombre del usuario.
     * @param lastName Cadena de texto que representa la entrada en el campo de 
     * apellido del usuario.
     * @param email Cadena de texto que representa la entrada en el campo de 
     * correo electrónico del usuario.
     * @return true si alguno de los campos está vacío, false si todos los campos
     * tienen los datos correspondientes.
     */
    
    private boolean checkEmptyFields(String userName, String firstName, String lastName, String email) {
        if (userName.equals("")  || 
            firstName.equals("") || 
            lastName.equals("")  || 
            email.equals("")) {
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Limpia los campos del usuario después de realizar un registro.
     */
    
    private void cleanFields() {
        view.getUserName().setText("");
        view.getFirstName().setText("");
        view.getLastName().setText("");
        view.getEmail().setText("");
    }

    /**
     * Muestra distintos mensajes al usuario dependiendo del código de mensaje
     * recibido.
     * @param messageCode: Código del mensaje a mostrar. 
     */
    
    private void showMessageToUser(int messageCode) {
        switch (messageCode) {
            case ERROR_EMPTY_FIELDS:
                JOptionPane.showMessageDialog(
                    null,
                    "Los campos de datos del usuario no pueden estar vacíos.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case SUCCESS_REGISTER:
                JOptionPane.showMessageDialog(
                    null,
                    "Registro de usuario exitoso!");
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
        }
    }
    
    /**
     * Recibe un evento (un botón presionado en la GUI) y administra el flujo del
     * programa hacia el método correspondiente.
     * @param event Evento de botón presionado recibido.
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();
        
        if (pressedButton == view.getRegisterButton()) {
            registerUser();
        }
        else if (pressedButton == view.getExitButton()) {
            view.dispose();
        }
    }
    
}
