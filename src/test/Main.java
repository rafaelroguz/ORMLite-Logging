package test;

import mvc.controller.MainController;
import mvc.model.MainModel;
import mvc.view.MainView;

/**
 * La clase Main ejecuta la primera ventana de la GUI, que sirve como incio de la
 * aplicación.
 * @author Rafael Rodríguez Guzmán
 */

public class Main {
    
    public static void main(String[] args) {
        MainController controller = new MainController(new MainView(), new MainModel());
    }
    
}
