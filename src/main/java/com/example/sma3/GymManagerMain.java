package com.example.sma3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.io.IOException;


public class GymManagerMain extends Application {
    Button butt1, butt2;
    Label label1, label2;
    Stage primaryStage;
    FlowPane fPane1, fPane2;
    Scene scene2;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(GymManagerMain.class.getResource("GymManagerView.fxml"));
        TextArea textArea = new TextArea();
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Gym Manager");
        stage.getIcons().add(new Image("file:src/main/resources/images/Scarlet-Knight.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}