package com.fechin.service.export;

import com.fechin.domain.export.ExportProduct;

import java.util.List;

public interface ExportProductService {
    List<ExportProduct> findByExportId(String id);

    void updateExportProduct(ExportProduct exportProduct);
}
