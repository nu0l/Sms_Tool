package com.sms.sms_tool.aliyun.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {

    @FXML
    private Pane contentPane;

    @FXML
    private javafx.scene.control.Button sendSmsButton;
    @FXML
    private javafx.scene.control.Button querySmsButton;
    @FXML
    private javafx.scene.control.Button otherFunctionButton;
    @FXML
    private javafx.scene.control.Button querySmsSignButton;


    @FXML
    public void initialize() {
        // 默认加载发送短信功能
        loadView("/views/SendSmsView.fxml");

        // 绑定按钮事件
        sendSmsButton.setOnAction(event -> loadView("/views/SendSmsView.fxml"));
        querySmsButton.setOnAction(event -> loadView("/views/QuerySmsView.fxml"));
        querySmsSignButton.setOnAction(event -> loadView("/views/QuerySmsSignView.fxml"));
        otherFunctionButton.setOnAction(event -> loadView("/views/OtherFunctionView.fxml"));
    }


    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newView = loader.load();
            contentPane.getChildren().setAll(newView); // 动态切换功能界面
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

