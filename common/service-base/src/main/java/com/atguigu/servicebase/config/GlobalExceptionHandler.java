package com.atguigu.servicebase.config;


import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * @author li
 * @create 2021-04-23 16:30
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //对所有异常进行相同的处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("出现异常");
    }

    //对特定的异常进行处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("出现异常，被除数为零");
    }

    //自定义异常
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R error(EduException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
