package com.cola.concurrentstock.db;

import com.cola.concurrentstock.base.model.ProductOrder;

public interface ProductOrderDBService {

    void commitProductOrder(Long productId);

    void commitProductOrder1(Long productId);

    void commitProductOrder2(Long productId);

    void commitProductOrder3(Long productId);
}
