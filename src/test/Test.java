package test;

import mvc.controller.UserController;
import mvc.model.UserModel;
import mvc.view.UserView;

public class Test {
    
    public static void main(String[] args) {
        UserController controller = 
            new UserController(new UserView(), new UserModel());
    }
    
}
