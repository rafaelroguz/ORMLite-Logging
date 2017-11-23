package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mvc.model.DeleteModel;
import mvc.view.DeleteView;

public class DeleteController implements ActionListener {
    
    private final DeleteView view;
    private final DeleteModel model;
    
    public DeleteController(DeleteView view, DeleteModel model) {
        this.model = model;
        this.view = view;
        this.view.getSearchButton().addActionListener(this);
        this.view.getDeleteButton().addActionListener(this);
        this.view.getExitButton().addActionListener(this);
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        Object pressedButton = event.getSource();
        
        if (pressedButton == view.getSearchButton()) {
            
        }
        else if (pressedButton == view.getDeleteButton()) {
            
        }
        else if (pressedButton == view.getExitButton()) {
            view.dispose();
        }
    }
    
}
