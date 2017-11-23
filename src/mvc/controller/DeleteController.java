package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import mvc.model.DeleteModel;
import mvc.model.User;
import mvc.view.DeleteView;

/**
 * La clase DeleteController realiza la conexión entre DeleteView y DeleteModel,
 * permitiendo enlazar las peticiones de la GUI con el modelo correspondiente.
 * Recupera el nombre de usuario de la GUI y lo envía al modelo para procesar las
 * peticiones de búsqueda y eliminación de usuarios.
 * @author Rafael Rodríguez Guzmán
 */

public class DeleteController implements ActionListener {
    
    private final String SUCCESS = "SUCCESS";
    private final String ERROR_DBCONFIG_FILE = "ERROR_DBCONFIG_FILE";
    private final String ERROR_DBCONFIG_LECTURE = "ERROR_DBCONFIG_LECTURE";
    private final String ERROR_DELETE = "ERROR_DELETE";
    private final String ERROR_EMPTY_FIELDS = "ERROR_EMPTY_FIELDS";
    private final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    private final DeleteView view;
    private final DeleteModel model;
    private User user;
    
    public DeleteController(DeleteView view, DeleteModel model) {
        this.model = model;
        this.user = new User();
        this.view = view;
        this.view.getSearchButton().addActionListener(this);
        this.view.getDeleteButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    /**
     * Busca una entrada en la BD que coincida con el nombre de usuario introducido,
     * recuperando un usuario (User) en caso de ser posible.
     */
    
    private void searchUser() {
        String userName = view.getUserName().getText();
        
        cleanFields();
        
        if (userName.equals("")) {
            showMessageToUser(ERROR_EMPTY_FIELDS);
        } else {
            user = model.findUser(userName);
            
            if (user == null) {
                showMessageToUser(ERROR_USER_NOT_FOUND);
            } else {
                setFields(user);
            }
        }
    }
    
    /**
     * Recibe la petición de eliminación de usuario y la redirige a DeleteModel
     * para ser procesada.
     */
    
    private void deleteUser() {
        String resultCode = model.deleteUser(user);
        
        showMessageToUser(resultCode);
        
        cleanFields();
    }
    
    /**
     * Muestra distintos mensajes al usuario dependiendo del código de mensaje
     * recibido.
     * @param messageCode: Código del mensaje a mostrar. 
     */
    
    private void showMessageToUser(String messageCode) {
        switch (messageCode) {
            case ERROR_EMPTY_FIELDS:
                JOptionPane.showMessageDialog(
                    null,
                    "Introduzca un nombre de usuario a buscar.",
                    null,
                    JOptionPane.ERROR_MESSAGE);
                break;
            case SUCCESS:
                JOptionPane.showMessageDialog(
                    null,
                    "Se eliminó el usuario correctamente!");
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
            case ERROR_DELETE:
            JOptionPane.showMessageDialog(
                null,
                "No se pudo eliminar el usuario.",
                null,
                JOptionPane.ERROR_MESSAGE);
            break; 
        }
    }
    
    /**
     * Limpia  y deshabilita los campos del usuario después de realizar una 
     * eliminación de usuario, así como el botón de eliminar usuario.
     */
    
    private void cleanFields() {
        view.getUserName().setText("");
        
        view.getFirstName().setText("");
        
        view.getLastName().setText("");
        
        view.getEmail().setText("");
        
        view.getDeleteButton().setEnabled(false);
    }
    
     /**
     * Llena y habilita los campos de datos del usuario en la GUI después de 
     * buscar y encontrar el usuario en la BD, así como el botón de actualizar
     * usuario.
     * @param user Usuario cuyos datos se mostrarán en la GUI.
     */
    
    private void setFields(User user) {
        view.getFirstName().setText(user.getFirstName());
        
        view.getLastName().setText(user.getLastName());
        
        view.getEmail().setText(user.getEmail());
        
        view.getDeleteButton().setEnabled(true);
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
        else if (pressedButton == view.getDeleteButton()) {
            deleteUser();
        }
        else if (pressedButton == view.getExitButton()) {
            view.dispose();
        }
    }
    
}
