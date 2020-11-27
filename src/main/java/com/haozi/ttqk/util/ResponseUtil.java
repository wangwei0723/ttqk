package com.haozi.ttqk.util;

import com.haozi.ttqk.vo.ResultVo;

public class ResponseUtil {
    public static ResultVo success(Object object){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(object);
        return resultVo;
    }
    public static ResultVo fail(String msg){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(-1);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
