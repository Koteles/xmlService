package com.zip;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReading {

    public List<List<String>> xlsx(InputStream inputFile, String fileName) {    	
    	
        
        List<String> list = new ArrayList<String>();
        
        List<List<String>> group = new ArrayList<List<String>>();
        boolean isXLS = false;
        int dotIndex = fileName.lastIndexOf(".");
        if(fileName.substring(dotIndex+1, fileName.length()).equals("xls")) {
        	isXLS = true;
        }       
        
        try {
            
        	Sheet sheet = null;
        	
        	if(isXLS) {
        		HSSFWorkbook wBook = new HSSFWorkbook(inputFile);
            	sheet = wBook.getSheetAt(0);
        		
        	} else {
        		// Get the workbook object for XLSX file
        	XSSFWorkbook wBook = new XSSFWorkbook(inputFile);
        	 // Get first sheet from the workbook
    		sheet = wBook.getSheetAt(0);
        	}
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
                            list.add(cell.getBooleanCellValue() + ",");
                            break;
                    case Cell.CELL_TYPE_NUMERIC:
                            list.add(cell.getNumericCellValue() + ",");
                            break;
                    case Cell.CELL_TYPE_STRING:
                            list.add(cell.getStringCellValue() + ",");
                            break;

                    case Cell.CELL_TYPE_BLANK:
                            list.add("" + ",");
                            break;
                    default:                    		
                            list.add(cell + ",");

                    }
                    
                }
                String last = list.get(list.size()-1);
                int index = last.lastIndexOf(",");
                last = new StringBuilder(last).replace(index, index+1, "").toString();
               
                list.set(list.size()-1, last);
                
                group.add(new ArrayList<String>(list));
                list.clear();
            }
            

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return group;
    }
   
}
