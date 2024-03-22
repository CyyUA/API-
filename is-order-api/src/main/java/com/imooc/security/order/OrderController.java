package com.imooc.security.order;

import com.imooc.security.server.resource.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private RestTemplate restTemplate = new RestTemplate();
    @PostMapping
    public OrderInfo creat(@RequestBody OrderInfo info , @AuthenticationPrincipal User user){  //拿到用户名  userdetailservice实现类返回什么类型就写社么类型
        log.info("userID is " + user.getId());
       // PriceInfo price = restTemplate.getForObject("http://localhost:9080/prices/"+ info.getProductId(),PriceInfo.class);
       // log.info("price is" + price.getPrice());
        return info;
    }

    @GetMapping("{id}")
    public OrderInfo getInfo(@PathVariable Long id){
        log.info("orderId is" + id);
        return new OrderInfo();
    }
}
