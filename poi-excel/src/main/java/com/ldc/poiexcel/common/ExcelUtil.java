package com.ldc.poiexcel.common;

import com.ldc.poiexcel.model.Student;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/7 8:05
 */
public class ExcelUtil {

    /**
     * Description: 解析excel表格(通过文件解析)
     * @author ldc
     * @date 2020-06-28 18:24:52
     */
    public static List<List<String>> readExcelByFile(MultipartFile filedata) {
        String fileName = filedata.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        // return a list contains many list
        List<List<String>> lists = new ArrayList<List<String>>();
        //读取excel文件
        InputStream is = null;
        try {
            is = filedata.getInputStream();
            //获取工作薄
            Workbook wb = null;
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(is);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }

            //读取第一个工作页sheet
            Sheet sheet = wb.getSheetAt(0);
            //第一行为标题
            for (Row row : sheet) {
                ArrayList<String> list = new ArrayList<String>();
                for (Cell cell : row) {
                    //根据不同类型转化成字符串
                    if (0 == cell.getCellType()) {
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            list.add(DateFormatUtils.format(date, "yyyy-MM-dd"));
                        } else {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            list.add(cell.getStringCellValue());
                        }
                    } else {
                        list.add(cell.getStringCellValue());
                    }
                }
                lists.add(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    /**
     * Description: 将数据生成excel表格
     * @author ldc
     * @date 2020-06-28 18:24:52
     */
    @SuppressWarnings("deprecation")
    public static <T> void exportTtoExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLUE.index);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                if (fieldName.equals("serialVersionUID")) {
                    continue;
                }
                if (fieldName.equals("is_del")) {
                    continue;
                }
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;

                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }
                    else if (value != null) {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(workbook!=null)workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void exportMaptoExcel(String title, LinkedHashMap<String,String> headers, Collection<Map<String, Object>> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLUE.index);
        HSSFFont font4 = workbook.createFont();
        font4.setColor(HSSFColor.RED.index);
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        //倒遍历
        //ListIterator<Map.Entry<String,String>> iterator = new ArrayList<Map.Entry<String,String>>(headers.entrySet()).listIterator(headers.size());
        //正遍历
        ListIterator<Map.Entry<String,String>> iterator = new ArrayList<Map.Entry<String,String>>(headers.entrySet()).listIterator();
        int indexHeader = 0;
        List<String> columns = new ArrayList<String>();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            HSSFCell cell = row.createCell(indexHeader);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(entry.getValue());
            cell.setCellValue(text);
            columns.add(entry.getKey());
            indexHeader++;
        }
        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> rowMap = (Map<String, Object>) it.next();
            for (short i = 0; i < columns.size(); i++) {
                String fieldName = columns.get(i);
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                try {
                    Object value = rowMap.get(fieldName);
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value!=null){

                        if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        }else {
                            // 整理日签到中的日期ArrayList
                            if(("日签到记录表".equals(title)||"月签到记录表".equals(title)||"考勤筛查记录表".equals(title)) &&i==8){
                                @SuppressWarnings("unchecked")
                                List<Date> list1 = (List<Date>)value;
                                //List<String> list2 = new ArrayList<String>();
                                String listString="";
                                String formatTime=null;
                                for(Date tmp:list1)
                                {
                                    //System.out.println(tmp);
                                    Date date = (Date) tmp;
                                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                                    formatTime = sdf.format(date);
                                    //System.out.println(tmp);
                                    //list2.add(formatTime);
                                    listString+=formatTime+"  /  ";

                                }

                                textValue=listString.substring(0,listString.lastIndexOf("/"));
                                //System.out.println(list2.toString());
                                //System.out.println(textValue);
                            }else{
                                // 其它数据类型都当作字符串简单处理
                                textValue = value.toString();
                            }

                        }
                    }else{
                        if("考勤记录表".equals(title) && i>4) {
                            textValue="未打卡";//此处为导出考勤记录表特有
                        }else if(("日签到记录表".equals(title)||"月签到记录表".equals(title)||"考勤筛查记录表".equals(title)) && i==6) {
                            textValue="未打卡";//此处为导出考勤记录表特有
                        }
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        richString.applyFont(font3);
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            if("考勤记录表".equals(title) && i>4){
                                if(!("正常".equals(textValue) || "出差".equals(textValue) ||
                                        "外出".equals(textValue) || "休假".equals(textValue))) {
                                    richString.applyFont(font4);
                                }
                            }else if(("日签到记录表".equals(title)||"月签到记录表".equals(title)||"考勤筛查记录表".equals(title)) && i==6){
                                if(!("正常".equals(textValue) || "出差".equals(textValue) ||
                                        "外出".equals(textValue) || "休假".equals(textValue))) {
                                    richString.applyFont(font4);
                                }
                            }
                        }
                        cell.setCellValue(richString);
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                }catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(workbook!=null)workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void exportExcel(String title, String[] headers, Collection<Student> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLUE.index);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("cj.z");
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<Student> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Student t = (Student) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                if (fieldName.equals("serialVersionUID")) {
                    continue;
                }
                if (fieldName.equals("is_del")) {
                    continue;
                }
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;

                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }
                    else if (value != null) {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(workbook!=null)workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 测试学生
        //ExportExcel<Student> ex = new ExportExcel<Student>();
        String title = "学生";
        String[] headers = { "学号", "姓名", "年龄", "性别" };
        List<Student> dataset = new ArrayList<Student>();
        dataset.add(new Student(10000001, "张三", 20, 1));
        dataset.add(new Student(20000002, "李四", 24, 2));
        dataset.add(new Student(30000003, "王五", 22, 3));

        try {
            OutputStream out = new FileOutputStream("E://a.xls");
            exportTtoExcel(title, headers, dataset, out,"yyyy-MM-dd HH:mm:ss");
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
            System.out.println("excel导出成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

