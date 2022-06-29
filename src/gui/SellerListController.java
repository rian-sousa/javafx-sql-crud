package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.App;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerServices;


public class SellerListController implements Initializable, DataChangeListener {

    private SellerServices service;

    @FXML
    private TableView<Seller> tableViewSellers;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, Double> tableColumnBaseSalary;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Seller> obsList;


    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Seller obj = new Seller();
        createDialogForm(obj, "/gui/SellerForm.fxml", parentStage);
    }

    public void setSellerServices(SellerServices service){
        this.service = service; //Injeção de dependecia
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InitializeNodes();
    }

    private void InitializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));    //Inicializa o fucionamento da tabela
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        Utils.formatTableColumnDate(tableColumnBirthDate, "dd/MM/yyyy");
        tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
        Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);

        Stage stage = (Stage) App.getMainScene().getWindow(); //Referencia pra tela principal
        tableViewSellers.prefHeightProperty().bind(stage.heightProperty()); //TableView acompanhar o tamanho da janela
        
    }

    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list); 
        tableViewSellers.setItems(obsList);
        initEditButtons();
        initRemoveButtons();    
    }

    private void createDialogForm(Seller obj, String absoluteName, Stage parentStage){
        // try {
        //     FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
        //     Pane pane = loader.load();

        //     SellerFormController controller = loader.getController(); //pegar referencia p/ controlador
        //     controller.setSeller(obj);
        //     controller.setSellerServices(new SellerServices());
        //     controller.subscribeDataChangeListener(this);  //habilitando o listener/observer p/ esssa classe
        //     controller.updateFormData();

        //     Stage dialogStage = new Stage();
        //     dialogStage.setTitle("Enter Seller data");
        //     dialogStage.setScene(new Scene(pane));
        //     dialogStage.setResizable(false);
        //     dialogStage.initOwner(parentStage); //quem que é o stage pai da janela? parentStage
        //     dialogStage.initModality(Modality.WINDOW_MODAL); //enquanto não fechar a janela n é possível mexer na tela anterior
        //     dialogStage.showAndWait();
            
        // } catch (IOException e) {
        //     Alerts.showAlert("IO Excepetion", "Error loading view", e.getMessage(), AlertType.ERROR);
        // }
    }

    @Override
    public void onDataChange() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("Edit");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/gui/SellerForm.fxml", Utils.currentStage(event)));
            }
        });
    } 

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("Remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        if(result.get() == ButtonType.OK){
            if(service == null){
                throw new IllegalStateException("Service was null");
            }
            try{
                service.remove(obj);
                updateTableView();
            } 
            catch(DbIntegrityException e){
                Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
            }
        }
    }
}