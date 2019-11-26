package com.cola.concurrentstock.db;

import com.cola.concurrentstock.base.model.ProductOrder;

public interface ProductOrderDBService {

    ProductOrder commitProductOrder(Long productId);

    ProductOrder commitProductOrder2(Long productId);

    ProductOrder commitProductOrder3(Long productId);
}
