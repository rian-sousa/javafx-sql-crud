package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentServices;

public class DepartmentListController implements Initializable {

    private DepartmentServices service;

    @FXML
    private TableView<Department> tableViewDepartments;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;


    @FXML
    public void onBtNewAction(){
        System.out.println("BUTTON NEW ACTION!");
    }

    public void setDepartmentServices(DepartmentServices service){
        this.service = service; //Injeção de dependecia
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

    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list); 
        tableViewDepartments.setItems(obsList);    
    }
    
}
