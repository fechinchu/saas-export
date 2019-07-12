package com.fechin.service.packing.impl;

import com.fechin.dao.cargo.ShippingOrderDao;
import com.fechin.domain.cargo.ShippingOrder;
import com.fechin.domain.cargo.ShippingOrderExample;
import com.fechin.service.packing.ShippingOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {
    @Autowired
    private ShippingOrderDao shippingOrderDao;

    @Override
    public PageInfo<ShippingOrder> findOnPage(Integer page, Integer size, ShippingOrderExample shippingOrderExample) {
        PageHelper.startPage(page,size);
        List<ShippingOrder> list = shippingOrderDao.selectByExample(shippingOrderExample);
        return new PageInfo<ShippingOrder>(list);

    }

    @Override
    public ShippingOrder findById(String id) {
        return shippingOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ShippingOrder shippingOrder) {
        shippingOrderDao.insertSelective(shippingOrder);
    }

    @Override
    public void update(ShippingOrder shippingOrder) {
        shippingOrderDao.updateByPrimaryKeySelective(shippingOrder);
    }

    @Override
    public void delete(String id) {
        shippingOrderDao.deleteByPrimaryKey(id);
    }


}
