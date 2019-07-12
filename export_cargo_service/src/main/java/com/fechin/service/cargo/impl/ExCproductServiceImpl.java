package com.fechin.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.cargo.ContractDao;
import com.fechin.dao.cargo.ExtCproductDao;
import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ExtCproduct;
import com.fechin.domain.cargo.ExtCproductExample;
import com.fechin.service.cargo.ExtCproductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("all")
public class ExCproductServiceImpl implements ExtCproductService {
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo<ExtCproduct> findOnPage(ExtCproductExample extCproductExample, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        PageInfo<ExtCproduct> pageInfo = new PageInfo<>(extCproducts);
        return pageInfo;
    }

    @Override
    public void save(ExtCproduct extCproduct) {
        //保存商品的附件的同时,需要改变该合同的数据,对于该商品目前并不需要做出改变
        extCproduct.setId(UUID.randomUUID().toString().replace("-", ""));
        BigDecimal price = extCproduct.getPrice();
        Long cnumber = extCproduct.getCnumber();
        BigDecimal amountB = new BigDecimal(0);
        if (price != null && cnumber != null) {
            //只有两个都不为空,才进行如下的操作
            BigDecimal cnumberB = new BigDecimal(cnumber);
            amountB = cnumberB.multiply(price);
        }
        extCproduct.setAmount(amountB);
        //接着需要与该商品有关的合同的数据
        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //查完合同数据之后,获取合同总价,相加
        BigDecimal totalAmount = contract.getTotalAmount();
        if (totalAmount == null) {
            totalAmount = new BigDecimal(0);
        }
        BigDecimal addTotalAmount = totalAmount.add(amountB);
        //获取合同的商品Num,加一
        Integer extNum = contract.getExtNum();
        int i = extNum + 1;
        //获取该合同的id
        String id = contract.getId();
        //新建一份合同,selective进行更新
        Contract contract1 = new Contract();
        contract1.setId(id);
        contract1.setExtNum(i);
        contract1.setTotalAmount(addTotalAmount);
        //根据已有的数据进行更新
        contractDao.updateByPrimaryKeySelective(contract1);
        extCproductDao.insertSelective(extCproduct);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        BigDecimal newPrice = extCproduct.getPrice();
        //获取数量
        Long cnumberNew1 = extCproduct.getCnumber();
        BigDecimal newAmount = new BigDecimal(0);
        if (newPrice != null && cnumberNew1 != null) {
            //只有两个都不为空,才进行如下的操作
            //计算出新的数据
            BigDecimal newCnumber = new BigDecimal(cnumberNew1);
            newAmount = newCnumber.multiply(newPrice);
        }
        //首先查出原来数据库中的关于该产品的数据
        String extProductOldId = extCproduct.getId();
        ExtCproduct exProductOld = extCproductDao.selectByPrimaryKey(extProductOldId);

        BigDecimal oldAmount = exProductOld.getAmount();
        BigDecimal oldPrice = exProductOld.getPrice();
        Long oldCnumber = exProductOld.getCnumber();

            /*
            新的数据:newPrice,newCnumber,newAmount
            旧的数据:oldPrice,oldCnumber,oldAmount
             */
        //接着需要与该商品有关的合同的数据
        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //查完合同数据之后,获取合同总价,相加
        BigDecimal totalAmount = contract.getTotalAmount();
        if (totalAmount == null) {
            totalAmount = new BigDecimal(0);
        }
        BigDecimal totalAmountNew = totalAmount.add(newAmount.subtract(oldAmount));
        //修改货物的化,proNum不会进行改变
        //获取该合同的id
        String id = contract.getId();
        //新建一份合同,selective进行更新
        Contract contract1 = new Contract();
        contract1.setId(id);
        contract1.setTotalAmount(totalAmountNew);

        //对附件货物的总价格进行更新
        extCproduct.setAmount(newAmount);
        //根据已有的数据进行更新
        contractDao.updateByPrimaryKeySelective(contract1);
        extCproductDao.updateByPrimaryKeySelective(extCproduct);

    }

    @Override
    public void delete(String id, String contractId, String contractProductId) {
        //首先根据id查询该附件
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        BigDecimal amount = extCproduct.getAmount();

        //接着需要与该商品有关的合同的数据
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //查完合同数据之后,获取合同总价
        BigDecimal totalAmount = contract.getTotalAmount();
        if (totalAmount == null) {
            totalAmount = new BigDecimal(0);
        }
        BigDecimal newTotalAnmout = totalAmount.subtract(amount);
        //获取合同的附件商品Num,减1
        Integer extNum = contract.getExtNum();
        int i = extNum - 1;

        //新建一份合同,selective进行更新
        Contract contract1 = new Contract();
        contract1.setId(contractId);
        contract1.setExtNum(i);
        contract1.setTotalAmount(newTotalAnmout);
        //根据已有的数据进行更新
        contractDao.updateByPrimaryKeySelective(contract1);

        //根据id删除该附件
        extCproductDao.deleteByPrimaryKey(id);

    }
}
