package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable {

    private Department entity; //injecão de dependencia

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department entity){
        this.entity = entity;
    }

    @FXML
    public void onBtSaveAction(){
        System.out.println("BUTTON SAVE ACTION!");
    }

    @FXML
    public void onBtCancelAction(){
        System.out.println("BUTTO CANCEL ACTION!");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();        
    }    

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);  //Adiciona restrição no input da tabela
        Constraints.setTextFieldMaxLength(txtName, 30);
    }
    
    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId())); //converte o valor id(int) para uma string
        txtName.setText(entity.getName());
    }

}
