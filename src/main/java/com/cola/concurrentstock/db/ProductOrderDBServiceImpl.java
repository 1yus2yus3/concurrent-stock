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
@Transactional
@Service
public class ProductOrderDBServiceImpl implements ProductOrderDBService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    ProductStockMapper productStockMapper;
    @Resource
    ProductOrderMapper productOrderMapper;


    /***
     * 同步代码锁：使用的方法只允许一个线程持有锁。从代码层面隔离了共享资源的竞争，效率比较低，分布式环境中存在并发安全问题
     * @param productId
     * @return
     */

    @Override
    public void commitProductOrder(Long productId) {
            ProductStock productStock = productStockMapper.selectById(productId);
            //库存已卖完
            if(productStock == null || productStock.getStock() == 0) {
                return ;
            }
            //假定一次只卖一件
            productStock.setStock(productStock.getStock() - 1);
            if(productStockMapper.updateById(productStock) == 0){
                //数据已过期=>重新执行售卖逻辑
                logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
                return ;
            }
            //产生订单
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
            productOrder.setProductId(productStock.getId());
            productOrder.setProductName(productStock.getProductName());
            productOrderMapper.insert(productOrder);
            return ;
    }
    /***
     * 乐观锁适用于写少读多的情景，因为这种乐观锁相当于JAVA的CAS，所以多条数据同时过来的时候，不用等待，可以立即进行返回。
     * @param productId
     * @return
     */
    @Override
    public void commitProductOrder1(Long productId) {

        ProductStock productStock = productStockMapper.selectById(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return ;
        }
        //假定一次只卖一件
        productStock.setStock(productStock.getStock() - 1);
        //乐观锁 update . set version_no = version_no+1 where .. and version_no = version_no
        if(productStockMapper.updateById(productStock) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return ;
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return ;
    }

    @Override
    public void commitProductOrder2(Long productId) {

        ProductStock productStock = productStockMapper.selectById(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return ;
        }
        //假定一次只卖一件
        //乐观锁 update . set version_no = version_no+1 where .. and version_no = version_no
        if(productStockMapper.updateStockById(productId) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return ;
            //commitProductOrder(productId);
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return ;

    }

    /***
     * 悲观锁适用于写多读少的情景，这种情况也相当于JAVA的synchronized，reentrantLock等，大量数据过来的时候，只有一条数据可以被写入，其他的数据需要等待。执行完成后下一条数据可以继续
     * 非主键不含索引（name）进行查询，并且查询到数据，name字段产生表锁
     * @param productId
     * @return
     */
    @Override
    public void commitProductOrder3(Long productId) {
        ProductStock productStock = productStockMapper.selectByIdForUpdate(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return ;
        }
        //假定一次只卖一件
        //悲观锁
        productStock.setStock(productStock.getStock()-1);
        if(productStockMapper.updateById(productStock) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return ;
            //commitProductOrder(productId);
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return ;
    }
}
