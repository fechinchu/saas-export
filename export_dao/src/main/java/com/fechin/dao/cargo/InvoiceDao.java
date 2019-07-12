package com.fechin.dao.cargo;

import com.fechin.domain.cargo.Invoice;
import com.fechin.domain.cargo.InvoiceExample;

import java.util.List;

public interface InvoiceDao {
    int deleteByPrimaryKey(String invoiceId);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    List<Invoice> selectByExample(InvoiceExample example);

    Invoice selectByPrimaryKey(String invoiceId);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);
}