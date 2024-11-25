package com.sms.sms_tool.aliyun.controller;

import com.sms.sms_tool.aliyun.service.QuerySmsSignService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_KEY;
import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_SECRET;

public class QuerySmsSignController {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsSignController.class);
    @FXML
    private TextField pageIndexField;
    @FXML
    private TextField pageSizeField;
    @FXML
    private TextArea resultArea;

    private QuerySmsSignService querySmsSignService;
    public QuerySmsSignController() throws Exception {
        this.querySmsSignService = new QuerySmsSignService(ACCESS_KEY, ACCESS_SECRET);
    }

    public void querySmsSign() {
        String pageIndexStr = pageIndexField.getText();
        String pageSizeStr = pageSizeField.getText();

        if (pageIndexStr.isEmpty() || pageSizeStr.isEmpty()) {
            logger.error("Error: 请填写所有必需参数！");
            resultArea.setText("Error: 请填写所有必需参数！");
            return;
        }

        int pageIndex = Integer.parseInt(pageIndexStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        try {
            String result = querySmsSignService.querySmsSign(pageIndex, pageSize);
            logger.info("Query SmsSign result: " + result);
            resultArea.setText(result);
        }
        catch (Exception e) {
            logger.error("Exception occurred while querying SmsSign: PageIndex={}, PageSize={}", pageIndex, pageSize, e);
            resultArea.setText("Error: 签名查询失败 - " + e.getMessage());
        }

    }
}
