package com.tdh.controller;


import com.tdh.common.Route;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class GetRepController {

    @RequestMapping(value = Route.RepertoryUrl.TO_REPERTORY,method = {RequestMethod.GET})
    public String toRepertoryList(){
        return Route.RepertoryUrl.TO_REPERTORY_URL;
    }

    @RequestMapping(value = Route.RepertoryUrl.TO_REPERTORY_ADD,method = {RequestMethod.GET})
    public String toRepertoryAdd(){
        return Route.RepertoryUrl.TO_REPERTORY_ADD_URL;
    }

    @RequestMapping(value = Route.RepertoryUrl.TO_REPERTORY_EDIT,method = {RequestMethod.GET})
    public String toRepertoryEdit(){return Route.RepertoryUrl.TO_REPERTORY_EDIT_URL; }
}
