package com.fechin.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fechin.domain.vo.ContractAndProductVo;
import com.fechin.service.cargo.ContractService;
import com.fechin.web.controller.BaseController;
import common.utils.DownloadUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class ContractPrintController extends BaseController {
    @Reference
    private ContractService contractService;

    @RequestMapping(path = "/print", name = "前往打印页面")
    public String print() {
        return "cargo/print/contract-print";
    }

    @RequestMapping(path = "/printExcel", name = "打印报表")
    public void printExcel(String inputDate) throws Exception {
        List<ContractAndProductVo> lists = contractService.findByCompanyIdAndTime(companyId, inputDate);

        //获取模板的路径
        String realPath = session.getServletContext().getRealPath("make/xlsprint/tOUTPRODUCT.xlsx");
        Workbook workbook = new XSSFWorkbook(realPath);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        //获取到标题的单元格,对该单元格进行赋值
        String s = inputDate.replaceAll("-0", "-").replaceAll("-", "年") + "月份出货表";
        cell.setCellValue(s);

        //获取第三行的样式,并将该样式放在数组中
        Row row1 = sheet.getRow(2);
        CellStyle[] cellStyles = new CellStyle[8];
        for (int i = 1; i <=8; i++) {
            cellStyles[i-1] = row1.getCell(i).getCellStyle();
        }

        //获取完样式之后,开始报表赋值,首先定义一个rowIndex,初始值从2开始
        int rowIndex = 2;
        for (ContractAndProductVo list : lists) {
            Row row2 = sheet.createRow(rowIndex++);

            Cell cell1 = row2.createCell(1);
            cell1.setCellValue(list.getCustomName());
            cell1.setCellStyle(cellStyles[0]);

            cell1 = row2.createCell(2);
            cell1.setCellValue(list.getContractNo());
            cell1.setCellStyle(cellStyles[1]);

            cell1 = row2.createCell(3);
            cell1.setCellValue(list.getProductNo());
            cell1.setCellStyle(cellStyles[2]);

            cell1 = row2.createCell(4);
            cell1.setCellValue(list.getcNumber());
            cell1.setCellStyle(cellStyles[3]);

            cell1 = row2.createCell(5);
            cell1.setCellValue(list.getFactoryName());
            cell1.setCellStyle(cellStyles[4]);

            cell1 = row2.createCell(6);
            cell1.setCellValue(list.getDeliveryPeriod());
            cell1.setCellStyle(cellStyles[5]);

            cell1 = row2.createCell(7);
            cell1.setCellValue(list.getShipTime());
            cell1.setCellStyle(cellStyles[6]);

            cell1 = row2.createCell(8);
            cell1.setCellValue(list.getTradeTerms());
            cell1.setCellStyle(cellStyles[7]);

        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.download(byteArrayOutputStream,response,"报表.xlsx");
    }

}
