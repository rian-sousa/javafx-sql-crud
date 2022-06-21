package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.App;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj, "/gui/DepartmentForm.fxml", parentStage);
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

    private void createDialogForm(Department obj, String absoluteName, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            DepartmentFormController controller = loader.getController(); //pegar referencia p/ controlador
            controller.setDepartment(obj);
            controller.setDepartmentServices(new DepartmentServices());
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage); //quem que é o stage pai da janela? parentStage
            dialogStage.initModality(Modality.WINDOW_MODAL); //enquanto não fechar a janela n é possível mexer na tela anterior
            dialogStage.showAndWait();
            
        } catch (IOException e) {
            Alerts.showAlert("IO Excepetion", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
    
}
