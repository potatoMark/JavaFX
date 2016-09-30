package cn.mark;


import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {


    private Stage primaryStage;
    private BorderPane rootLayout;


    public MainApp(){

    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SocketApp");


        // Set the application icon.

        initRootLayout();

    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/demoView.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(personOverview);
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
