package com.example.demo.service.impl;

import com.example.demo.common.exception.BizException;
import com.example.demo.domin.TbUser;
import com.example.demo.service.UserSignService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static com.example.demo.common.enums.BizResultEnum.USER_SIGN_ERROR;

@Slf4j
@Service
public class UserSignServiceImpl implements UserSignService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyyMM");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String getKey(TbUser user) {
        LocalDate now = LocalDate.now();
        String month = monthFormatter.format(now); //取当前月份
        String key = "sign:" + user.getUserId() + ":" + month;
        log.info("当前的ke={}", key);
        return key;
    }

    @Override
    public Boolean userSign(TbUser user, int day) {
        RLock lock = redisson.getLock("lock:userSign" + user.getUserId());
        try {
            //尝试加锁，最多等待2秒，上锁以后3秒自动解锁
            boolean tryLock = lock.tryLock(2, 3, TimeUnit.SECONDS);
            if (tryLock) {
                throw new BizException(USER_SIGN_ERROR);
            }
            String key = this.getKey(user);
            return redisTemplate.opsForValue().setBit(key, day - 1, true);
        } catch (Exception e) {
            throw new BizException(USER_SIGN_ERROR);
        } finally {
            //解锁
            if (lock != null){
                lock.unlock();
            }
        }
    }


    @Override
    public Integer getOneDayIsSign(TbUser user, int day) {
        String key = this.getKey(user);
        Boolean bit = redisTemplate.opsForValue().getBit(key, day - 1);
        return Boolean.TRUE.equals(bit) ? 1 : 0;
    }

    @Override
    public Long getMonthSignTotalCount(TbUser user) {
        String key = this.getKey(user);
        Object totalCount = redisTemplate.execute((RedisCallback<Long>) connection
                -> connection.bitCount(key.getBytes()));
        return Long.parseLong(totalCount.toString());
    }

    @Override
    public Integer getMonthContinuousSignDays(TbUser user) {
        String key = this.getKey(user);
        LocalDate localDate = LocalDate.now();
        int signCount = 0;
        long mask = 0b1;
        //用bitfield命令取出第一天到当前天的数据
        List<Long> signList = (List<Long>) redisTemplate.execute(new RedisCallback<List<Long>>() {
            @Override
            public List<Long> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitField(
                        key.getBytes(),
                        BitFieldSubCommands
                                .create()
                                .get(BitFieldSubCommands.BitFieldType.unsigned(localDate.getDayOfMonth()))
                                .valueAt(0)
                );
            }
        });
        if (!CollectionUtils.isEmpty(signList)) {
            long sign = signList.get(0) == null ? 0 : signList.get(0);
            for (int i = 0; i < localDate.getDayOfMonth(); i++) {
                //判断低位为0表示没有签到
                if ((sign & mask) == 0) {
                    //没有签到的情况，如果是当天则不处理，否则退出计数
                    if (i > 0) break;
                } else {
                    signCount++;
                }
                //最低位前进一天
                sign >>= 1;
            }
        }
        return signCount;
    }

    @Override
    public TreeMap<String, Long> currentMonthSign(TbUser user) {
        String key = this.getKey(user);
        TreeMap<String, Long> signMap = new TreeMap<>();
        LocalDate localDate = LocalDate.now();
        long mask = 0b1;
        //用bitfield命令取出第一天到当月最后一天的数据
        List<Long> signList = (List<Long>) redisTemplate.execute(new RedisCallback<List<Long>>() {
            @Override
            public List<Long> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitField(
                        key.getBytes(),
                        BitFieldSubCommands
                                .create()
                                .get(BitFieldSubCommands.BitFieldType.unsigned(localDate.lengthOfMonth()))
                                .valueAt(0)
                );
            }
        });
        if (!CollectionUtils.isEmpty(signList)) {
            long sign = signList.get(0) == null ? 0 : signList.get(0);
            for (int i = localDate.lengthOfMonth(); i > 0; i--) {
                //从最后一天往前算
                LocalDate d = localDate.withDayOfMonth(i);
                //放入时间和最后一位签到记录
                signMap.put(dateFormatter.format(d), sign & mask);
                //最低位前进一天
                sign >>= 1;
            }
        }
        return signMap;
    }

    @Override
    public TreeMap<String, Integer> currentMonthSignOtherWay(TbUser user) {
        String key = this.getKey(user);
        TreeMap<String, Integer> map = new TreeMap<>();
        LocalDate localDate = LocalDate.now();
        int monthLength = localDate.lengthOfMonth();
        for (int offset = 0; offset < monthLength; offset++) {
            Boolean bit = redisTemplate.opsForValue().getBit(key, offset);
            int signFlag = Boolean.TRUE.equals(bit) ? 1 : 0;
            LocalDate d = localDate.withDayOfMonth(offset + 1);
            map.put(dateFormatter.format(d), signFlag);
        }
        return map;
    }
}
