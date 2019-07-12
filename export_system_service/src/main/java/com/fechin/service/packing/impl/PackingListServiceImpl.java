package com.fechin.service.packing.impl;

import com.fechin.dao.cargo.FinanceDao;
import com.fechin.dao.cargo.InvoiceDao;
import com.fechin.dao.cargo.PackingListDao;
import com.fechin.dao.cargo.ShippingOrderDao;
import com.fechin.domain.cargo.PackingList;
import com.fechin.domain.cargo.PackingListExample;
import com.fechin.service.packing.PackingListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackingListServiceImpl implements PackingListService {
    @Autowired
    private PackingListDao packingListDao;
    @Autowired
    private FinanceDao financeDao;
    @Autowired
    private ShippingOrderDao shippingOrderDao;
    @Autowired
    private InvoiceDao invoiceDao;

    @Override
    public void packing(PackingList packingList) {

        packingListDao.insertSelective(packingList);
        //共享主键
        /*Finance finance = new Finance();
        finance.setFinanceId(s);
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(s);
        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setShippingOrderId(s);*/

    }

    @Override
    public PageInfo<PackingList> findAllOnPage(Integer page, Integer size, PackingListExample packingListExample) {
        PageHelper.startPage(page,size);
        List<PackingList> packingLists = packingListDao.selectByExample(packingListExample);
        return new PageInfo<PackingList>(packingLists);
    }

    @Override
    public void update(PackingList packingList) {
        packingListDao.updateByPrimaryKeySelective(packingList);
    }

    @Override
    public PackingList findById(String id) {
        return packingListDao.selectByPrimaryKey(id);
    }

    @Override
    public void delete(String id) {
        packingListDao.deleteByPrimaryKey(id);
    }

    @Override
    public void submit(String id) {
        PackingList packingList = new PackingList();
        packingList.setPackingListId(id);
        packingList.setState(1L);
        packingListDao.updateByPrimaryKeySelective(packingList);
    }

    @Override
    public void cancel(String id) {
        PackingList packingList = new PackingList();
        packingList.setPackingListId(id);
        packingList.setState(0L);
        packingListDao.updateByPrimaryKeySelective(packingList);
    }
}
