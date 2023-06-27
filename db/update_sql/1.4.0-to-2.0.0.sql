ALTER TABLE `log_access_log`
    CHANGE COLUMN `ip` `client_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问IP地址' AFTER `username`,
    CHANGE COLUMN `uri` `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求URI' AFTER `user_agent`,
    CHANGE COLUMN `method` `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方式' AFTER `matching_pattern`,
    CHANGE COLUMN `req_params` `query_string` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数' AFTER `request_method`,
    CHANGE COLUMN `req_body` `request_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求body' AFTER `query_string`,
    CHANGE COLUMN `http_status` `response_status` int(5) NULL DEFAULT NULL COMMENT '响应状态码' AFTER `request_body`,
    CHANGE COLUMN `result` `response_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应信息' AFTER `response_status`,
    CHANGE COLUMN `error_msg` `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误消息' AFTER `response_body`,
    CHANGE COLUMN `time` `execution_time` bigint(64) NULL DEFAULT NULL COMMENT '执行时长' AFTER `error_message`,
    MODIFY COLUMN `trace_id` char(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '追踪ID' AFTER `id`;