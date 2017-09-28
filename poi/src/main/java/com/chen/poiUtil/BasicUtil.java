package com.chen.poiUtil;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: ChenJie
 * @Description:
 * @Date 2017/9/27
 * @Modified by:
 */
public class BasicUtil {
    public static void main(String[] args) {
        writeExcel07High();
        writeExcel03();
    }

    /**
     * 读excel
     * */
    public static void read(){
        File file = null ;
        try {
            file = new File("E:\\idea-space\\MyProject\\poi\\src\\main\\resources\\技术覆盖一览表.xlsx");
            //生成workbook
            Workbook wb = WorkbookFactory.create(file);
            //获取sheet个数
            int sheetNum = wb.getNumberOfSheets();
            Sheet sheet = null;
            //遍历sheet
            for(int i=0;i<sheetNum;i++){
                sheet = wb.getSheetAt(i);
                //获取第三行的表头放到数组里
                Row row3 = sheet.getRow(2);
                int lastrow = row3.getLastCellNum();
                String [] heads = new String [lastrow+1];
                for(int g=0;g<lastrow;g++){
                    heads[g] = row3.getCell(g).toString();
                }

                int lastRow = sheet.getLastRowNum();
                //遍历行
                for(int j=3;j<lastRow;j++){
                    Row row = sheet.getRow(j);
                    short firstCellNum = row.getFirstCellNum();
                    short lastCellNum = row.getLastCellNum();
                    String name = row.getCell(0).getStringCellValue();
                    String jineng = name+"的技能是: ";
                    for(int k=firstCellNum;k<lastCellNum;k++){
                        String value = row.getCell(k).toString();
                        if("√".equals(value)){
                            jineng += heads[k]+", ";
                        }
                    }
                    System.out.println(jineng);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写 excel 07之后新版本
     * */
    public static void writeExcel07High(){
        File file;
        Workbook wb;
        Sheet sheet;
        Row row;
        Cell cell;
        try {
            wb = new XSSFWorkbook();
            sheet = wb.createSheet("2017 年度计划");
            row = sheet.createRow(0);
            cell = row.createCell(0, Cell.CELL_TYPE_STRING);
            cell.setCellValue("月份");
            cell = row.createCell(1, Cell.CELL_TYPE_STRING);
            cell.setCellValue("计划");
            for(int i=0;i<10;i++){
                row = sheet.createRow(i+1);
                cell = row.createCell(0,Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue((i+1)+"月");
                cell = row.createCell(1,Cell.CELL_TYPE_STRING);
                cell.setCellValue("吃饭"+(i+1)+"碗");
            }

            FileOutputStream fos = new FileOutputStream("E:\\idea-space\\MyProject\\poi\\src\\main\\resources\\excel07.xlsx");
            wb.write(fos);
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写excel03
     * */
    public static void writeExcel03(){
        File file;
        Workbook wb;
        Sheet sheet;
        Row row;
        Cell cell;
        try {
            wb = new HSSFWorkbook();
            sheet = wb.createSheet("2017 年度计划");
            row = sheet.createRow(0);
            cell = row.createCell(0, Cell.CELL_TYPE_STRING);
            cell.setCellValue("月份");
            cell = row.createCell(1, Cell.CELL_TYPE_STRING);
            cell.setCellValue("计划");
            for(int i=0;i<10;i++){
                row = sheet.createRow(i+1);
                cell = row.createCell(0,Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(i+1);
                cell = row.createCell(1,Cell.CELL_TYPE_STRING);
                cell.setCellValue("吃饭"+(i+1)+"碗");
            }

            FileOutputStream fos = new FileOutputStream("E:\\idea-space\\MyProject\\poi\\src\\main\\resources\\excel03.xls");
            wb.write(fos);
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
