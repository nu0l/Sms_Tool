module com.example.sms_tool {
    requires javafx.controls;
    requires javafx.fxml;
    requires tea.openapi;
    requires tea.util;
    requires darabonba.env;
    requires dysmsapi20170525;
    requires org.slf4j;
    requires java.sql;
    requires com.fasterxml.jackson.databind;

    opens com.sms.sms_tool to javafx.fxml;
    exports com.sms.sms_tool;
    exports com.sms.sms_tool.aliyun.config;
    opens com.sms.sms_tool.aliyun.config to javafx.fxml;
    exports com.sms.sms_tool.aliyun.controller;
    opens com.sms.sms_tool.aliyun.controller to javafx.fxml;
    exports com.sms.sms_tool.aliyun.service;
    opens com.sms.sms_tool.aliyun.service to javafx.fxml;
}
     