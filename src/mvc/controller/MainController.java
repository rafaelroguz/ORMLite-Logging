package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            
        }
        else if (pressedButton == view.getExitButton()) {
            System.exit(0);
        }
    }
    
}
