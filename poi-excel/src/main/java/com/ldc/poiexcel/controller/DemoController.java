package com.ldc.poiexcel.controller;

import com.ldc.poiexcel.common.ExcelUtil;
import com.ldc.poiexcel.common.ResponseData;
import com.ldc.poiexcel.model.Student;
import com.ldc.poiexcel.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/22 21:17
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    @Autowired
    private DemoService demoService;

    //导入excel解析
    @RequestMapping(value = "/uploadPath", method = RequestMethod.POST)
    public Object uploadPath(@RequestParam("fileContent") MultipartFile file, @RequestParam Long lineId, @RequestParam Long towerId) throws Exception{
        List<List<String>> list = ExcelUtil.readExcelByFile(file);
        return ResponseData.success(demoService.savePath(lineId, towerId, list), "上传成功");
    }

    //将数据存入excel导出
    @GetMapping("download1")
    public Object download1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = "学生";
        String[] headers = { "学号", "姓名", "年龄", "性别" };
        List<Student> dataset = new ArrayList<Student>();
        dataset.add(new Student(10000001, "张三", 20, 1));
        dataset.add(new Student(20000002, "李四", 24, 2));
        dataset.add(new Student(30000003, "王五", 22, 3));
        String pattern = "yyyy-MM-dd HH:mm:ss";
        response.setHeader("content-disposition", "attachment;filename=" + "111.xls");
        response.setCharacterEncoding("utf-8");
        ExcelUtil.exportExcel(title, headers, dataset, response.getOutputStream(), pattern);
        response.getOutputStream().close();//若输出流未关闭则excel文件打开一直报格式不一致
        return "success";
    }

    //将数据存入excel导出
    @GetMapping("download2")
    public Object download2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = "学生";
        String[] headers = { "学号", "姓名", "年龄", "性别" };
        List<Student> dataset = new ArrayList<Student>();
        dataset.add(new Student(10000001, "lili", 20, 1));
        String pattern = "yyyy-MM-dd HH:mm:ss";
        response.setHeader("content-disposition", "attachment;filename=" + "222.xls");
        response.setCharacterEncoding("utf-8");
        ExcelUtil.<Student>exportTtoExcel(title,headers,dataset,response.getOutputStream(),pattern);
        response.getOutputStream().close();//若输出流未关闭则excel文件打开一直报格式不一致
        return "success";
    }

}
