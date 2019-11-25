package com.cola.concurrentstock.base.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("product_stock")
public class ProductStock {

    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    @TableField("product_name")
    private String productName;
    private Integer stock;
    //@Version
    private Integer versionNo;
}
