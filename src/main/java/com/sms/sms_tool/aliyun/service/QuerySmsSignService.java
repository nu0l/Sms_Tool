package com.sms.sms_tool.aliyun.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.QuerySmsSignListRequest;
import com.aliyun.dysmsapi20170525.models.QuerySmsSignListResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuerySmsSignService {
    private static final Logger logger = LoggerFactory.getLogger(QuerySmsSignService.class);
    private final Client client;

    /**
     * 构造方法，用于初始化阿里云短信服务的客户端
     *
     * @param accessKeyId     阿里云访问密钥 ID
     * @param accessKeySecret 阿里云访问密钥 Secret
     */
    public QuerySmsSignService(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        this.client = new Client(config);
    }

    /**
     * 查询签名列表详情
     *
     * @param pageIndex 当前页码 (从 1 开始)
     * @param pageSize 每页显示的签名个数
     * @return 查询结果字符串
     */
    public String querySmsSign(int pageIndex, int pageSize) {

        try {
            QuerySmsSignListRequest queryRequest = new QuerySmsSignListRequest()
                    .setPageIndex(pageIndex)
                    .setPageSize(pageSize);

            RuntimeOptions runtime = new RuntimeOptions();
            QuerySmsSignListResponse response = client.querySmsSignListWithOptions(queryRequest, runtime);

            // 获取响应内容
            String code = response.body.code;
            String message = response.body.message;

            if ("OK".equals(code)) {
                logger.info("Query SmsSign success: PageIndex={}, PageSize={}, Code={}, Message={}", pageIndex, pageSize, code, message);
                return "Success: " + message;
            } else {
                logger.error("Failed to query SmsSign: PageIndex={}, PageSize={}, Code={}, Message={}", pageIndex, pageSize, code, message);
                return String.format("Error: %s - %s", code, message);
            }
        } catch (Exception e) {
            logger.error("Exception occurred while querying SmsSign: PageIndex={}, PageSize={}", pageIndex, pageSize, e);
            return "Exception: " + e.getMessage();
        }
    }
}