package com.fechin.service.export.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.cargo.ContractDao;
import com.fechin.dao.cargo.ContractProductDao;
import com.fechin.dao.cargo.ExtCproductDao;
import com.fechin.dao.export.ExportDao;
import com.fechin.dao.export.ExportProductDao;
import com.fechin.dao.export.ExtEproductDao;
import com.fechin.domain.cargo.*;
import com.fechin.domain.export.*;
import com.fechin.domain.vo.ExportProductResult;
import com.fechin.domain.vo.ExportProductVo;
import com.fechin.domain.vo.ExportResult;
import com.fechin.domain.vo.ExportVo;
import com.fechin.service.export.ExportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private ExportProductDao exportProductDao;
    @Autowired
    private ExtEproductDao extEproductDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ExtCproductDao extCproductDao;

    @Override
    public void saveExport(Export export) {
        String contractIds = export.getContractIds();

        String[] contractIdArray = contractIds.split(",");
        //需要将原合同的状态改掉
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria2 = contractExample.createCriteria();
        criteria2.andIdIn(Arrays.asList(contractIdArray));
        List<Contract> contracts = contractDao.selectByExample(contractExample);

        //处理export
        Integer proNum = 0;
        Integer extNum = 0;
        for (Contract contract : contracts) {
            //改变contract的状态
            contract.setState(2);
            proNum += contract.getProNum();
            extNum += contract.getExtNum();
            //对contract进行更新
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //将export存放在数据库
        export.setId(UUID.randomUUID().toString().replace("-", ""));
        export.setCreateTime(new Date());
        export.setProNum(proNum);
        export.setExtNum(extNum);
        export.setState(0L);
        exportDao.insertSelective(export);

        //可以根据contractId来查询出货物和附件
        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdIn(Arrays.asList(contractIdArray));
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(example);

        for (ContractProduct contractProduct : contractProductList) {
            //在循环contractProduct的过程中创建exportProduct对象,并将这些对象存放在数据库中
            ExportProduct exportProduct = new ExportProduct();
            BeanUtils.copyProperties(contractProduct,exportProduct);
            //给contractProduct指定ID和export的id(外键)
            exportProduct.setId(UUID.randomUUID().toString().replace("-",""));
            exportProduct.setExportId(export.getId());
            //将exportProduct存入数据库
            exportProductDao.insertSelective(exportProduct);

            //根据contractProductId去数据库查询他的附属品
            ExtCproductExample extCproductExample = new ExtCproductExample();
            ExtCproductExample.Criteria criteria1 = extCproductExample.createCriteria();
            criteria1.andContractProductIdEqualTo(contractProduct.getId());
            List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);

            for (ExtCproduct extCproduct : extCproducts) {
                ExtEproduct extEproduct = new ExtEproduct();
                BeanUtils.copyProperties(extCproduct,extEproduct);
                extEproduct.setId(UUID.randomUUID().toString().replace("-",""));
                extEproduct.setExportProductId(exportProduct.getId());
                extEproduct.setExportId(export.getId());
                extEproductDao.insertSelective(extEproduct);
            }

        }

    }

    @Override
    public PageInfo<Export> findAllOnPage(ExportExample exportExample, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Export> exports = exportDao.selectByExample(exportExample);
        return new PageInfo<Export>(exports);
    }

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Export export) {
        exportDao.updateByPrimaryKeySelective(export);
    }

    @Override
    public void exportE(String id) {
        //进行电子报运
        //1.首先根据id将报运单和货物查询出来
        Export export = exportDao.selectByPrimaryKey(id);

        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProducts = exportProductDao.selectByExample(exportProductExample);
        //将报运单中的数据进行赋值
        ExportVo exportVo = new ExportVo();
        BeanUtils.copyProperties(export, exportVo);
        exportVo.setExportId(id);

        //将查询出来的exportProducts数据封装到exportProductVo
        List<ExportProductVo> exportDaos = new ArrayList<>();
        for (ExportProduct exportProduct : exportProducts) {
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            //由于数据类型不相同,部分数据需要手动赋值
            exportProductVo.setCnumber(Math.toIntExact(exportProduct.getCnumber()!=null?exportProduct.getCnumber():0));
            exportProductVo.setBoxNum(Math.toIntExact(exportProduct.getBoxNum()!=null?exportProduct.getBoxNum():0));
            exportProductVo.setGrossWeight(exportProduct.getGrossWeight()!=null?exportProduct.getGrossWeight().doubleValue():null);
            exportProductVo.setNetWeight(exportProduct.getNetWeight()!=null?exportProduct.getNetWeight().doubleValue():null);
            exportProductVo.setSizeLength(exportProduct.getSizeLength()!=null?exportProduct.getSizeLength().doubleValue():null);
            exportProductVo.setSizeWidth(exportProduct.getSizeWidth()!=null?exportProduct.getSizeWidth().doubleValue():null);
            exportProductVo.setSizeHeight(exportProduct.getSizeHeight()!=null?exportProduct.getSizeHeight().doubleValue():null);
            exportProductVo.setExPrice(exportProduct.getExPrice()!=null?exportProduct.getExPrice().doubleValue():null);
            exportProductVo.setPrice(exportProduct.getPrice()!=null?exportProduct.getPrice().doubleValue():null);
            exportProductVo.setTax(exportProduct.getTax()!=null?exportProduct.getTax().doubleValue():null);
            exportProductVo.setOrderNo(Math.toIntExact(exportProduct.getOrderNo()!=null?exportProduct.getOrderNo():0));
            exportProductVo.setExportId(id);
            exportProductVo.setExportProductId(exportProduct.getId());
            exportDaos.add(exportProductVo);
        }
        exportVo.setProducts(exportDaos);
        //调用webservice
        WebClient.create("http://localhost:9090/ws/export/user")
                .type(MediaType.APPLICATION_XML_TYPE).post(exportVo);

        ExportResult exportResult = WebClient.create("http://localhost:9090/ws/export/user/" + id)
                .type(MediaType.APPLICATION_XML_TYPE).get(ExportResult.class);
        //将exportResult封装到自己的javaBean中,并保存到数据库中
        Set<ExportProductResult> products = exportResult.getProducts();
        for (ExportProductResult product : products) {
            String exportProductId = product.getExportProductId();
            Double tax = product.getTax();
            ExportProduct exportProduct = new ExportProduct();
            exportProduct.setId(exportProductId);
            exportProduct.setTax(new BigDecimal(tax));
            //将exportProduct存入数据库
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }
        //修改export的状态
        Export export1 = new Export();
        export1.setId(id);
        export1.setState(2L);
        exportDao.updateByPrimaryKeySelective(export1);

    }
}
