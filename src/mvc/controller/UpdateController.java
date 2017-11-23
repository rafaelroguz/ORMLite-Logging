package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import mvc.model.UpdateModel;
import mvc.model.User;
import mvc.view.UpdateView;

/**
 * La clase UpdateController realiza la conexión entre UpdateView y UpdateModel,
 * permitiendo enlazar las peticiones de la GUI con el modelo correspondiente.
 * Recupera la información necesaria de los elementos de la GUI y los envía al 
 * modelo para ser procesados de acuerdo a los eventos solicitados por la GUI.
 * @author Rafael Rodríguez Guzmán
 */

public class UpdateController implements ActionListener {
    
    private final int SUCCESS_UPDATE = 1;
    private final int ERROR_DBCONFIG_FILE = 2;
    private final int ERROR_DBCONFIG_LECTURE = 3;
    private final int ERROR_UPDATE = 5;
    private final int ERROR_EMPTY_FIELDS = 10;
    private final int ERROR_USER_NOT_FOUND = 11;
    private final UpdateView view;
    private final UpdateModel model;
    private String userName;
    
    public UpdateController(UpdateView view, UpdateModel model) {
        this.model = model;
        this.view = view;
        this.view.getSearchButton().addActionListener(this);
        this.view.getUpdateButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    /**
     * Busca una entrada en la BD que coincida con el nombre de usuario introducido,
     * recuperando un usuario (User) en caso de ser posible.
     */
    
    private void searchUser() {
        userName = view.getUserName().getText();
        
        cleanFields();
        
        if (userName.equals("")) {
            showMessageToUser(ERROR_EMPTY_FIELDS);
        } else {
            User user = model.findUser(userName);
            
            if (user == null) {
                showMessageToUser(ERROR_USER_NOT_FOUND);
            } else {
                setFields(user);
            }
        }
    }
    
    /**
     * Recupera los datos del usuario de la GUI y los envía a UpdateModel para
     * tratar de realizar la actualización en la BD.
     */
    
    private void updateUser() {
        String firstName = view.getFirstName().getText();
        String lastName = view.getLastName().getText();
        String email = view.getEmail().getText();
        boolean emptyFields = checkEmptyFields(firstName, lastName, email);
        
        if (emptyFields) {
            showMessageToUser(ERROR_EMPTY_FIELDS);
        } else {
            User user = new User();            
            user.setUserName(userName);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            
            int resultCode = model.updateUser(user);
            showMessageToUser(resultCode);
            
            cleanFields();
        }
    }
    
    /**
     * Valida si alguno de los campos de usuario en la GUI se encuentra vacío.
     * @param firstName Cadena de texto que representa la entrada en el campo de
     * nombre del usuario.
     * @param lastName Cadena de texto que representa la entrada en el campo de 
     * apellido del usuario.
     * @param email Cadena de texto que representa la entrada en el campo de 
     * correo electrónico del usuario.
     * @return true si alguno de los campos está vacío, false si todos los campos
     * tienen los datos correspondientes.
     */
    
    private boolean checkEmptyFields(String firstName, String lastName, String email) {
        if (firstName.equals("") || 
            lastName.equals("")  || 
            email.equals("")) {
            
            return true;
        } else {
            return false;
        }
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
                    "Introduzca un nombre de usuario a buscar.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case SUCCESS_UPDATE:
                JOptionPane.showMessageDialog(
                    null,
                    "Actualización de usuario exitosa!");
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
            case ERROR_USER_NOT_FOUND:
                JOptionPane.showMessageDialog(
                    null,
                    "No se pudo encontrar al usuario.",
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
        }
    }
    
    /**
     * Llena y habilita los campos de datos del usuario en la GUI después de 
     * buscar y encontrar el usuario en la BD, así como el botón de actualizar
     * usuario.
     * @param user Usuario cuyos datos se mostrarán en la GUI.
     */
    
    private void setFields(User user) {
        view.getFirstName().setText(user.getFirstName());
        view.getFirstName().setEnabled(true);
        
        view.getLastName().setText(user.getLastName());
        view.getLastName().setEnabled(true);
        
        view.getEmail().setText(user.getEmail());
        view.getEmail().setEnabled(true);
        
        view.getUpdateButton().setEnabled(true);
    }
    
    /**
     * Limpia  y deshabilita los campos del usuario después de realizar una 
     * modificación al usuario, así como el botón de actualizar usuario.
     */
    
    private void cleanFields() {
        view.getUserName().setText("");
        
        view.getFirstName().setText("");
        view.getFirstName().setEnabled(false);
        
        view.getLastName().setText("");
        view.getLastName().setEnabled(false);
        
        view.getEmail().setText("");
        view.getEmail().setEnabled(false);
        
        view.getUpdateButton().setEnabled(false);
    }
    
    /**
     * Recibe un evento (un botón presionado en la GUI) y administra el flujo del
     * programa hacia el método correspondiente.
     * @param event Evento de botón presionado recibido.
     */
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();
        
        if (pressedButton == view.getSearchButton()) {
            searchUser();
        }
        else if (pressedButton == view.getUpdateButton()) {
            updateUser();
        }
        else if (pressedButton == view.getExitButton()) {
            view.dispose();
        }
    }
    
}
