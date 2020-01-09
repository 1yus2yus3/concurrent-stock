package com.cola.concurrentstock;

import com.cola.concurrentstock.base.mapper.ProductStockMapper;
import com.cola.concurrentstock.base.model.ProductStock;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@MapperScan("com.cola.concurrentstock.base.mapper")
class ConcurrentStockApplicationTests {

    @Resource
    private ProductStockMapper productStockMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<ProductStock> userList = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            ProductStock stock = new ProductStock();
            stock.setStock(100000);
            stock.setProductName("产品"+i);
            productStockMapper.insert(stock);
        }
    }



    @Test
    public void test(){

    }
}
