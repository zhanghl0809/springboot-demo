package com.example.demo.common.enums;

/**
 * id生成，业务类型枚举
 */
public enum SnowflakeIdEnum {

    /**
     * 业务类型
     */
    USER(1L,1L);


    /**
     * 工作ID (0~31)
     */
    private Long workerId;
    /**
     * 数据中心ID (0~31)
     */
    private Long datacenterId;

    SnowflakeIdEnum(Long workerId, Long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public Long getWorkerId() {
        return this.workerId;
    }
    public Long getDatacenterId() {
        return this.datacenterId;
    }
}
