package com.cola.concurrentstock.db;

import com.cola.concurrentstock.base.model.ProductOrder;

public interface ProductOrderDBService {

    ProductOrder commitProductOrder(Long productId);
}
