package com.example.demo.service;

import com.example.demo.domin.TbUser;

import java.util.TreeMap;

public interface UserSignService {

    /**
     * 用户签到
     * @param user u
     * @return boolean
     */
    Boolean userSign(TbUser user, int day);

    /**
     * 查询某一天是否签到
     * @param user u
     * @param day day
     * @return boolean
     */
    Integer getOneDayIsSign(TbUser user, int day);

    /**
     * 查询当前月份签到总次数
     * @param user user
     * @return Long
     */
    Long getMonthSignTotalCount(TbUser user);

    /**
     * 查询当前月份连续签到总次数
     * @param user user
     * @return boolean
     */
    Integer getMonthContinuousSignDays(TbUser user);

    /**
     * 查询当前月份签到日历
     * @param user
     * @return boolean
     */
    TreeMap<String,Long> currentMonthSign(TbUser user);

    /**
     * 查询当前月份签到日历
     * @param user
     * @return boolean
     */
    TreeMap<String,Integer> currentMonthSignOtherWay(TbUser user);


}
