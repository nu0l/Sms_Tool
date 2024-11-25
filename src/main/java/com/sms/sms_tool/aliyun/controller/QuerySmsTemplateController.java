package com.sms.sms_tool.aliyun.controller;

import com.sms.sms_tool.aliyun.service.QuerySmsTemplateService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_KEY;
import static com.sms.sms_tool.aliyun.config.AliyunConfig.ACCESS_SECRET;

public class QuerySmsTemplateController {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsTemplateController.class);

    private final QuerySmsTemplateService querySmsTemplateService;

    @FXML
    private TextField pageIndexField;
    @FXML
    private TextField pageSizeField;
    @FXML
    private TextArea resultArea;

    public QuerySmsTemplateController() throws Exception {
        // 初始化服务实例
        this.querySmsTemplateService = new QuerySmsTemplateService(ACCESS_KEY, ACCESS_SECRET);
    }

    @FXML
    public void querySmsTemplate() {
        // 获取用户输入
        String pageIndexStr = pageIndexField.getText();
        String pageSizeStr = pageSizeField.getText();

        // 输入校验
        if (pageIndexStr == null || pageIndexStr.isEmpty() || pageSizeStr == null || pageSizeStr.isEmpty()) {
            resultArea.setText("Error: 页码和页大小不能为空！");
            logger.warn("User input is empty: pageIndex={}, pageSize={}", pageIndexStr, pageSizeStr);
            return;
        }

        int pageIndex, pageSize;
        pageIndex = Integer.parseInt(pageIndexStr);
        pageSize = Integer.parseInt(pageSizeStr);

        if (pageIndex <= 0 || pageSize <= 0) {
            resultArea.setText("Error: 页码和页大小必须是正整数！");
            logger.warn("Invalid input values: pageIndex={}, pageSize={}", pageIndex, pageSize);
            return;
        }
        // 调用服务查询短信模板
        try {
            String result = querySmsTemplateService.querySmsTemplate(pageIndex, pageSize);
            resultArea.setText(result);
            logger.info("Query success: pageIndex={}, pageSize={}, result={}", pageIndex, pageSize, result);
        } catch (Exception e) {
            resultArea.setText("Error: 模板查询失败 - " + e.getMessage());
            logger.error("Exception during query: pageIndex={}, pageSize={}", pageIndex, pageSize, e);
        }
    }
}