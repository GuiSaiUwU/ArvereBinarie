package com.gjls.arverebinarie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuInicial extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuInicial.class.getResource("MenuInicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Visualizador De Árvores");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}