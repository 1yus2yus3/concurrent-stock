package com.cola.concurrentstock.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cola.concurrentstock.base.model.ProductStock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductStockMapper extends BaseMapper<ProductStock> {

    @Update("update product_stock set stock = stock-1 where id= ${productId} and stock > 0")
    int updateStockById(@Param("productId") Long productId);

    @Select("SELECT * FROM product_stock where id=${productId} for update")
    ProductStock selectByIdForUpdate(@Param("productId") Long productId);

}
