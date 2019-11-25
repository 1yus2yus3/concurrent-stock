package com.cola.concurrentstock.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_order")
public class ProductOrder {
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    @TableField("order_name")
    private String orderName;
    @TableField("product_id")
    private Long productId;
    @TableField("product_name")
    private String productName;
}
