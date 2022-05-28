package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXMLLoader;


public class App extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
            ScrollPane scrollPane = loader.load();

            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            mainScene = new Scene(scrollPane);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("CRUD - Rian Sousa");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getMainScene(){
        return  mainScene;
    }
    public static void main(String[] args) throws Exception {
        launch(args);
        
    }
}
