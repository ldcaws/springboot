package com.ldc.freemarkdoc.service;

import com.ldc.freemarkdoc.common.DocUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/6 22:47
 */
@Service
public class DemoService {

    public File getInspectReport(String taskId, String saveRootPath) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name","周恩来");
        paramsMap.put("age",new Date().toString());
        paramsMap.put("content","文档内容");
        File file = DocUtil.createDoc(paramsMap, saveRootPath + File.separator + taskId + ".doc", "reportTemplate");
        return file;
    }

    public String savePath(Long lineId,Long towerId, List<List<String>> data) {
        return "入库成功";
    }

}
