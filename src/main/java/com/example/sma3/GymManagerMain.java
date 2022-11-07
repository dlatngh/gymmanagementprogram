package com.example.sma3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Runs and launches the Gym Manager GUI
 *
 * @author David Kim, Sooho Lim
 */
public class GymManagerMain extends Application {
    /**
     * main stage of GUI
     */
    Stage primaryStage;

    /**
     * method to start the GUI ap[
     *
     * @param stage the stage
     * @throws IOException in out exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class.getResource("GymManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Gym Manager");
        stage.getIcons().add(new Image("file:src/main/resources/images/Scarlet-Knight.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * driver
     *
     * @param args command line
     */
    public static void main(String[] args) {
        launch();
    }
}