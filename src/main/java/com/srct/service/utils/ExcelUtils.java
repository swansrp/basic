package com.srct.service.utils;

import com.srct.service.config.annotation.BeanFieldAnnotation;
import com.srct.service.utils.log.Log;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ExcelUtils
 * @Description: Notice:
 * @Author: Sharuopeng
 * <p>
 * 1.List that want to generate into Excel. If define
 * {@link com.srct.service.config.annotation.BeanFieldAnnotation}
 * it can show Chinese Title If not , It will use each field name
 * as Column Title
 * </p>
 */
public class ExcelUtils {

    public static final String EXCEL_SUFFIX = ".xls";
    public static final String DEFAULT_SHEET = "result";

    private ExcelUtils() {
    }

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
        List<String> titles = getColumnTitle(itemClass);
        List<Field> fields = getFieldList(itemClass);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = createSheetWithName(wb, DEFAULT_SHEET, titles);
        HSSFCellStyle cellStyle = setContentCellStyle(wb);
        ArrayList<Object> list = (ArrayList<Object>) object;
        for (int line = 0; line < list.size(); line++) {
            HSSFRow row = sheet.createRow(line + 1);
            for (int columnIndex = 0; columnIndex < fields.size(); columnIndex++) {
                HSSFCell cell = row.createCell(columnIndex);
                cell.setCellStyle(cellStyle);
                String value = null;
                try {
                    Object obj = ReflectionUtil.getValue(list.get(line), fields.get(columnIndex));
                    if (obj != null) {
                        if (obj instanceof Date) {
                            value = DateUtils.formatDate((Date) obj, "yyyy-MM-dd HH:mm:ss");
                        } else {
                            value = obj.toString();
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                cell.setCellValue(value);
            }
        }
        formatSheet(wb, fields);
        return wb;
    }

    public static int generateResultExcelResponse(Object object, String file) {
        if (object == null || !file.endsWith(EXCEL_SUFFIX)) {
            return -1;
        }
        HSSFWorkbook hssfWorkbook = generateResultExcelResponse(object);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.flush();
            if (hssfWorkbook != null) {
                hssfWorkbook.write(outputStream);
                return 1;
            }
        } catch (IOException e) {
            Log.e(e.getMessage());
        }
        return -1;
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

    private static HSSFSheet createSheetWithName(HSSFWorkbook wb, String sheetName, List<String> titles) {
        HSSFSheet sheet = wb.createSheet(sheetName);
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle colorStyle = setTitleCellStyle(wb);
        for (int i = 0; i < titles.size(); i++) {
            HSSFCell titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(colorStyle);
            titleCell.setCellValue(titles.get(i));
        }
        return sheet;
    }

    private static void formatSheet(HSSFWorkbook wb, List<Field> fields) {
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            HSSFSheet sheet = wb.getSheetAt(i);
            for (int j = 0; j < fields.size(); j++) {
                sheet.autoSizeColumn(j);
            }
            setSizeColumn(sheet, fields);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<String> getColumnTitle(Class<?> clazz) {
        List<String> columnTitles = new ArrayList<>();
        getFieldList(clazz, columnTitles);
        return columnTitles;
    }

    private static List<Field> getFieldList(Class<?> clazz, List<String> titles) {
        return ReflectionUtil.getFields(clazz).stream().filter(field -> {
            ApiModelProperty apiModelAnnotation = field.getAnnotation(ApiModelProperty.class);
            BeanFieldAnnotation beanFieldAnnotation = field.getAnnotation(BeanFieldAnnotation.class);
            if (beanFieldAnnotation != null && StringUtils.isNotEmpty((beanFieldAnnotation).title())) {
                if (titles != null) {
                    titles.add((beanFieldAnnotation).title());
                }
                return true;
            } else if (apiModelAnnotation != null && StringUtils.isNotEmpty((apiModelAnnotation).value())) {
                if (titles != null) {
                    titles.add((apiModelAnnotation).value());
                }
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
    }

    private static List<Field> getFieldList(Class<?> clazz) {
        return getFieldList(clazz, null);
    }

    private static void setSizeColumn(HSSFSheet sheet, List<Field> fields) {
        for (int columnNum = 0; columnNum <= fields.size(); columnNum++) {
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

    public static <T> ArrayList<T> readFromExcel(MultipartFile file, Class<T> clazz) {
        File f;
        ArrayList<T> objList = null;
        try {
            f = File.createTempFile("tmp", null);
            file.transferTo(f);
            objList = readFromExcel(f.getAbsolutePath(), clazz);
            f.deleteOnExit();
        } catch (IllegalStateException | IOException e) {
            Log.e(e);
        }
        return objList;
    }

    public static <T> ArrayList<T> readFromExcel(String filePath, Class<T> clazz) {
        FileInputStream fs;
        HSSFWorkbook wb;
        ArrayList<T> objList = null;
        try {
            fs = new FileInputStream(filePath);
            // 使用POI提供的方法得到excel的信息
            POIFSFileSystem ps = new POIFSFileSystem(fs);
            wb = new HSSFWorkbook(ps);
            objList = readFormExcel(wb, clazz);
            fs.close();
        } catch (Exception e) {
            Log.e(e);
        }
        return objList;
    }

    private static <T> ArrayList<T> readFormExcel(HSSFWorkbook wb, Class<T> clazz) {
        ArrayList<T> resList = new ArrayList<>();
        // 获取到工作表，因为一个excel可能有多个工作表
        HSSFSheet sheet = wb.getSheetAt(0);
        int maxline = sheet.getLastRowNum();
        List<Field> fields = getFieldList(clazz);
        for (int line = 1; line <= maxline; line++) {
            HSSFRow row = sheet.getRow(line);
            if (row == null) {
                continue;
            }
            int maxColumn = row.getLastCellNum();
            try {
                T res = clazz.newInstance();
                for (int column = 0; column < maxColumn; column++) {
                    HSSFCell cell = row.getCell(column);
                    String valueString = cell.getStringCellValue();
                    try {
                        Object value =
                                JSONUtil.readJson(JSONUtil.toJSONString(valueString), fields.get(column).getType());
                        ReflectionUtil.setValue(fields.get(column), res, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                resList.add(res);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }



}
