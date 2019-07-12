package com.fechin.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.cargo.ContractDao;
import com.fechin.dao.cargo.ContractProductDao;
import com.fechin.dao.cargo.ExtCproductDao;
import com.fechin.domain.cargo.Contract;
import com.fechin.domain.cargo.ContractProduct;
import com.fechin.domain.cargo.ContractProductExample;
import com.fechin.domain.cargo.ExtCproduct;
import com.fechin.service.cargo.ContractProductService;
import com.fechin.service.cargo.ContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("all")
@Service
public class ContractProductServcieImpl implements ContractProductService {
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ContractService contractService;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public PageInfo<ContractProduct> findByContractIdOnPages(ContractProductExample example, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(example);
        PageInfo<ContractProduct> pageInfo = new PageInfo<>(contractProducts);
        return pageInfo;
    }

    @Override
    public void save(ContractProduct contractProduct) {
        //Long boxNum = contractProduct.getBoxNum();
        //获取单价
        contractProduct.setId(UUID.randomUUID().toString().replace("-", ""));
        BigDecimal price = contractProduct.getPrice();
        //获取数量

        Long cnumber = contractProduct.getCnumber();
        BigDecimal amountB = new BigDecimal(0);
        if (price != null && cnumber != null) {
            //只有两个都不为空,才进行如下的操作
            BigDecimal cnumberB = new BigDecimal(cnumber);
            amountB = cnumberB.multiply(price);
        }
        contractProduct.setAmount(amountB);
        //接着需要与该商品有关的合同的数据
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //查完合同数据之后,获取合同总价,相加
        BigDecimal totalAmount = contract.getTotalAmount();
        if (totalAmount == null) {
            totalAmount = new BigDecimal(0);
        }
        BigDecimal addTotalAmount = totalAmount.add(amountB);
        //获取合同的商品Num,加一
        Integer proNum = contract.getProNum();
        int i = proNum + 1;
        //获取该合同的id
        String id = contract.getId();
        //新建一份合同,selective进行更新
        Contract contract1 = new Contract();
        contract1.setId(id);
        contract1.setProNum(i);
        contract1.setTotalAmount(addTotalAmount);
        //根据已有的数据进行更新
        contractDao.updateByPrimaryKeySelective(contract1);
        contractProductDao.insertSelective(contractProduct);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //获取价格
        BigDecimal newPrice = contractProduct.getPrice();
        //获取数量
        Long cnumberNew1 = contractProduct.getCnumber();

        BigDecimal newAmount = new BigDecimal(0);
        if (newPrice != null && cnumberNew1 != null) {
            //计算出新的数据
            BigDecimal newCnumber = new BigDecimal(cnumberNew1);
            newAmount = newCnumber.multiply(newPrice);
        }
        //首先查出原来数据库中的关于该产品的数据
        String contractProductOldId = contractProduct.getId();
        ContractProduct productOld = contractProductDao.selectByPrimaryKey(contractProductOldId);

        BigDecimal oldAmount = productOld.getAmount();
        BigDecimal oldPrice = productOld.getPrice();
        Long oldCnumber = productOld.getCnumber();
        /*
        新的数据:newPrice,newCnumber,newAmount
        旧的数据:oldPrice,oldCnumber,oldAmount
         */
        //接着需要与该商品有关的合同的数据
        String contractId = contractProduct.getContractId();
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

        //对货物的总价格进行更新
        contractProduct.setAmount(newAmount);
        //根据已有的数据进行更新
        contractDao.updateByPrimaryKeySelective(contract1);
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
    }

    @Override
    public void delete(String id, String contractId) {
        //根据id查询contractProduct
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);

        //获取到它的附件
        List<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
        BigDecimal tempAmount = new BigDecimal(0);
        for (ExtCproduct extCproduct : extCproducts) {
            BigDecimal amount = extCproduct.getAmount();
            //每循环一次,就加一次
            tempAmount = tempAmount.add(amount);
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }
        //总的被删除价格
        BigDecimal totalAmountWillBeDeleted = tempAmount.add(contractProduct.getAmount());

        //接着需要与该商品有关的合同的数据
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        BigDecimal oldTotalAmount = contract.getTotalAmount();
        Integer oldProNum = contract.getProNum();
        Integer oldExtNum = contract.getExtNum();

        //新建一份合同,selective进行更新
        Contract contract1 = new Contract();
        contract1.setId(contractId);
        contract1.setTotalAmount(oldTotalAmount.subtract(totalAmountWillBeDeleted));
        contract1.setProNum(oldProNum - 1);
        contract1.setExtNum(oldExtNum - extCproducts.size());

        //对合同进行更新
        contractDao.updateByPrimaryKeySelective(contract1);
        //删除该货物
        contractProductDao.deleteByPrimaryKey(id);
    }
}
