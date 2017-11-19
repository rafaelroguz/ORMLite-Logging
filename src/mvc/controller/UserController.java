package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JOptionPane;

import mvc.model.UserModel;
import mvc.view.UserView;

public class UserController implements ActionListener {

    private static final int SUCCESS_REGISTER = 0;
    private static final int ERROR_FILE_NOT_FOUND = 1;
    private static final int ERROR_FILE_LECTURE = 2;
    private static final int ERROR_REGISTERED_USER = 3;
    private static final int ERROR_EMPTY_FIELDS = 4;
    
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
    
    private void replicateActions() {
        model.replicateUsers();
    }
    
    private void showMessagesToUser(int errorCode) {
        switch (errorCode) {
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
        }
    }
    
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
