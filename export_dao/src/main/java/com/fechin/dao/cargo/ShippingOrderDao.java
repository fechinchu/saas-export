package com.fechin.dao.cargo;

import com.fechin.domain.cargo.ShippingOrder;
import com.fechin.domain.cargo.ShippingOrderExample;

import java.util.List;

public interface ShippingOrderDao {
    int deleteByPrimaryKey(String shippingOrderId);

    int insert(ShippingOrder record);

    int insertSelective(ShippingOrder record);

    List<ShippingOrder> selectByExample(ShippingOrderExample example);

    ShippingOrder selectByPrimaryKey(String shippingOrderId);

    int updateByPrimaryKeySelective(ShippingOrder record);

    int updateByPrimaryKey(ShippingOrder record);


}