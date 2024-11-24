package com.sms.sms_tool.aliyun.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsRequest;
import com.aliyun.dysmsapi20170525.models.QuerySendDetailsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuerySmsService {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsService.class);
    private final Client client;

    /**
     * 构造方法，用于初始化阿里云短信服务的客户端
     *
     * @param accessKeyId     阿里云访问密钥 ID
     * @param accessKeySecret 阿里云访问密钥 Secret
     */
    public QuerySmsService(String accessKeyId, String accessKeySecret) throws Exception {
        // 初始化阿里云短信客户端
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        this.client = new Client(config);
    }

    /**
     * 查询短信发送详情
     * @param phoneNumbers 需要查询的手机号码
     * @param queryDate 短信发送日期
     * @param pageSize 每页显示条数
     * @param currentPage 当前页码
     */
    public String querySms(String phoneNumbers, String queryDate, String pageSize, String currentPage) throws Exception {
        try {
            // 构建查询请求
            QuerySendDetailsRequest request = new QuerySendDetailsRequest()
                    .setPhoneNumber(phoneNumbers)
                    .setSendDate(queryDate)
                    .setPageSize(Long.valueOf(pageSize))
                    .setCurrentPage(Long.valueOf(currentPage));

            // 查询短信发送详情
            RuntimeOptions runtimeOptions = new RuntimeOptions();
            QuerySendDetailsResponse response = client.querySendDetailsWithOptions(request, runtimeOptions);

            // 获取响应内容
            String code = response.body.code;
            String message = response.body.message;

            // 根据响应结果记录日志和返回消息
            if ("OK".equals(code)) {
                logger.info("Query SMS success: PhoneNumbers={}, Code={}, Message={}", phoneNumbers, code, message);
                return "Success: " + message;
            } else {
                logger.error("Failed to query SMS: PhoneNumbers={}, Code={}, Message={}", phoneNumbers, code, message);
                return String.format("Error: %s - %s", code, message);
            }
        } catch (NumberFormatException e) {
            logger.error("Invalid number format: PageSize or CurrentPage", e);
            return "Error: 页数或每页数量格式错误";
        } catch (Exception e) {
            logger.error("Exception occurred while querying SMS for phone number: {}", phoneNumbers, e);
            return "Exception: " + e.getMessage();
        }
    }
}
