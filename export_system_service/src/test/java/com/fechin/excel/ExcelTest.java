package com.fechin.excel;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import java.io.FileOutputStream;

public class ExcelTest {
    @Test
    public void test01() throws Exception {
        //1.创建工作簿XSSF提供读写Excel OOXML XLSX格式案列的功能
        XSSFWorkbook wb = new XSSFWorkbook();
        //2.创建表单sheet
        Sheet test = wb.createSheet("test");
        //3.文件流
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\JavaDevelopmentCodes\\application_folder\\test.xlsx");
        //4.写入文件
        wb.write(fileOutputStream);
        //5.关闭流
        fileOutputStream.close();
    }

    @Test
    public void test02() throws Exception{
        XSSFWorkbook web = new XSSFWorkbook();
        XSSFSheet sheet = web.createSheet();
        //创建行对象,默认是从第0行开始,参数就是索引
        XSSFRow row = sheet.createRow(2);
        //创建单元格对象,参数就是索引,默认是从0开始
        XSSFCell cell = row.createCell(2);
        //在单元格中输入数据
        cell.setCellValue("蛇皮怪");
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\JavaDevelopmentCodes\\application_folder\\test2.xlsx");
        web.write(fileOutputStream);
        fileOutputStream.close();
    }

    @Test
    public void test03() throws Exception{
        XSSFWorkbook web = new XSSFWorkbook();
        XSSFSheet sheet = web.createSheet();
        XSSFRow row = sheet.createRow(2);
        XSSFCell cell = row.createCell(2);
        cell.setCellValue("呵呵哒");

        //进行样式的处理
        XSSFCellStyle style = web.createCellStyle();
        style.setBorderTop(BorderStyle.DASH_DOT);//上边框
        style.setBorderBottom(BorderStyle.MEDIUM);//下边框
        style.setBorderLeft(BorderStyle.SLANTED_DASH_DOT);
        style.setBorderRight(BorderStyle.THICK);
        //创建字体对象
        XSSFFont font = web.createFont();
        font.setFontName("华文楷体");//字体设置
        font.setFontHeightInPoints((short) 28);//字号设置
        style.setFont(font);

        //行高和列宽
        row.setHeightInPoints(50);
        //列宽的宽度,字符宽度
        sheet.setColumnWidth(2,31*256);

        //居中显示
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //向单元格设置样式
        cell.setCellStyle(style);

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\JavaDevelopmentCodes\\application_folder\\test3.xlsx");
        web.write(fileOutputStream);
        fileOutputStream.close();
    }

}
