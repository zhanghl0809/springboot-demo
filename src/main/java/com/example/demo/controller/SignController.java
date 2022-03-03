package com.example.demo.controller;


import com.example.demo.common.annotations.UseToken;
import com.example.demo.common.result.ApiResult;
import com.example.demo.domin.TbUser;
import com.example.demo.service.UserService;
import com.example.demo.service.UserSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.TreeMap;


@UseToken
@Slf4j
@RestController
@RequestMapping("/sign")
public class SignController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSignService userSignService;


    @GetMapping("/userSign")
    public ApiResult<Object> userSign(@RequestParam("userId") Long userId, @RequestParam("day") int day) {
        TbUser user = userService.selectByUserId(userId);
        Boolean signFlag = userSignService.userSign(user, day);
        if (signFlag) {
            return ApiResult.FAIL("您已签过了，不要太贪心哦");
        }
        return ApiResult.SUCCESS();
    }

    /**
     * 查询某天的签到情况
     * @param day
     * @return
     */
    @GetMapping("getDaySign")
    public ApiResult<Object> getDaySign(@RequestParam("userId") Long userId, @RequestParam("day") int day){
        TbUser user = userService.selectByUserId(userId);
        Integer dayIsSign = userSignService.getOneDayIsSign(user, day);
        return ApiResult.SUCCESS(dayIsSign);
    }

    /**
     * 当月总签到次数
     * @return
     */
    @GetMapping("monthTotalCount")
    public ApiResult<Object> monthTotalCount(@RequestParam("userId") Long userId) {
        TbUser user = userService.selectByUserId(userId);
        Long count = userSignService.getMonthSignTotalCount(user);
        return ApiResult.SUCCESS(count);
    }

    /**
     * 查看连续签到天数
     * @return
     */
    @GetMapping("continuousDays")
    public ApiResult<Object> continuousDays(@RequestParam("userId") Long userId) {
        TbUser user = userService.selectByUserId(userId);
        Integer continuousSignDays = userSignService.getMonthContinuousSignDays(user);
        return ApiResult.SUCCESS(continuousSignDays);
    }

    /**
     * 当月签到日历
     * @return
     */
    @GetMapping("currentMonthSign")
    public ApiResult<Object> currentMonthSign(@RequestParam("userId") Long userId) {
        TbUser user = userService.selectByUserId(userId);
        TreeMap<String, Long> map = userSignService.currentMonthSign(user);
        return ApiResult.SUCCESS(map);
    }

}


