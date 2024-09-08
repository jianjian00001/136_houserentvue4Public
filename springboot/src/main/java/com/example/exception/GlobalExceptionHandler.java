package com.example.exception;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.utils.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

//@ControllerAdvice(annotations = {RestController.class, Controller.class})
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    //统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json串
    public ResponseResult customer(HttpServletRequest request, CustomException e) {
        return new ResponseResult(100,e.getCode()+e.getMsg());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handle(MethodArgumentNotValidException e) {
        e.printStackTrace();
//        return e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseResult(100,e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public String handle(BindException e) {
        e.printStackTrace();
        return e.getBindingResult().getFieldError().getDefaultMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult handle(ConstraintViolationException e) {
        e.printStackTrace();
        StringBuffer sb = new StringBuffer();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            sb.append(violation.getMessage());
            sb.append(",");
        }
        System.out.println(e.getConstraintViolations());
        System.out.println(111111);
        System.out.println(sb.toString());
//        return sb.toString();
        return new ResponseResult(100,sb.toString());
    }

}
