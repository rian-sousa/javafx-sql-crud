package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class DepartmentListController implements Initializable {

    @FXML
    private TableView<Department> tableViewDepartments;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(){
        System.out.println("BUTTON NEW ACTION!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InitializeNodes();
    }

    private void InitializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));    //Inicializa o fucionamento da tabela
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) App.getMainScene().getWindow(); //Referencia pra tela principal
        tableViewDepartments.prefHeightProperty().bind(stage.heightProperty()); //TableView acompanhar o tamanho da janela
        
    }
    
}
