package com.srct.service.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;

import com.srct.service.config.annotation.BeanFieldAnnotation;
import com.srct.service.utils.log.Log;

/**
 * 
 * @ClassName: ExcelUtils
 * @Description: Notice:
 *               <p>
 *               1.List that want to generate into Excel. If define
 *               {@link com.srct.service.config.annotation.BeanFieldAnnotation}
 *               it can show Chinese Title If not , It will use each field name
 *               as Column Title
 *               </p>
 */
public class ExcelUtils {

    private ExcelUtils() {
    }

    // private static final String FIX_CLASS_CHINESE_TITLE_FILED_NAME =
    // "sColumnTitle";
    private static List<Field> sContentClassVariablesField;

    private static List<String> sColumnTitle;

    /**
     *
     * @Title: generateDefaultExcelResponse
     * @Description:
     * @param object
     *            bean
     * @return HSSFWorkbook
     */
    @SuppressWarnings("unchecked")
    public static HSSFWorkbook generateResultExcelResponse(Object object) {
        if (!(object instanceof ArrayList<?>)) {
            Log.e("The generate Object is not ArrayList");
            return null;
        }
        if (((ArrayList<Object>) object).isEmpty()) {
            Log.e("The generate list is empty");
            return null;
        }
        Class<?> itemClass = ((ArrayList<Object>) object).get(0).getClass();
        setContentClassVariables(itemClass);
        setColumnTitle(itemClass);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = createSheetWithName(wb, "result");
        HSSFCellStyle cellStyle = setContentCellStyle(wb);
        ArrayList<Object> list = (ArrayList<Object>) object;
        for (int line = 0; line < list.size(); line++) {
            HSSFRow row = sheet.createRow(line + 1);
            for (int columnIndex = 0; columnIndex < sContentClassVariablesField.size(); columnIndex++) {
                HSSFCell cell = row.createCell(columnIndex);
                cell.setCellStyle(cellStyle);
                Method getMethod = null;
                try {
                    // Item Class should generate getXXX method
                    getMethod = itemClass.getMethod("get" + convertGetFunctionNameByUsingVariables(
                            sContentClassVariablesField.get(columnIndex).getName()));
                } catch (NoSuchMethodException | SecurityException e1) {
                    Log.e("getMethod Exception " + e1.getMessage());
                }
                try {
                    if (getMethod == null) {
                        Log.e("Doesnt find get Method for " + sContentClassVariablesField.get(columnIndex).getName());
                    } else {
                        Object invokeResult = getMethod.invoke(list.get(line));
                        if (invokeResult != null) {
                            invokeResult = invokeResult.toString();
                        }
                        cell.setCellValue((String) invokeResult);
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e2) {
                    Log.e("getMethod Exception " + e2.getMessage());
                }
            }
        }
        formatSheet(wb);
        return wb;
    }

    public static int generateResultExcelResponse(Object object, String file) {
        if (object == null || !file.endsWith(".xls")) {
            return -1;
        }
        HSSFWorkbook hssfWorkbook = generateResultExcelResponse(object);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.flush();
            hssfWorkbook.write(outputStream);
            return 1;
        } catch (IOException e) {
            Log.e(e.getMessage());
            return -1;
        }
    }

    private static HSSFCellStyle setContentCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setShrinkToFit(true);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }

    private static HSSFCellStyle setTitleCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setShrinkToFit(true);
        cellStyle.setWrapText(true);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setFillForegroundColor(HSSFColorPredefined.PALE_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private static HSSFSheet createSheetWithName(HSSFWorkbook wb, String sheetName) {
        HSSFSheet sheet = wb.createSheet("result");
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle colorStyle = setTitleCellStyle(wb);
        for (int i = 0; i < sColumnTitle.size(); i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(colorStyle);
            titleCell.setCellValue(sColumnTitle.get(i));
        }
        return sheet;
    }

    private static void formatSheet(HSSFWorkbook wb) {
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            HSSFSheet sheet = wb.getSheetAt(i);
            for (int j = 0; j < sContentClassVariablesField.size(); j++) {
                sheet.autoSizeColumn(j);
            }
            setSizeColumn(sheet);
        }
    }

    private static void setContentClassVariables(Class<?> clazz) {
        sContentClassVariablesField = new ArrayList<>();
        if (clazz != null) {
            Field[] f = clazz.getDeclaredFields();
            for (Field fie : f) {
                sContentClassVariablesField.add(fie);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void setColumnTitle(Class<?> clazz) {
        sColumnTitle = new ArrayList<>();
        for (Field f : sContentClassVariablesField) {
            if (f.getAnnotation(BeanFieldAnnotation.class) != null) {
                sColumnTitle.add(f.getAnnotation(BeanFieldAnnotation.class).title());
            } else {
                sColumnTitle.add(f.getName());
            }
        }
    }

    private static void setSizeColumn(HSSFSheet sheet) {
        for (int columnNum = 0; columnNum <= sContentClassVariablesField.size(); columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256 + 184);
            // Refer to
            // https://blog.csdn.net/duqian42707/article/details/51491312
        }
    }

    private static String convertGetFunctionNameByUsingVariables(String variables) {
        return variables.substring(0, 1).toUpperCase() + variables.substring(1);
    }
}
