package com.fechin.service.export.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.fechin.dao.export.ExportProductDao;
import com.fechin.domain.export.ExportProduct;
import com.fechin.domain.export.ExportProductExample;
import com.fechin.service.export.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExportProductServiceImpl implements ExportProductService {
    @Autowired
    private ExportProductDao exportProductDao;

    @Override
    public List<ExportProduct> findByExportId(String id) {
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        return exportProductDao.selectByExample(exportProductExample);
    }

    @Override
    public void updateExportProduct(ExportProduct exportProduct) {
        exportProductDao.updateByPrimaryKeySelective(exportProduct);
    }
}
