### 并发在很多实际生产的项目中会面临到，并不是说系统小，用户量少就不会存在并发问题。所以对并发的处理和解决思路是每一步开发必须考虑的问题；
下面我从实际项目中剥离出的一个场景做个总结。一方面加深自己的理解，另一方面可以分享给大家。
#### 系统中有一批爆款产品，在618中年庆活动中售卖。前面页面展示产品，用户选择产品下单，即完成操作。暂且忽略支付环境

## NO.1 synchronized 同步代码锁 修饰代码块
```
@Transactional
@Override
public synchronized ProductOrder commitProductOrder(Long productId) {
        ProductStock productStock = productStockMapper.selectById(productId);
        //库存已卖完
        if(productStock == null || productStock.getStock() == 0) {
            return null;
        }
        //假定一次只卖一件
        productStock.setStock(productStock.getStock() - 1);
        if(productStockMapper.updateById(productStock) == 0){
            //数据已过期=>重新执行售卖逻辑
            logger.error("数据已过期=>重新执行售卖逻辑,{}",productId);
            return null;
        }
        //产生订单
        ProductOrder productOrder = new ProductOrder();
        productOrder.setOrderName("成功售卖一件" + productStock.getProductName());
        productOrder.setProductId(productStock.getId());
        productOrder.setProductName(productStock.getProductName());
        productOrderMapper.insert(productOrder);
        return productOrder;
}
```
观察上面的代码其实没有什么问题，但是实际模拟并发就会发现，实际库存和预计的存在差别。
