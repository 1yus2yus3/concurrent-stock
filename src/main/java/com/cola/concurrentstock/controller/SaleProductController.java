package com.cola.concurrentstock.controller;

import com.cola.concurrentstock.base.model.ProductOrder;
import com.cola.concurrentstock.db.ProductOrderDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleProductController {

    @Autowired
    ProductOrderDBService productOrderDBService;

    @PostMapping("db/commitProductOrder")
    public ProductOrder commitProductOrder(@RequestParam(required = false,defaultValue = "2") Long productId){

        synchronized (this) {
            ProductOrder order = productOrderDBService.commitProductOrder(productId);
            return order;
        }
    }
    @PostMapping("db/commitProductOrder1")
    public ProductOrder commitProductOrder1(@RequestParam(required = false,defaultValue = "2") Long productId){

        ProductOrder order = productOrderDBService.commitProductOrder1(productId);

        return order;
    }
    @PostMapping("db/commitProductOrder2")
    public ProductOrder commitProductOrder2(@RequestParam(required = false,defaultValue = "2") Long productId){

        ProductOrder order = productOrderDBService.commitProductOrder2(productId);

        return order;
    }

    @PostMapping("db/commitProductOrder3")
    public ProductOrder commitProductOrder3(@RequestParam(required = false,defaultValue = "2") Long productId){

        ProductOrder order = productOrderDBService.commitProductOrder3(productId);

        return order;
    }
}
