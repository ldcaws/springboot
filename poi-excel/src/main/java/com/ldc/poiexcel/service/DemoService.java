package com.ldc.poiexcel.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/6 22:47
 */
@Service
public class DemoService {

    public String savePath(Long lineId,Long towerId, List<List<String>> data) {
        return "入库成功";
    }

}
