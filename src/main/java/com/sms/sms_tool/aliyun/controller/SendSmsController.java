package com.sms.sms_tool.aliyun.controller;

import com.sms.sms_tool.aliyun.service.QuerySmsSignService;
import com.sms.sms_tool.aliyun.service.SendSmsService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_KEY;
import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_SECRET;

public class SendSmsController {
    private static final Logger logger = LoggerFactory.getLogger(SendSmsController.class);

    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField signNameField;
    @FXML
    private TextField templateCodeField;
    @FXML
    private TextField templateParamField;
    @FXML
    private TextArea resultArea;

    private final SendSmsService smsService;

    public SendSmsController() throws Exception {
        // 初始化短信服务
        this.smsService = new SendSmsService(ACCESS_KEY, ACCESS_SECRET);
    }

    @FXML
    public void sendSms() {
        // 获取用户输入
        String phoneNumbers = phoneNumberField.getText().trim();
        String signName = signNameField.getText().trim();
        String templateCode = templateCodeField.getText().trim();
        String templateParam = templateParamField.getText().trim();

        // 检查必填字段
        if (phoneNumbers.isEmpty() || signName.isEmpty() || templateCode.isEmpty() || templateParam.isEmpty()) {
            resultArea.setText("Error: 请填写所有必需参数！");
            logger.error("Invalid input parameters");
            return;
        }

        try {
            // 将模板参数转换为键值对形式
            Map<String, String> templateParams = parseTemplateParams(templateParam);

            // 调用发送短信服务
            String result = smsService.sendSms(phoneNumbers, signName, templateCode, templateParams);

            // 显示结果
            resultArea.setText(result);
            logger.info("SMS sent successfully");
        } catch (IllegalArgumentException e) {
            // 参数解析错误
            logger.error("Invalid template param format", e);
            resultArea.setText("Error: 模板参数格式不正确！请使用键值对形式（如：key1:value1,key2:value2）。");
        } catch (Exception e) {
            // 其他异常
            logger.error("Exception occurred while sending SMS", e);
            resultArea.setText("Error: 发送短信失败 - " + e.getMessage());
        }
    }

    /**
     * 解析用户输入的模板参数字符串为键值对
     *
     * @param input 用户输入的模板参数字符串（格式：key1:value1,key2:value2）
     * @return 模板参数的 Map 形式
     * @throws IllegalArgumentException 如果格式不正确
     */
    private Map<String, String> parseTemplateParams(String input) {
        Map<String, String> templateParams = new HashMap<>();
        String[] pairs = input.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("Invalid template param format");
            }
            templateParams.put(keyValue[0].trim(), keyValue[1].trim());
        }

        return templateParams;
    }
}