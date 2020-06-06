package com.sgcc.fastdfs2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: ss
 * @time: 2020/6/4 20:45
 */
@Configuration
@ConfigurationProperties(prefix="fdfs.config")
public class FastDFSConfigurer
{
    private static FastDFSConfigurer configurer;
    @Value("${fdfs.config.connect_timeout}")
    private String connect_timeout;
    @Value("${fdfs.config.network_timeout}")
    private String network_timeout;
    @Value("${fdfs.config.charset}")
    private String charset;
    @Value("${fdfs.config.http.tracker_http_port}")
    private String tracker_http_port;
    @Value("${fdfs.config.http.anti_steal_token}")
    private String anti_steal_token;
    @Value("${fdfs.config.tracker_server}")
    private String tracker_server;

    @PostConstruct
    public void init()
    {
        configurer = this;
    }

    public static FastDFSConfigurer getFastDFSConfigurer()
    {
        return configurer;
    }

    public String getConnect_timeout()
    {
        return this.connect_timeout;
    }

    public void setConnect_timeout(String connect_timeout)
    {
        this.connect_timeout = connect_timeout;
    }

    public String getNetwork_timeout()
    {
        return this.network_timeout;
    }

    public void setNetwork_timeout(String network_timeout)
    {
        this.network_timeout = network_timeout;
    }

    public String getCharset()
    {
        return this.charset;
    }

    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    public String getTracker_http_port()
    {
        return this.tracker_http_port;
    }

    public void setTracker_http_port(String tracker_http_port)
    {
        this.tracker_http_port = tracker_http_port;
    }

    public String getAnti_steal_token()
    {
        return this.anti_steal_token;
    }

    public void setAnti_steal_token(String anti_steal_token)
    {
        this.anti_steal_token = anti_steal_token;
    }

    public String getTracker_server()
    {
        return this.tracker_server;
    }

    public void setTracker_server(String tracker_server)
    {
        this.tracker_server = tracker_server;
    }
}

