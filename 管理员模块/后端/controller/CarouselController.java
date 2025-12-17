package com.kk.em.controller;
//作用：实现对商品的增删改查
import com.auth0.jwt.JWT;
import com.kk.em.annotation.Authority;
import com.kk.em.common.Result;
import com.kk.em.entity.AuthorityType;
import com.kk.em.entity.Good;
import com.kk.em.service.GoodService;
import com.kk.em.service.UserService;
import com.kk.em.entity.Carousel;
import com.kk.em.service.CarouselService;
import com.kk.em.entity.User;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")//请求路径
public class CarouselController {//控制器
    @Resource
    private CarouselService carouselService;//注入CarouselService
    @Resource
    private HttpServletRequest request;//注入HttpServletRequest
    @Resource
    private UserService userService;//注入UserService
    @Resource
    private GoodService goodService;//注入GoodService

    public User getUser() {//获取当前用户
        String token = request.getHeader("token");//从header中获取token
        String username = JWT.decode(token).getAudience().get(0);//从token中获取username
        return userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));//根据username查询用户
    }

    /*
    查询
    */
    @GetMapping("/{id}")//根据id查询
    public Result findById(@PathVariable Long id) {
        return Result.success(carouselService.getById(id));
    }//根据id查询

    @GetMapping//查询所有
    public Result findAll() {//查询所有商品
        List<Carousel> list = carouselService.getAllCarousel();//获取所有商品
        return Result.success(list);//返回所有Carousel
    }

    /*
    保存
    */
    @Authority(AuthorityType.requireAuthority)//需要权限
    @PostMapping//保存
    public Result save(@RequestBody Carousel carousel) {//保存商品
        Good good = goodService.getById(carousel.getGoodId());//根据goodId查询商品
        if(good == null) {//商品不存在
            return Result.error("400", "商品id错误，未查询到商品id = " + carousel.getGoodId());//商品id错误
        }
        carouselService.saveOrUpdate(carousel);//保存或更新
        return Result.success();//返回成功
    }
    @Authority(AuthorityType.requireAuthority)//需要权限
    @PutMapping//{id}
    public Result update(@RequestBody Carousel carousel) {//更新商品
        Good good = goodService.getById(carousel.getGoodId());//根据goodId查询商品
        if(good == null) {//商品不存在
            return Result.error("400", "商品id错误，未查询到商品id = " + carousel.getGoodId());//商品id错误
        }
        carouselService.updateById(carousel);//更新
        return Result.success();//返回成功
    }

    /*
    删除
    */
    @Authority(AuthorityType.requireAuthority)//{id}
    @DeleteMapping("/{id}")//删除
    public Result delete(@PathVariable Long id) {//删除商品
        carouselService.removeById(id);//根据id删除
        return Result.success();//返回成功
    }


}
