package com.fechin.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.cargo.ContractDao;
import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ContractExample;
import com.fechin.domain.vo.ContractAndProductVo;
import com.fechin.service.cargo.ContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo<Contract> findByCompanyIdOnPages(Integer page, Integer size, ContractExample contractExample) {
        PageHelper.startPage(page,size);
        List<Contract> list =contractDao.selectByExample(contractExample);
        PageInfo<Contract> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void add(Contract contract) {
        contract.setCreateTime(new Date());
        contract.setUpdateTime(new Date());

        contract.setId(UUID.randomUUID().toString().replace("-",""));
        BigDecimal bigDecimal = new BigDecimal(0);
        contract.setTotalAmount(bigDecimal);
        contract.setState(0);
        contract.setProNum(0);
        contract.setExtNum(0);

        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contract.setUpdateTime(new Date());
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public Contract findByCompanyIdAndId(ContractExample contractExample) {
        //根据条件查询默认使用的是list集合
        return contractDao.selectByExample(contractExample).get(0);
    }

    @Override
    public void deleteById(String id) {
        contractDao.deleteByPrimaryKey(id);
    }

    @Override
    public void submitById(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void cancelById(String id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public List<ContractAndProductVo> findByCompanyIdAndTime(String companyId, String inputDate) {
        return contractDao.findByCompanyIdAndTime(companyId,inputDate);
    }

}
