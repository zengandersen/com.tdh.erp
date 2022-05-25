package com.tdh.controller;


import com.tdh.common.Route;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class GetMealController {


    @RequestMapping(value = Route.MealUrl.TO_MEAL,method = RequestMethod.GET)
    public String toMeal(){return Route.MealUrl.TO_MEAL_URL;}

    @RequestMapping(value = Route.MealUrl.TO_MEAL_INFO,method = RequestMethod.GET)
    public String toMealInfo(){return Route.MealUrl.TO_MEAL_INFO_URL;}

    @RequestMapping(value = Route.MealUrl.TO_MEAL_INFO_EDIT,method =  RequestMethod.GET)
    public String toMealEdit (){return Route.MealUrl.TO_MEAL_INFO_EDIT_URL;}

    @RequestMapping(value = Route.MealBindUrl.TO_MEAL_BIND,method = RequestMethod.GET)
    public String toMealBind(){return Route.MealBindUrl.TO_MEAL_BIND_URL;}

    @RequestMapping(value = Route.MealBindUrl.TO_MEAL_NOT_BIND_LIST ,method = RequestMethod.GET)
    public String toMealNotBind(){return Route.MealBindUrl.TO_MEAL_NOT_BIND_URL;}
}
