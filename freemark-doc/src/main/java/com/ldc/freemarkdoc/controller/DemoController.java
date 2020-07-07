package com.ldc.freemarkdoc.controller;

import com.ldc.freemarkdoc.common.DateUtil;
import com.ldc.freemarkdoc.common.ExcelUtil;
import com.ldc.freemarkdoc.common.FileUtil;
import com.ldc.freemarkdoc.common.ResponseData;
import com.ldc.freemarkdoc.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/6 22:42
 */
@RestController
@RequestMapping("/rest")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/downloadWord", method = RequestMethod.GET, produces = "application/json")
    public Object downloadWord(HttpServletRequest request, HttpServletResponse response) {
        String taskId = request.getParameter("taskId");
        taskId = taskId == null?"报告":taskId;
        try {
            String fileSaveRootPath = request.getSession().getServletContext().getRealPath("/WEB-INF/reports");
            File f = new File(fileSaveRootPath);
            if (!f.exists()) {
                f.mkdirs();
            }
            //wordFileSavePath在service层生成
            //String wordFileSavePath = fileSaveRootPath + File.pathSeparator + "巡检报告-" + taskId + ".doc";
            //生成word文档
            File file = demoService.getInspectReport(taskId, fileSaveRootPath);

            String realFileName = "巡检报告-" + taskId + "-" + DateUtil.formateNowByPattern(DateUtil.DATE_FROMMAT_YMDHMS) + ".doc";
            //下载word文档
            FileUtil.downloadFile(request, response, file.getPath(), realFileName);
            return ResponseData.success(true, "下载巡检报告成功。");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("下载巡检报告出现异常。");
        }
    }

    @RequestMapping(value = "/downloadPathTempalate", method = RequestMethod.GET)
    public Object downloadPathTempalate(HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = new File(DemoController.class.getResource("/").getPath());
            String downloadName = "pathTemplate" + "-" + DateUtil.formateNowByPattern(DateUtil.DATE_FROMMAT_YMDHMS) + ".xls";
            FileUtil.downloadFile(request, response, file.getPath() + "/" + "pathTemplate.xls", downloadName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("下载巡检路径excel模板失败");
        }
        return ResponseData.success("下载成功");
    }

    @RequestMapping(value = "/uploadPath", method = RequestMethod.POST)
    public Object uploadPath(@RequestParam("fileContent") MultipartFile file, @RequestParam Long lineId, @RequestParam Long towerId) throws Exception{
        List<List<String>> list = ExcelUtil.readExcelByFile(file);
        return ResponseData.success(demoService.savePath(lineId, towerId, list), "上传成功");
    }

}
