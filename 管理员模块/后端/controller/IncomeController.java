package com.kk.em.controller;



import com.kk.em.annotation.Authority;
import com.kk.em.entity.AuthorityType;
import com.kk.em.service.IncomeService;
import com.kk.em.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Authority(AuthorityType.requireAuthority)
@RestController
@RequestMapping("/api/income")
public class IncomeController {

    @Resource
    private IncomeService incomeService;

    @GetMapping("/chart")
    public Result getChart(){
        return Result.success(incomeService.getChart());
    }
    @GetMapping("/week")
    public Result getWeekIncome(){
        return Result.success(incomeService.getWeekIncome());
    }

    @GetMapping("/month")
    public Result getMonthIncome(){
        return Result.success(incomeService.getMonthIncome());
    }
}
