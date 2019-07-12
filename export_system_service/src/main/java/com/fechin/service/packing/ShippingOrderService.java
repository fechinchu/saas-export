package com.fechin.service.packing;

import com.fechin.domain.cargo.ShippingOrder;
import com.fechin.domain.cargo.ShippingOrderExample;
import com.github.pagehelper.PageInfo;

public interface ShippingOrderService {
    PageInfo<ShippingOrder> findOnPage(Integer page, Integer size, ShippingOrderExample shippingOrderExample);

    ShippingOrder findById(String id);

    void save(ShippingOrder shippingOrder);

    void update(ShippingOrder shippingOrder);

    void delete(String id);



}
