// File: HelloApplication.java
package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mini File Explorer");

        // Initialize UI components
        MainUI ui = new MainUI();

        BorderPane glavniPane = ui.createMainLayout();
        Scene scene = new Scene(glavniPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
