package com.cola.concurrentstock.db;

import com.cola.concurrentstock.base.mapper.ProductOrderMapper;
import com.cola.concurrentstock.base.mapper.ProductStockMapper;
import com.cola.concurrentstock.base.model.ProductOrder;
import com.cola.concurrentstock.base.model.ProductStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ProductOrderDBServiceImpl implements ProductOrderDBService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    ProductStockMapper productStockMapper;
    @Resource
    ProductOrderMapper productOrderMapper;

    private int count=0;


    @Override
    public synchronized ProductOrder commitProductOrder(Long productId) {
        count++;
        System.out.println("============"+count);
        ProductStock productStock = productStockMapper.selectById(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return null;
        }
        //假定一次只卖一件
        productStock.setStock(productStock.getStock() - 1);
        //乐观锁 update . set version_no = version_no+1 where .. and version_no = version_no
        if(productStockMapper.updateById(productStock) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return null;
            //commitProductOrder(productId);
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return productOrder;
    }

    @Override
    public ProductOrder commitProductOrder2(Long productId) {

        ProductStock productStock = productStockMapper.selectById(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return null;
        }
        //假定一次只卖一件
        //乐观锁 update . set version_no = version_no+1 where .. and version_no = version_no
        if(productStockMapper.updateStockById(productId) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return null;
            //commitProductOrder(productId);
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return productOrder;

    }

    @Override
    public ProductOrder commitProductOrder3(Long productId) {
        ProductStock productStock = productStockMapper.selectByIdForUpdate(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return null;
        }
        //假定一次只卖一件
        //悲观锁
        productStock.setStock(productStock.getStock()-1);
        if(productStockMapper.updateById(productStock) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return null;
            //commitProductOrder(productId);
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return productOrder;
    }
}
