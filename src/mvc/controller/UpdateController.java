package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc.model.UpdateModel;
import mvc.view.UpdateView;

public class UpdateController implements ActionListener {

    private final UpdateView view;
    private final UpdateModel model;
    
    public UpdateController(UpdateView view, UpdateModel model) {
        this.model = model;
        this.view = view;
        this.view.getSearchButton().addActionListener(this);
        this.view.getUpdateButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();
        
        if (pressedButton == view.getSearchButton()) {
            
        }
        else if (pressedButton == view.getUpdateButton()) {
            
        }
        else if (pressedButton == view.getExitButton()) {
            view.dispose();
        }
    }
    
}
