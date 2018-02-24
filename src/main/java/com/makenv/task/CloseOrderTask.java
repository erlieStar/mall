package com.makenv.task;

import com.makenv.service.OrderService;
import com.makenv.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CloseOrderTask {

    @Autowired
    private OrderService orderService;

    //@Scheduled(cron = "0 */1 * * * ?")//每一分钟执行一次
    @Scheduled(cron = "0/1 * * * * ?")//调试使用，每一秒执行一次
    public void closeOrderTaskV1() {
        System.out.println("执行");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
        orderService.closeOrder(hour);
    }

}
