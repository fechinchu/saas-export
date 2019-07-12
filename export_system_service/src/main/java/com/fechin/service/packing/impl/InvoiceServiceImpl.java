package com.fechin.service.packing.impl;

import com.fechin.dao.cargo.InvoiceDao;
import com.fechin.domain.cargo.Invoice;
import com.fechin.domain.cargo.InvoiceExample;
import com.fechin.service.packing.InvoiceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;

    @Override
    public PageInfo<Invoice> findOnPage(Integer page, Integer size, InvoiceExample invoiceExample) {
        PageHelper.startPage(page,size);
        List<Invoice> invoices = invoiceDao.selectByExample(invoiceExample);
        return new PageInfo<Invoice>(invoices);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Invoice invoice) {
        invoiceDao.insertSelective(invoice);
    }

    @Override
    public void update(Invoice invoice) {
        invoiceDao.updateByPrimaryKeySelective(invoice);
    }

    @Override
    public void delete(String id) {
        invoiceDao.deleteByPrimaryKey(id);
    }

    @Override
    public void submit(String id) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(id);
        invoice.setState(1);
        invoiceDao.updateByPrimaryKeySelective(invoice);
    }

    @Override
    public void cancel(String id) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(id);
        invoice.setState(0);
        invoiceDao.updateByPrimaryKeySelective(invoice);
    }
}
