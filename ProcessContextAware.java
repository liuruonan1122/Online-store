package com.kk.em.config;
//进程上下文感知，在启动时关闭占用指定端口的进程
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
//注解@Component用于将该类注册为Spring Bean
public class ProcessContextAware implements ServletContextAware {
    @Value("${server.port}")
    private String port;
//@Value用于注入配置文件中的属性值，这里注入的是端口号
    @Override
    //@Override用于重写ServletContextAware接口中的setServletContext方法
    public void setServletContext(ServletContext servletContext) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
//获取操作系统类型，判断是Windows还是Linux或Mac OS
            if (os.contains("win")) {
                // Windows系统关闭占用指定端口的逻辑
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "netstat -ano | findstr " + port);
                // 执行命令行命令netstat -ano | findstr port，获取占用指定端口的进程的PID
                Process process = processBuilder.start();
                // 关闭占用指定端口的进程
                InputStream inputStream = process.getInputStream();
                // 读取命令行输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                // 逐行读取命令行输出
                String line;
                // 循环读取命令行输出，逐行解析PID并关闭进程
                while ((line = reader.readLine()) != null) {
                    // 解析PID
                    String[] tokens = line.trim().split("\\s+");
                    // 取最后一个元素为PID
                    String pid = tokens[tokens.length - 1];
                    // 关闭进程
                    ProcessBuilder killProcess = new ProcessBuilder("cmd.exe", "/c", "taskkill /F /PID " + pid);
                    // 执行命令行命令taskkill /F /PID pid，关闭进程
                    killProcess.start();
                    // 等待进程关闭
                }
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // Linux或Mac OS系统关闭占用指定端口的逻辑
                ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "lsof -ti:" + port + " | xargs kill -9");
                // 执行命令行命令lsof -ti:port | xargs kill -9，获取占用指定端口的进程的PID并关闭进程
                processBuilder.start();
                // 等待进程关闭
            }
        } catch (IOException e) {
            // 异常处理
            e.printStackTrace();
            // 关闭占用指定端口的进程失败
        }

    }
}