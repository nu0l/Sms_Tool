package com.sms.sms_tool.aliyun.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class SendSmsService {

    private static final Logger logger = LoggerFactory.getLogger(SendSmsService.class);
    private final Client client;
    private final ObjectMapper mapper = new ObjectMapper(); // 创建一个全局 ObjectMapper 实例，避免重复创建

    /**
     * 构造方法，用于初始化阿里云短信服务的客户端
     *
     * @param accessKeyId     阿里云访问密钥 ID
     * @param accessKeySecret 阿里云访问密钥 Secret
     */
    public SendSmsService(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        this.client = new Client(config);
    }

    /**
     * 发送短信
     *
     * @param phoneNumbers   接收短信的手机号，多个号码以逗号分隔，上限为 1000 个手机号码
     * @param signName       短信签名
     * @param templateCode   短信模板代码
     * @param templateParams 模板参数，作为键值对提供
     */
    public String sendSms(String phoneNumbers, String signName, String templateCode, Map<String, String> templateParams) {
        try {
            // 将模板参数转换为 JSON 格式
            String templateParam = mapper.writeValueAsString(templateParams);

            // 构建短信请求
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(phoneNumbers)
                    .setSignName(signName)
                    .setTemplateCode(templateCode)
                    .setTemplateParam(templateParam);

            // 调用阿里云短信服务
            SendSmsResponse response = client.sendSms(request);

            // 获取响应内容
            String code = response.body.code;
            String message = response.body.message;

            // 根据响应码记录日志和返回结果
            if ("OK".equals(code)) {
                logger.info("SMS sent successfully: PhoneNumbers={}, Code={}, Message={}", phoneNumbers, code, message);
                return "Success" + message;
            } else {
                logger.error("Failed to send SMS: PhoneNumbers={}, Code={}, Message={}", phoneNumbers, code, message);
                return String.format("Error: %s - %s", code, message);
            }
        } catch (Exception e) {
            logger.error("Exception occurred while sending SMS to: {}", phoneNumbers, e);
            return "Exception: " + e.getMessage();
        }
    }
}
