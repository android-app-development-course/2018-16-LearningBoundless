package com.scnu.learning_boundless.util;

/*
 * @program: LearningBoundless
 * @description:
 * @author: WuchangI
 * @create: 2018-12-27-12-08
 **/

import com.scnu.learning_boundless.entity.Result;

public class CreateResultUtil {

    public static Result createSuccessResult(Object data){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功！");
        result.setData(data);

        return result;
    }

    public static Result createErrorResult(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
