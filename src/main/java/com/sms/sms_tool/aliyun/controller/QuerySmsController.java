package com.sms.sms_tool.aliyun.controller;

import com.sms.sms_tool.aliyun.service.QuerySmsService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_KEY;
import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_SECRET;

public class QuerySmsController {
    private final QuerySmsService smsService;

    @FXML
    private TextField pageSizeField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField queryDateField;
    @FXML
    private TextField currentPageField;
    @FXML
    private TextArea resultArea;

    public QuerySmsController() throws Exception {
        // 初始化短信查询服务
        this.smsService = new QuerySmsService(ACCESS_KEY, ACCESS_SECRET);
    }

    @FXML
    public void querySms() {
        String phoneNumbers = phoneNumberField.getText().trim();
        String queryDate = queryDateField.getText().trim();
        String pageSize = pageSizeField.getText().trim();
        String currentPage = currentPageField.getText().trim();

        // 检查必填字段是否为空
        if (phoneNumbers.isEmpty() || queryDate.isEmpty() || pageSize.isEmpty() || currentPage.isEmpty()) {
            resultArea.setText("Error: 请填写所有必需参数！");
            return;
        }

        try {
            // 调用服务层查询短信记录
            String result = smsService.querySms(phoneNumbers, queryDate, pageSize, currentPage);
            resultArea.setText(result);
        } catch (Exception e) {
            resultArea.setText("Error: 查询失败 - " + e.getMessage());
        }
    }
}
