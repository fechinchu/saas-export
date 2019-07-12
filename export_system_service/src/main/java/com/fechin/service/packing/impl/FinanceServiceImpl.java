package com.fechin.service.packing.impl;

import com.fechin.dao.cargo.FinanceDao;
import com.fechin.domain.cargo.Finance;
import com.fechin.domain.cargo.FinanceExample;
import com.fechin.service.packing.FinanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private FinanceDao financeDao;

    @Override
    public PageInfo<Finance> findOnPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Finance> finances = financeDao.selectByExample(new FinanceExample());
        return new PageInfo<Finance>(finances);
    }

    @Override
    public Finance findById(String id) {
        return financeDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Finance finance) {
        financeDao.insertSelective(finance);
    }

    @Override
    public void update(Finance finance) {
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Override
    public void delete(String id) {
        financeDao.deleteByPrimaryKey(id);
    }

    @Override
    public void submit(String id) {
        Finance finance = new Finance();
        finance.setFinanceId(id);
        finance.setState(1);
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Override
    public void cancel(String id) {
        Finance finance = new Finance();
        finance.setFinanceId(id);
        finance.setState(0);
        financeDao.updateByPrimaryKeySelective(finance);
    }
}
