package com.tdh.controller;


import com.tdh.common.Route;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class GetInputController {


    @RequestMapping(value = Route.InputUrl.TO_INPUT,method = RequestMethod.GET)
    public String toInput(){return Route.InputUrl.TO_INPUT_URL;}

    @RequestMapping(value = Route.InputUrl.TO_INPUT_ADD,method = RequestMethod.GET)
    public String toInputAdd(){return Route.InputUrl.TO_INPUT_ADD_URL;}

    @RequestMapping(value = Route.OutUrl.TO_OUTPUT,method = RequestMethod.GET)
    public String toOutput(){return Route.OutUrl.TO_OUTPUT_URL;}

    @RequestMapping(value = Route.OutUrl.TO_OUTPUT_ADD,method = RequestMethod.GET)
    public String toOutPutAdd (){return Route.OutUrl.TO_OUTPUT_ADD_URL;}

}
