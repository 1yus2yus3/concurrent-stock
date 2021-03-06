package com.cola.concurrentstock.controller;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.cola.concurrentstock.base.model.ProductOrder;
import com.cola.concurrentstock.db.ProductOrderDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductOrderDBService productOrderDBService;

    @PostMapping("db/commitProductOrder")
    public void commitProductOrder(@RequestParam(required = false,defaultValue = "2") Long productId){

        synchronized (this) {
            productOrderDBService.commitProductOrder(productId);
        }
    }
    @PostMapping("db/commitProductOrder1")
    public void commitProductOrder1(@RequestParam(required = false,defaultValue = "2") Long productId){

        try{
            productOrderDBService.commitProductOrder1(productId);
            return ;
        }catch (Exception e){
            logger.error("异常：{}",e);
            throw new ApiException(e);
        }
    }
    @PostMapping("db/commitProductOrder2")
    public void commitProductOrder2(@RequestParam(required = false,defaultValue = "2") Long productId){

        productOrderDBService.commitProductOrder2(productId);

        return ;
    }

    @PostMapping("db/commitProductOrder3")
    public void commitProductOrder3(@RequestParam(required = false,defaultValue = "2") Long productId){

        productOrderDBService.commitProductOrder3(productId);

        return ;
    }
}
