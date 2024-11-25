package com.sms.sms_tool.aliyun.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateListRequest;
import com.aliyun.dysmsapi20170525.models.QuerySmsTemplateListResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuerySmsTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsTemplateService.class);
    private final Client client;

    /**
     * 构造方法，初始化阿里云短信服务客户端
     *
     * @param accessKeyId     阿里云访问密钥 ID
     * @param accessKeySecret 阿里云访问密钥 Secret
     */
    public QuerySmsTemplateService(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        this.client = new Client(config);
    }

    /**
     * 查询短信模板列表
     *
     * @param pageIndex 当前页码
     * @param pageSize  每页显示的模板数量
     * @return 查询结果字符串
     */
    public String querySmsTemplate(int pageIndex, int pageSize) throws Exception {
        try {
            QuerySmsTemplateListRequest request = new QuerySmsTemplateListRequest()
                    .setPageIndex(pageIndex)
                    .setPageSize(pageSize);

            QuerySmsTemplateListResponse response = client.querySmsTemplateList(request);

            String code = response.body.code;
            String message = response.body.message;

            // 根据响应状态处理结果
            if ("OK".equals(code)) {
                logger.info("Query SMS Template success: Code={}, Message={}", code, message);
                return "Success: " + message;
            } else {
                logger.error("Failed to query SMS Template: Code={}, Message={}", code, message);
                return String.format("Error: %s - %s", code, message);
            }
        } catch (Exception e) {
            logger.error("Exception occurred during querying SMS Template", e);
            return "Exception: " + e.getMessage();
        }
    }
}