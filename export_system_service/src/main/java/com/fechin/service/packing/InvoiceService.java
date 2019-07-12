package com.fechin.service.packing;

import com.fechin.domain.cargo.Invoice;
import com.fechin.domain.cargo.InvoiceExample;
import com.github.pagehelper.PageInfo;

public interface InvoiceService {
    PageInfo<Invoice> findOnPage(Integer page, Integer size, InvoiceExample invoiceExample);

    Invoice findById(String id);

    void save(Invoice invoice);

    void update(Invoice invoice);

    void delete(String id);

    void submit(String id);

    void cancel(String id);
}
