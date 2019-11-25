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

        ProductOrder order = productOrderDBService.commitProductOrder(productId);

        return order;
    }
}
