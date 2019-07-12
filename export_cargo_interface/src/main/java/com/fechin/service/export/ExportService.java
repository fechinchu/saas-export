package com.fechin.service.export;

import com.fechin.domain.export.Export;
import com.fechin.domain.export.ExportExample;
import com.github.pagehelper.PageInfo;

public interface ExportService {

    void saveExport(Export export);

    PageInfo<Export> findAllOnPage(ExportExample exportExample, Integer page, Integer size);

    Export findById(String id);

    void update(Export export);

    void exportE(String id);
}
