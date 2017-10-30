package com.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReading {

	private Map<String, String> myMap;
	
    public List<List<String>> xlsx(InputStream inputFile) {
    	
    	myMap = new LinkedHashMap<String, String>();
    	
        // For storing data into CSV files
        StringBuffer data = new StringBuffer();
        StringBuffer[] arr = new StringBuffer[100];
        List<String> list = new ArrayList<String>();
        
        List<List<String>> group = new ArrayList<List<String>>(100);
        
        try {
            //FileOutputStream fos = new FileOutputStream(outputFile);
            // Get the workbook object for XLSX file
            XSSFWorkbook wBook = new XSSFWorkbook(inputFile);
            // Get first sheet from the workbook
            XSSFSheet sheet = wBook.getSheetAt(0);
            Row row;
            Cell cell;
            // Iterate through each rows from first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                row = rowIterator.next();

                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {

                    cell = cellIterator.next();

                    switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");
                            list.add(cell.getBooleanCellValue() + ",");
                            break;
                    case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");
                            list.add(cell.getNumericCellValue() + ",");
                            break;
                    case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ",");
                            list.add(cell.getStringCellValue() + ",");
                            break;

                    case Cell.CELL_TYPE_BLANK:
                            data.append("" + ",");
                            list.add("" + ",");
                            break;
                    default:                    		
                            data.append(cell + ",");
                            list.add(cell + ",");

                    }
                    String last = list.get(list.size()-1);
                    int index = last.lastIndexOf(",");
                    last = new StringBuilder(last).replace(index, index+1, "").toString();
                    System.out.println(last);
                    list.set(list.size()-1, last);
                }
                group.add(new ArrayList<String>(list));
                list.clear();
                data.append("\r\n");
            }
/*
            fos.write(data.toString().getBytes());
            fos.close();*/
            

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
       // System.out.println(data.toString());
        //return data.toString();
        return group;
    }
    //testing the application 

  /*  public static void main(String[] args) {
        //reading file from desktop
        File inputFile = new File("C:\\Users\\user69\\Desktop\\test.xlsx");
        //writing excel data to csv 
        File outputFile = new File("C:\\Users\\user69\\Desktop\\test1.csv");
        xlsx(inputFile, outputFile);
    }*/
}
