package com.sms.sms_tool.aliyun.controller;

import com.sms.sms_tool.aliyun.service.QuerySmsSignService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsSignService.class);

    @FXML
    private ComboBox<String> vendorComboBox;
    @FXML
    private Pane contentPane;
    @FXML
    private Button sendSmsButton;
    @FXML
    private Button querySmsButton;
    @FXML
    private Button querySmsSignButton;
    @FXML
    private Button querySmsTemplateButton;
    @FXML
    private Button otherFunctionButton;
    private String selectedVendor;

    @FXML
    public void initialize() {
        loadView("/views/aliyun/OtherFunctionView.fxml");
        // 初始化厂商选择下拉框
        vendorComboBox.setItems(FXCollections.observableArrayList("阿里云", "腾讯云", "华为云"));
        vendorComboBox.getSelectionModel().selectFirst(); // 默认选择第一个厂商
        selectedVendor = vendorComboBox.getSelectionModel().getSelectedItem();

        // 添加厂商选择监听器
        vendorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedVendor = newValue;
            logger.info("Selected Vendor: " + selectedVendor);
            // 当切换厂商时，可以清空当前功能或进行其他操作
            contentPane.getChildren().clear();
        });

        // 默认加载发送短信功能
        // loadView("/views/SendSmsView.fxml");

        // 绑定按钮事件
        sendSmsButton.setOnAction(event -> loadView("/views/aliyun/SendSmsView.fxml"));
        querySmsButton.setOnAction(event -> loadView("/views/aliyun/QuerySmsView.fxml"));
        querySmsSignButton.setOnAction(event -> loadView("/views/aliyun/QuerySmsSignView.fxml"));
        querySmsTemplateButton.setOnAction(event -> loadView("/views/aliyun/QuerySmsTemplateView.fxml"));
        otherFunctionButton.setOnAction(event -> loadView("/views/aliyun/OtherFunctionView.fxml"));
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Pane newView = loader.load();
            contentPane.getChildren().setAll(newView); // 动态切换功能界面
            // 如果子界面需要厂商信息，可以通过控制器传递
            Object controller = loader.getController();
            if (controller instanceof VendorAware) {
                ((VendorAware) controller).setVendor(selectedVendor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface VendorAware {
        void setVendor(String vendor);
    }
}