package com.kk.em.config;
//作用：全局异常处理器，捕获所有未处理的异常，并返回一个错误的响应对象，HTTP状态码为500
import com.kk.em.exception.ServiceException;
import com.kk.em.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
//作用：声明一个全局异常处理器，捕获所有未处理的异常，并返回一个错误的响应对象，HTTP状态码为500
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    //声明一个异常处理器，捕获ServiceException异常，并返回一个错误的响应对象，HTTP状态码为500
    public Result handle(ServiceException se){
        return Result.error(se.getCode(),se.getMessage());
    }

    //1. @ExceptionHandler注解用于声明一个异常处理器，注解的值为要处理的异常类型；
    //2. @ResponseBody注解用于将返回值直接写入HTTP响应体中，而不是默认的JSON格式；
    //3. handle方法是异常处理器的具体实现，方法参数为要处理的异常对象；
    //4. Result.error方法用于构造一个错误的响应对象，方法参数为错误码和错误信息；
    //5. ResponseEntity<String>是Spring提供的 ResponseEntity 类，用于构建HTTP响应对象，参数为响应体和HTTP状态码；
    //6. 异常处理器会捕获所有未处理的异常，并返回一个错误的响应对象，HTTP状态码为500 Internal Server Error。
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        // 自定义异常处理逻辑
        String message = e.getMessage();
        // 打印堆栈信息
        if(!(e instanceof ServiceException)) {
            e.printStackTrace();
        }
        if (message.contains("(using password: YES)")) {
            // 屏蔽MySQL的root密码错误信息
            if (!message.contains("'root'@'")) {
                // 屏蔽MySQL的localhost密码错误信息
                message = "PU Request failed with status code 500";
                // 屏蔽MySQL的远程连接密码错误信息
            } else if (message.contains("'root'@'localhost'")) {
                // 屏蔽MySQL的localhost密码错误信息
                message = "P Request failed with status code 500";
                // 屏蔽MySQL的远程连接密码错误信息
            }
        } else if(message.contains("Table") && message.contains("doesn't exist")) {
            // 屏蔽MySQL的表不存在错误信息
            message = "T Request failed with status code 500";
            // 屏蔽MySQL的数据库不存在错误信息
        } else if (message.contains("Unknown database")) {
            // 屏蔽MySQL的数据库不存在错误信息
            message = "U Request failed with status code 500";
            // 屏蔽MySQL的远程连接密码错误信息
        } else if (message.contains("edis")) {
            // 屏蔽Redis的连接错误信息
            message = "R Request failed with status code 500";
            // 屏蔽Redis的远程连接密码错误信息
        } else if (message.contains("Failed to obtain JDBC Connection")) {
            // 屏蔽数据库连接错误信息
            message = "C Request failed with status code 500";
            // 屏蔽数据库远程连接密码错误信息
        } else if (message.contains("SQLSyntaxErrorException")) {
            // 屏蔽SQL语法错误信息
            message = "S Request failed with status code 500";
            // 屏蔽SQL远程连接密码错误信息
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        // 这里可以根据实际情况返回不同的HTTP状态码
    }
}

