package com.ldc.transactionaldemo.common;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/9 22:40
 */
public class LogUtil {

    /**
     * Description: 统一日志输出格式，在使用的时候格式如下：
     * 在正常日志情况下：log.info(LogUtil.format("说明文字或者对象信息,可以传多个参数"));
     * 在异常情况下：log.error(LogUtil.format("说明文字或者对象信息,可以传多个参数"),e);
     * @author yangbao
     * @date 2020-06-28 10:52:24
     */
    public static String format(Object... infos){
        String log = "method>>>" + Thread.currentThread().getStackTrace()[2].getMethodName() + ";log>>>";
        for(Object info : infos){
            log+=info;
        }
        return log;
    }
}