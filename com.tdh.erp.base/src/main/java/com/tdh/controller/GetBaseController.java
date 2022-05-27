package com.tdh.controller;


import com.tdh.common.Route;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;


@Controller
public class GetBaseController {

    @RequestMapping(value = Route.FactoryUrl.TO_FACTORY_LIST,method = {RequestMethod.GET})
    public String toFactoryList(){
        return Route.FactoryUrl.TO_FACTORY_URL;
    }

    @RequestMapping(value = Route.FactoryUrl.TO_FACTORY_DETAIL,method = {RequestMethod.GET})
    public String toFactoryDetail(){
        return Route.FactoryUrl.TO_FACTORY_DETAIL_URL;
    }

    @RequestMapping(value = Route.GoodsUrl.TO_GOODS_LIST,method = {RequestMethod.GET})
    public String toGoodsList (){return Route.GoodsUrl.TO_GOODS_URL;}

    @RequestMapping(value = Route.GoodsUrl.TO_GOODS_DETAIL,method = {RequestMethod.GET})
    public String toGoodsDetail(){return Route.GoodsUrl.TO_GOODS_DETAIL_URL;}

    @RequestMapping(value = Route.GoodsUrl.TO_GOODS_ADD,method = {RequestMethod.GET})
    public String toGoodsAdd(){return Route.GoodsUrl.TO_GOODS_ADD_URL;}

    @RequestMapping(value =Route.ConsumerUrl.TO_CONSUMER,method = {RequestMethod.GET})
    public String toConsumerList(){return Route.ConsumerUrl.TO_CONSUMER_URL;}

    @RequestMapping(value =Route.ConsumerUrl.TO_CONSUMER_ADD,method = {RequestMethod.GET})
    public String toConsumerAdd(){return Route.ConsumerUrl.TO_CONSUMER_ADD_URL;}

    @RequestMapping(value =Route.ConsumerUrl.TO_CONSUMER_EDIT,method = {RequestMethod.GET})
    public String toConsumerEdit(){return Route.ConsumerUrl.TO_CONSUMER_EDIT_URL;}
}
