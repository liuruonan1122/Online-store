package com.kk.em.service;

import com.kk.em.entity.OrderGoods;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.em.mapper.OrderGoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderGoodsService extends ServiceImpl<OrderGoodsMapper, OrderGoods> {

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

}
