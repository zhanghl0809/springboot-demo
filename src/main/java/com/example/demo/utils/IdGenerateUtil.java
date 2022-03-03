package com.example.demo.utils;

import com.example.demo.common.enums.SnowflakeIdEnum;

import java.util.UUID;

/**
 * uuid生成
 */
public class IdGenerateUtil {

    public static String getUUID() {
        long uuid;
        do {
            uuid = UUID.randomUUID().getMostSignificantBits();
        } while(uuid <= 0L);
        return String.valueOf(uuid);
    }

    public static Long getLongUUID() {
        return Math.abs(UUID.randomUUID().getMostSignificantBits());
    }

    public static String getId(SnowflakeIdEnum idEnum){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(idEnum.getWorkerId(), idEnum.getDatacenterId());
        return String.valueOf(idWorker.nextId());
    }

    public static Long getLongId(SnowflakeIdEnum idEnum){
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(idEnum.getWorkerId(), idEnum.getDatacenterId());
        return idWorker.nextId();
    }


}