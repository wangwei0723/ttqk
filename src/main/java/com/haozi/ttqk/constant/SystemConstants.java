package com.haozi.ttqk.constant;

import com.haozi.ttqk.enums.EnvironmentEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class SystemConstants {

    @Value("${system.dev}")
    public String env;
    public static String dev;

    public static String SERVICE_URL = "http://8.129.214.95:8081/ttqk/";
    public static String FILE_BASE_PATH = "/data/file/ttqk/";

    @PostConstruct
    public void initDev(){
        SystemConstants.dev = env;
        if(EnvironmentEnum.PRO.getEnv().equals(dev) ){
            SERVICE_URL = "http://8.129.214.95:8081/ttqk/";//改成生产URL
            FILE_BASE_PATH = "/data/file/ttqk/";//改成生产地址
        }
    }


}
