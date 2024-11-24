package com.sms.sms_tool;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/views/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 515, 500); // 设置合适的窗口大小
        stage.setTitle("Sms Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}